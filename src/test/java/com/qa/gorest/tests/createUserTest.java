package com.qa.gorest.tests;

import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.pojo.RestUser;
import com.qa.gorest.utils.StringUtils;

public class createUserTest extends BaseTest{

//	RestClient restClient;
	
	@BeforeMethod
	public void getUserSetup()
	{
		restClient = new RestClient(prop, baseURI);
	}
	
	@DataProvider
	public Object createUserData()
	{
		return new Object[][] {
			{"ShivShakti","female","active"},
			{"ParvatiShiv","female","active"},
			{"GauriShankar","male","active"}
		};
			
	}
	
	@Test(dataProvider="createUserData")
	public void createSingleUserTest(String name, String gender, String status)
	{
//		restClient = new RestClient();
		RestUser restuser = new RestUser(name, gender, StringUtils.getRandomEMail(), status);
		int userId = restClient.post(GOREST_SERVICE_URL,true, restuser,"json",true)
		.then().log().all().statusCode(APIHttpStatus.CREATED_201.getCode()).extract().path("id");
		
		System.out.println("user_id -> " + userId);	
		
		/// get call
		RestClient restClient= new RestClient(prop, baseURI);
		restClient.get(GOREST_SERVICE_URL+userId,true,true)
		.then().log().all().statusCode(APIHttpStatus.OK_200.getCode()).body("id", equalTo(userId));
		
	}
	
}
