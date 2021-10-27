package base;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import util.JsonGetSet;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class BasePage {
    private static final Logger log = LogManager.getLogger(BasePage.class);
    private static RequestSpecification request = given();
    String url_ ="https://reqres.in";

    JsonGetSet jsonGetSet = new JsonGetSet();

//get method
    public void getToUrl(String path){
        try {
            Response resp = get(url_+path);
            log.info("sending get to url : "+url_+path);
            log.info("response is: "+resp.asString());
            if(resp.asString().equals("Not Found")){
                Assert.assertFalse("The Getting has failed",true);
            }
            jsonGetSet.setResp(resp);
        }catch (Exception e){
            log.error(e.getMessage());
            Assert.assertFalse(String.valueOf(e),true);
        }
    }

//post method
    public void postToUrl(String path) {
        try {
            Response resp = request.body(jsonGetSet.getRequestBody())
                    .post(url_ + path);
            log.info("sending post to url : " + url_ + path);
            jsonGetSet.setResp(resp);
            jsonGetSet.setBookingId(resp.jsonPath().getString("bookingid"));
            log.info("Response from service: " + jsonGetSet.getResp().asString());
        } catch (Exception e) {
            log.error(e.getMessage());
            Assert.assertFalse(String.valueOf(e), true);
        }
    }

//this method has written because of to take some json value
    public void getJsonValue(){
        jsonGetSet.setFirstname(jsonGetSet.getResp().jsonPath().getString("firstname"));
        log.info(jsonGetSet.getResp().jsonPath().getString("firstname"));
        jsonGetSet.setLastname(jsonGetSet.getResp().jsonPath().getString("lastname"));
        log.info(jsonGetSet.getResp().jsonPath().getString("lastname"));
    }

//for see status code
        public void statusCodeIs( int statusCode){
            try {
               Response statusCodeIs = jsonGetSet.getResp();
                log.info("Status Code is :"+statusCodeIs.statusCode()+" - "+" Expecting status code is : "+statusCode);
                Assert.assertEquals(statusCodeIs.statusCode(),statusCode);
            }catch (Exception e){
                log.error(e.getMessage());
                Assert.assertFalse(String.valueOf(e), true);
            }
        }

//for see response time
        public void responseTime(){
            try {
                long responseTime = jsonGetSet.getResp().getTime();
                log.info("Response Time is : "+ responseTime);
            }catch (Exception e){
                log.error(e.getMessage());
                Assert.assertFalse(String.valueOf(e), true);
            }

        }


//for set header
        public static void header(String headerName,String headerValue){
            try{
                log.info("Header Name is : "+ headerName +" / " +" Header Value is : " + headerValue);
                request.header(headerName,headerValue);
            }catch (Exception e){
                log.error(e.getMessage());
                Assert.assertFalse(String.valueOf(e), true);
            }
        }

//
        public void requestBodyIs(String requestBody){
            try {
                jsonGetSet.setRequestBody(requestBody);
                log.info("request body is : "+ jsonGetSet.getRequestBody());
            }catch (Exception e){
                log.error(e.getMessage());
                Assert.assertFalse(String.valueOf(e), true);
            }
        }


//for authentication
        public void authCreateToken(String username,String password,String path,String requestBody){
            try{
                jsonGetSet.setRequestBody(requestBody);
                Response token = request.given()
                        .auth()
                        .preemptive()
                        .basic(username, password)
                        .body(jsonGetSet.getRequestBody())
                        .post(url_+path)
                        .then()
                        .extract()
                        .response();

                jsonGetSet.setToken(token);
                log.info(token.asString());
            }catch (Exception e){
                log.error(e.getMessage());
                Assert.assertFalse(String.valueOf(e), true);
            }

        }

//for cookie
        public void cookie(String path){
            try {
                request.cookie("token", jsonGetSet.getToken().jsonPath().getString(path));
            }catch (Exception e){
                log.error(e.getMessage());
                Assert.assertFalse(String.valueOf(e), true);
            }
        }


        public void baseUri(String path){
            try {
                request.baseUri(url_+path);
            }catch (Exception e){
                log.error(e.getMessage());
                Assert.assertFalse(String.valueOf(e), true);
            }
        }

        public void requestBodyForPatch(String requestBody){
            try{
                jsonGetSet.setRequestBody(requestBody);
                request.body(jsonGetSet.getRequestBody());
            }catch (Exception e){
                log.error(e.getMessage());
                Assert.assertFalse(String.valueOf(e), true);
            }
        }


        public void responsePatch(){
            try {
                Response response = request.patch();
                jsonGetSet.setResp(response);
                getJsonValue();
                log.info(response.asString());
            }catch (Exception e){
                log.error(e.getMessage());
                Assert.assertFalse(String.valueOf(e), true);
            }
        }

        public void expectedResult(String object,String expectedResult){
        try {
            String actualResult = jsonGetSet.getResp().jsonPath().getString(object);
            Assert.assertEquals(expectedResult,actualResult);
            log.info("Expected Result:"+expectedResult+ " / actual Result:"+actualResult);
        }catch (Exception e){
            log.error(e.getMessage());
            Assert.assertFalse(String.valueOf(e), true);
        }

        }

        public void deleteRequest(String path,String expectedResult){
            try {
                Response response = request.delete(url_+path);
                log.info("deleted");
                String jsonString =response.asString();
                log.info("Expected Message : "+ jsonString);
                Assert.assertEquals(expectedResult,jsonString);
            }catch (Exception e){
                log.error(e.getMessage());
                Assert.assertFalse(String.valueOf(e), true);
            }
        }

        public void endOfScenario(){
            log.info("*********************************");
            log.info("*********************************");
            log.info("******** END OF SCENARIO ********");
            log.info("*********************************");
            log.info("*********************************");
        }
    }

