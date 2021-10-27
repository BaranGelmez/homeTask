package test;

import base.BasePage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import util.JsonGetSet;

public class RegressionTests extends BasePage {

    JsonGetSet jsonGetSet = new JsonGetSet();

    @Test
    public void createUser() {
        header("Content-Type", "application/json");
        requestBodyIs("Create User");
        postToUrl("/api/users");
        statusCodeIs(201);
        endOfScenario();
    }

    @Test
    public void getUser(){
        header("Content-Type","application/json");
        getToUrl("/api/users/2");
        endOfScenario();
    }

    @Test
    public void getSingleUserNotFound(){
        header("Content-Type","application/json");
        getToUrl("/api/users/23");
        statusCodeIs(404);
        endOfScenario();
    }

    @Test
    public void registerUser(){
        header("Content-Type", "application/json");
        requestBodyIs("Register User");
        postToUrl("/api/register");
        statusCodeIs(200);
    }

    @Test
    public void deleteUser(){
        header("Content-Type", "application/json");
        deleteRequest("/api/users/308","");
        endOfScenario();
    }

}



