package com.qa.gorest.client;
import static io.restassured.RestAssured.given;

import java.util.Map;
import java.util.Properties;

import com.qa.gorest.frameworkexception.APIFrameWorkException;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {

//	private static final String BASE_URI = "https://gorest.co.in";
//	private static final String BEARER_TOKEN = "Bearer 8955b00b4d8291fee07e009a3dfcca4dd9e7439c18e93e3a261985c5c73b1662";
//	
	private static RequestSpecBuilder reqSpecBuilder;
	
	// static block executes before executing the class
//	static {
//		reqSpecBuilder = new RequestSpecBuilder();		
//	}
	
	private Properties prop;
	private String baseURI;
	
	// creating a constructor to handle multiple base uri at object creation
	public RestClient(Properties prop, String baseURI)
	{
		reqSpecBuilder = new RequestSpecBuilder();
		this.prop=prop;
		this.baseURI=baseURI;
	}
	
	
	/// since the auth code is mandatory for all calls , created a generic utility method to be used across all methods
	/// isAuthAdded --> to handle auth being added multiple times
	private boolean isAuthAdded=false;
	public void addAuthorization()
	{
		if(!isAuthAdded) {
		reqSpecBuilder.addHeader("Authorization", prop.getProperty("tokenId"));
		isAuthAdded = true;
		}
	}
	
	/// separate utility method to pass content type, to handle multiple values instead of single static value, user will pass it
	private void setRequestContentType(String contentType)
	{
		switch(contentType.toLowerCase()) {
		case "json" : reqSpecBuilder.setContentType(ContentType.JSON);
			break;
		case "xml" :  reqSpecBuilder.setContentType(ContentType.XML);
			break;
		case "multipart" : 	reqSpecBuilder.setContentType(ContentType.MULTIPART);
			break;
		case "text" : 	reqSpecBuilder.setContentType(ContentType.TEXT);
			break;
		default : System.out.println("pls pass valid value");
		throw new APIFrameWorkException("Invalid content Type custom message");
			
		}
	}
	/////////////Requests --> private in nature, access by public methods, encapsulation achieved
	/// passing with BASE URI only for basic GET call 
	private RequestSpecification createRequestSpec(boolean includeAuth)
	{
		reqSpecBuilder.setBaseUri(baseURI);
		if(includeAuth) { addAuthorization(); }
		return reqSpecBuilder.build();
	}
	// passing with headers for GET call 
	private RequestSpecification createRequestSpec(Map<String,String>headersMap,boolean includeAuth)
	{
		reqSpecBuilder.setBaseUri(baseURI);
		if(includeAuth) { addAuthorization(); }
		if(headersMap!=null)
		{
			reqSpecBuilder.addHeaders(headersMap);
		}
		return reqSpecBuilder.build();
	}
	
	// passing headers+query params for GET call 
	private RequestSpecification createRequestSpec(Map<String,String>headersMap, Map<String,String> queryParams,boolean includeAuth)
	{
		reqSpecBuilder.setBaseUri(baseURI);
		if(includeAuth) { addAuthorization(); }
		if(headersMap!=null)
		{
			reqSpecBuilder.addHeaders(headersMap);
		}
		if(queryParams!=null)
		{
			reqSpecBuilder.addQueryParams(queryParams);
		}
		return reqSpecBuilder.build();
	}
	
	//passing pojo and content type - post call 
	private RequestSpecification createRequestSpec(Object reqbody , String contentType, boolean includeAuth)
	{
		reqSpecBuilder.setBaseUri(baseURI);
		setRequestContentType(contentType);
		if(includeAuth) { addAuthorization(); }
		if(reqbody!=null) {
			reqSpecBuilder.setBody(reqbody);
		}
		
		return reqSpecBuilder.build();
	}
	
	//passing pojo + content type + headers - post call 
	private RequestSpecification createRequestSpec(Object reqbody , String contentType, Map<String,String> headersMap, boolean includeAuth)
	{
		reqSpecBuilder.setBaseUri(baseURI);
		if(includeAuth) { addAuthorization(); }
		setRequestContentType(contentType);
		if(headersMap!=null){
			reqSpecBuilder.addHeaders(headersMap);
		}
		if(reqbody!=null) {
			reqSpecBuilder.setBody(reqbody);
		}
		return reqSpecBuilder.build();
	}
	
/////////////////// http utils methods --- public in nature
	//// plain get call 
	public Response get(String serviceURL, boolean log, boolean includeAuth)
	{
		if(log)
		{
		return given(createRequestSpec(includeAuth)).log().all().when().get(serviceURL);
		}
		return given(createRequestSpec(includeAuth)).when().get(serviceURL);
	}
	//// get call with headers
	public Response get(String serviceURL, boolean log, Map<String,String> headersMap, boolean includeAuth)
	{
		if(log)
		{
		return given(createRequestSpec(headersMap,includeAuth)).log().all().when().get(serviceURL);
		}
		return given(createRequestSpec(headersMap,includeAuth)).when().get(serviceURL);
	}
	////get call with headers + query params 
	public Response get(String serviceURL, boolean log, Map<String,String> headersMap, Map<String,String> queryParams,boolean includeAuth )
	{
		if(log)
		{
		return given(createRequestSpec(headersMap,queryParams,includeAuth)).log().all().when().get(serviceURL);
		}
		return given(createRequestSpec(headersMap,queryParams,includeAuth)).when().get(serviceURL);
	}
	
	////POST call with Body + ContentType
	public Response post(String serviceURL, boolean log, Object reqbody, String contentType, boolean includeAuth)
	{
		if(log)
		{
		return given(createRequestSpec(reqbody,contentType,includeAuth)).log().all().when().post(serviceURL);
		}
		return given(createRequestSpec(reqbody,contentType,includeAuth)).when().post(serviceURL);
	}
	
	////POST call with Body + ContentType + Headers
	public Response post(String serviceURL, boolean log, Object reqbody, String contentType, Map<String,String> headersMap, boolean includeAuth)
	{
		if(log)
		{
		return given(createRequestSpec(reqbody,contentType,headersMap,includeAuth)).log().all().when().post(serviceURL);
		}
		return given(createRequestSpec(reqbody,contentType,headersMap,includeAuth)).when().post(serviceURL);
	}
	
////PUT call with Body + ContentType
	public Response put(String serviceURL, boolean log, Object reqbody, String contentType, boolean includeAuth)
	{
		if(log)
		{
		return given(createRequestSpec(reqbody,contentType,includeAuth)).log().all().when().put(serviceURL);
		}
		return given(createRequestSpec(reqbody,contentType,includeAuth)).when().put(serviceURL);
	}
	
	////PUT call with Body + ContentType + Headers
	public Response put(String serviceURL, boolean log, Object reqbody, String contentType, Map<String,String> headersMap, boolean includeAuth)
	{
		if(log)
		{
		return given(createRequestSpec(reqbody,contentType,headersMap,includeAuth)).log().all().when().put(serviceURL);
		}
		return given(createRequestSpec(reqbody,contentType,headersMap,includeAuth)).when().put(serviceURL);
	}
	
	////PATCH call with Body + ContentType
	public Response patch(String serviceURL, boolean log, Object reqbody, String contentType,boolean includeAuth)
	{
		if(log)
		{
		return given(createRequestSpec(reqbody,contentType,includeAuth)).log().all().when().patch(serviceURL);
		}
		return given(createRequestSpec(reqbody,contentType,includeAuth)).when().patch(serviceURL);
	}
	
	////PATCH call with Body + ContentType + Headers
	public Response patch(String serviceURL, boolean log, Object reqbody, String contentType, Map<String,String> headersMap,boolean includeAuth)
	{
		if(log)
		{
		return given(createRequestSpec(reqbody,contentType,headersMap,includeAuth)).log().all().when().patch(serviceURL);
		}
		return given(createRequestSpec(reqbody,contentType,headersMap,includeAuth)).when().patch(serviceURL);
	}
	
	////DELETE call with no params , body , only service url 
	public Response delete(String serviceURL, boolean log, boolean includeAuth)
	{
		if(log)
		{
		return given(createRequestSpec(includeAuth)).log().all().when().delete(serviceURL);
		}
		return given(createRequestSpec(includeAuth)).when().delete(serviceURL);
	}
	
}
