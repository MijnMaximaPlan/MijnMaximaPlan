package alexa.skills.mijnmaximaplan;

import org.junit.Test;

import static org.junit.Assert.*;

public class RestClientTest {

    @Test
    public void login() throws Exception {
        whenRequestLogin();
        thenTokenIsReturned();
    }

    @Test
    public void getUser() throws Exception {
        givenAccessToken();
        whenRequestUser();
        thenUserIs("Jannie");
    }

    //@Test
    public void getNextAppointment() throws Exception {
        givenAccessToken();
        whenRequestNextAppointment();
        thenAppointmentMessageIs("You have no upcoming visit scheduled for Ilja");
    }

    private void givenAccessToken() {
        token = restClient.login();
    }

    private void whenRequestLogin() {
        token = restClient.login();
    }

    private void whenRequestUser() {
        userName = restClient.getUserName(token);
    }

    private void whenRequestNextAppointment() {
        message = restClient.getNextAppointment(token);
    }

    private void thenTokenIsReturned() {
        assertNotNull(token);
    }

    private void thenUserIs(String name) {
        assertEquals(name, userName);
    }

    private void thenAppointmentMessageIs(String expectedMessage) {
        assertNotNull(message);
        assertEquals(expectedMessage, message);
    }

    private RestClient restClient = new RestClient();
    private String token;
    private String userName;
    private String message;
}