package com.qa.gorest.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.utils.JsonPathValidator;

import io.restassured.response.Response;

public class getUserTestTest extends BaseTest{

	@BeforeMethod
	public void getUserSetup()
	{
		restClient = new RestClient(prop, baseURI);
	}
	
  @Test(priority=3)
  public void getALLUsersTestTest() {
	 Response res = restClient.get(GOREST_SERVICE_URL, true,true);//.statusCode(APIHttpStatus.OK_200.getCode()).log().all();
	 Assert.assertEquals(res.statusCode(), APIHttpStatus.OK_200.getCode());	
	 
	 JsonPathValidator js = new JsonPathValidator();
	 List<Map<Object,Object>> listOfALLUsers = js.readListOfMaps(res, "$[*]");
	for(Object e:listOfALLUsers)
	{
		System.out.println(e);
	}
	 
	 List<String> name = js.readList(res, "$[?(@.name=='ParvatiShiv')].name");
//	 System.out.println(name);
	 name.stream().forEach(e->System.out.println(e));
	 
	 for(String e:name)
		{
			System.out.println(e);
			Assert.assertEquals(e, "ParvatiShiv");
		}

  }

  @Test(priority=2)
  public void getSingleUserTestTest() {
	  restClient.get(GOREST_SERVICE_URL+"6755679", true,true).then().assertThat().statusCode(APIHttpStatus.OK_200.getCode()).log().all();
  }

  @Test(priority=1)
  public void getUserWIthQueryParamTestTest() {
	  Map<String,String> queryParams = new HashMap<String,String>();
	  queryParams.put("email", "VaranasiKASHI1709403718306@UttarPradesh.co.in");
	//  queryParams.put("id", "6755679");
	  restClient.get(GOREST_SERVICE_URL, true, null, queryParams,true).then().assertThat().statusCode(APIHttpStatus.OK_200.getCode()).log().all();
  }
}
