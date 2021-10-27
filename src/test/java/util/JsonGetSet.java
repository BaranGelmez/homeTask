package util;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

public class JsonGetSet {
    Util util = new Util();

    private static String requestBody;
    private static Response token;
    private static Response resp ;
    private static String bookingId;
    private static String firstname;
    private static String lastname;


    private static final Logger log = LogManager.getLogger(JsonGetSet.class);


    public static String getRequestBody(){
        return requestBody;
    }

    public void setRequestBody(String requestBody){
        try{
            util.setParam(requestBody);
            this.requestBody = String.valueOf(util.getParam());
        }catch (Exception e){
            log.error(e.getMessage());
            Assert.assertFalse(String.valueOf(e),true);
        }
    }

    public static String getBookingId() {
        return bookingId;
    }

    public static void setBookingId(String bookingId) {
        JsonGetSet.bookingId = bookingId;
    }

    public static String getFirstname() {
        return firstname;
    }

    public static void setFirstname(String firstname) {
        JsonGetSet.firstname = firstname;
    }

    public static String getLastname() {
        return lastname;
    }

    public static void setLastname(String lastname) {
        JsonGetSet.lastname = lastname;
    }

    public static Response getToken() {
        return token;
    }

    public static void setToken(Response token) {
        JsonGetSet.token = token;
    }

    public static Response getResp() {
        return resp;
    }

    public static void setResp(Response resp) {
        JsonGetSet.resp = resp;
    }
}
