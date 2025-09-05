package com.app;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import s.utils.TestUtils;

public class TestBase {

@BeforeClass
    public static void init(){
    RestAssured.baseURI = "http://127.0.0.1:8085/student/";
    String uniqueEmail = TestUtils.getRandomValue() + "@test.com";
}


}
