package alexa.skills.mijnmaximaplan;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

public class MaximaPlanHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds = new HashSet<String>();

    static {
        String appId = System.getenv("APP_ID");
        supportedApplicationIds.add(appId);
    }

    public MaximaPlanHandler() {
        super(new MaximaPlanSpeechlet(), supportedApplicationIds);
    }
}
