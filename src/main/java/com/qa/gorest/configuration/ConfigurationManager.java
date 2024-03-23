package com.qa.gorest.configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import com.qa.gorest.frameworkexception.APIFrameWorkException;

public class ConfigurationManager {
	
	private Properties prop;
	private FileInputStream fis;
	
	public Properties initProp()
	{
		prop = new Properties();
		/// maven --> , pass multi env values
	
		String envName= System.getProperty("env"); // getprop method  will the read the cmd arg line value 
		System.out.println("Running tests on env " + envName);
		
		try {
			
		if(envName==null)
		{
			System.out.println("running tests on default QA env");
			fis = new FileInputStream("./src/test/resources/config/qa.config.properties");
		}
		else {System.out.println("running tests on env " + envName);
		
		
				switch(envName.toLowerCase().trim()) {
				
				case "qa": fis = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "dev":	fis = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "prod": fis = new FileInputStream("./src/test/resources/config/prod.config.properties");
					break;
					
					default:
						System.out.println("plz pass right env " + envName);
						throw new APIFrameWorkException("Invalid env value customized exception");
				}
		}
		
		}
		
		catch(Exception e) {e.printStackTrace();	}
		
			
			try {
				prop.load(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		
		return prop;
}
	
	
}