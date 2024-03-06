package com.qa.gorest.tests;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.gorest.base.BaseTest;
import com.qa.gorest.client.RestClient;
import com.qa.gorest.constants.APIHttpStatus;
import com.qa.gorest.utils.JsonPathValidator;

import io.restassured.response.Response;

public class CircuitTest extends BaseTest {
	
	@BeforeMethod
	public void getUserSetup()
	{
		restClient = new RestClient(prop, baseURI);
	}
	
	@Test
	  public void getALLCicuitsTestTest() {
		  Response res = restClient.get(CIRCUITS_SERVICE_URL+"/2016/circuits.json", true,false);
		  //.then().assertThat().statusCode(APIHttpStatus.OK_200.getCode()).log().all();
		 
		  int statuscode= res.statusCode();
		  JsonPathValidator js = new JsonPathValidator();
		 List<String> singlecountry= js.readList(res, "$.MRData.CircuitTable.Circuits[?(@.circuitId=='sochi')].Location.country");
		 System.out.println("single country-->"+singlecountry);
		 Assert.assertEquals(singlecountry, "Russia");
		 Assert.assertEquals(statuscode, APIHttpStatus.OK_200.getCode());
		  
		  
	  }

}
