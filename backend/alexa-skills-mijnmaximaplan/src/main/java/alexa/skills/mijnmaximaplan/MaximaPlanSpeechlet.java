package alexa.skills.mijnmaximaplan;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaximaPlanSpeechlet implements SpeechletV2 {

    private static final Logger log = LoggerFactory.getLogger(MaximaPlanSpeechlet.class);
    private final RestClient restClient = new RestClient();

    private static final String MAXIMA_PLAN_CART = "MaximaPlan";
    private static final String STOP_INTENT = "AMAZON.StopIntent";
    private static final String NO_INTENT = "AMAZON.NoIntent";
    private static final String YES_INTENT = "AMAZON.YesIntent";
    private static final String STATUS_INTENT = "GetStatus";

    private static final String WELCOME_MESSAGE = "Hi %s, Welcome to Princess Maxima Center.";
    private static final String GOODBYE_MESSAGE = "Thank you and enjoy your day.";
    private static final String DEFAULT_STATUS_MESSAGE = "At this moment, you do not have any appointment.";
    private static final String UNKNOWN_MESSAGE = "Sorry, I could not understand your question.";

    @Override
    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> speechletRequestEnvelope) {
        log.info("onSessionStarted requestId={}, sessionId={}",
                speechletRequestEnvelope.getRequest().getRequestId(),
                speechletRequestEnvelope.getSession().getSessionId());
    }

    @Override
    public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> speechletRequestEnvelope) {
        log.info("onLaunch requestId={}, sessionId={}",
                speechletRequestEnvelope.getRequest().getRequestId(),
                speechletRequestEnvelope.getSession().getSessionId());

        return getWelcomeResponse();
    }

    @Override
    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> speechletRequestEnvelope) {
        IntentRequest request = speechletRequestEnvelope.getRequest();

        log.info("onIntent requestId={}, sessionId={}",
                request.getRequestId(),
                speechletRequestEnvelope.getSession().getSessionId());

        Intent intent = request.getIntent();
        String intentName = (intent != null) ? intent.getName() : null;
        switch (intentName) {
            case STOP_INTENT:
            case NO_INTENT:
            case YES_INTENT:
                return getGoodByeResponse();
            case STATUS_INTENT:
                return getStatusResponse();
            default:
                return getUnknownCommandResponse();
        }
    }

    @Override
    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> speechletRequestEnvelope) {
        log.info("onSessionEnded requestId={}, sessionId={}",
                speechletRequestEnvelope.getRequest().getRequestId(),
                speechletRequestEnvelope.getSession().getSessionId());
    }

    private SpeechletResponse getWelcomeResponse() {
        String token = restClient.login();
        String userName = restClient.getUserName(token);

        return createSimpleResponse(String.format(WELCOME_MESSAGE, userName));
    }

    private SpeechletResponse getGoodByeResponse() {
        return createSimpleResponse(GOODBYE_MESSAGE);
    }

    private SpeechletResponse getStatusResponse() {
        String token = restClient.login();
        String userName = restClient.getUserName(token);
        String message = restClient.getNextAppointment(token);
        if (message == null || message.isEmpty()) {
            message = DEFAULT_STATUS_MESSAGE;
        }

        return createSimpleResponse("Hi " + userName + ", " + message);
    }

    private SpeechletResponse getUnknownCommandResponse() {
        return createSimpleResponse(UNKNOWN_MESSAGE);
    }

    private SpeechletResponse createSimpleResponse(String speechText) {
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, createCard(MAXIMA_PLAN_CART, speechText));
    }

    private SpeechletResponse createRepromptResponse(String speechText, String repromptText) {
        SimpleCard card = createCard(MAXIMA_PLAN_CART, speechText);
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
        repromptSpeech.setText(repromptText);
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptSpeech);

        return SpeechletResponse.newAskResponse(speech, reprompt, card);
    }

    private SimpleCard createCard(String title, String speechText) {
        SimpleCard card = new SimpleCard();
        card.setTitle(title);
        card.setContent(speechText);

        return card;
    }
}
