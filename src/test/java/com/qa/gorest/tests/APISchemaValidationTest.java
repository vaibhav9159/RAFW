package com.qa.gorest.tests;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.RestUser;
import com.qa.gorest.utils.StringUtils;
import static io.restassured.module.jsv.JsonSchemaValidator.*; 

public class APISchemaValidationTest extends BaseTest{

	
	@BeforeMethod
	public void getUserSetup()
	{
		restClient = new RestClient(prop, baseURI);
	}
	
	@Test(dataProvider="createUserData")
	public void verifySchemaForCreateSingleUserTest()
	{
//		restClient = new RestClient();
		RestUser restuser = new RestUser("SiyaRam", "Female", StringUtils.getRandomEMail(), "active");
		 restClient.post(GOREST_SERVICE_URL,true, restuser,"json",true)
		.then().log().all().statusCode(APIHttpStatus.CREATED_201.getCode())
		.assertThat().body(matchesJsonSchemaInClasspath("schema.json"));
		
	}
}
