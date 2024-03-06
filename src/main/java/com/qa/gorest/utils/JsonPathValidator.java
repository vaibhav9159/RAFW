package com.qa.gorest.utils;

import java.util.List;
import java.util.Map;

import com.jayway.jsonpath.JsonPath;

import io.restassured.response.Response;

public class JsonPathValidator {
	
	// single value , T == Type, any type of data
	public <T> T read(Response response, String jsonPath)
	{	
		String jsonResponse = response.getBody().asString();
		return JsonPath.read(jsonResponse, jsonPath);
	}
	
	// list of values 
	public <T> List<T> readList(Response response, String jsonPath)
	{	
		String jsonResponse = response.getBody().asString();
		return JsonPath.read(jsonResponse, jsonPath);
	}
	
	// map of values 
		public <T> List<Map<Object,Object>> readListOfMaps(Response response, String jsonPath)
		{	
			String jsonResponse = response.getBody().asString();
			return JsonPath.read(jsonResponse, jsonPath);
		}

}
