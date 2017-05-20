package alexa.skills.mijnmaximaplan;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestClient {

    private static final String DEFAULT_USER_NAME_PASSWORD = "userName=marcusk&password=marcusk";
    private static final Logger log = LoggerFactory.getLogger(RestClient.class);
    private static final Unirest UNIREST = new Unirest();
    private static final String HOST = "http://cgitest-nc0tn1t3.cloudapp.net";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
    private static final String AUTHORIZATION = "Authorization";
    private static final String ACCESS_TOKEN = "access_token";

    public String login() {
        try {
            HttpResponse<JsonNode> response = UNIREST.post(HOST + "/rest/token").header(CONTENT_TYPE, APPLICATION_X_WWW_FORM_URLENCODED).body(DEFAULT_USER_NAME_PASSWORD).asJson();
            return (String) response.getBody().getObject().get(ACCESS_TOKEN);
        } catch (UnirestException e) {
            log.error("Login request failed: ", e);
            throw new RuntimeException(e);
        }
    }


    public String getUserName(String token) {
        try {
            HttpResponse<JsonNode> response = UNIREST.get(HOST + "/rest/entities/USERS/instances/marcusk").header(AUTHORIZATION, "BEARER " + token).asJson();
            return (String) ((JSONObject)response.getBody().getObject().get("entityInstance")).get("firstName");
        } catch (UnirestException e) {
            log.error("Get User request failed: ", e);
            throw new RuntimeException(e);
        }
    }
}
