package com.qa.gorest.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RestUser {
	
	@JsonProperty("id")
	private int id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("gender")
	private String gender;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("status")
	private String status;
	
//////creating specific constructor to handle id which comes as part of response only and not in case of req payload
	public RestUser(String name, String gender, String email, String status)
	{
		this.name=name;
		this.gender=gender;
		this.email=email;
		this.status=status;
	}
	
}
