package com.jk.ptk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jk.ptk.app.response.PtkResponse;
import com.jk.ptk.app.response.ResponseCode;

public class Test {
	public static void main(String[] args) {
		PtkResponse response = new PtkResponse();
		
		response.setResponseCode(ResponseCode.OPERATION_SUCCESSFUL);
		response.setMessage("You operation was successful.");
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
			System.out.println(json);
			PtkResponse res = mapper.readValue("{\n" + 
					"  \"responseCode\" : 21,\n" + 
					"  \"message\" : \"You operation was successfully.\"\n" + 
					"}", PtkResponse.class);
			
			System.out.println(res.getResponseCode());
			System.out.println(res.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
