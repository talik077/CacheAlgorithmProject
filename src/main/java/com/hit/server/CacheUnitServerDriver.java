package com.hit.server;

import com.hit.util.CLI;

public class CacheUnitServerDriver {
	
	
	public static void main(String[] args) {
		
		/**
		String jsonStr = "{\r\n" + 
				"  \"headers\": {\r\n" + 
				"    \"action\": \"UPDATE\"\r\n" + 
				"  },\r\n" + 
				"  \"body\": [\r\n" + 
				"    {\r\n" + 
				"      \"dataModelId\": 111111,\r\n" + 
				"      \"content\": \"Some String Data\"\r\n" + 
				"    }\r\n" + 
				"  ]\r\n" + 
				"}";
	    GsonBuilder gsonBldr = new GsonBuilder();
	    // gsonBldr.registerTypeAdapter(Request.class, new RequestDeserializer<String>());
	    
		Request<DataModel<String>[]> request = gsonBldr.create().fromJson(jsonStr, new TypeToken<Request<DataModel<String>[]>>() {}.getType());
		System.out.println(request);
		**/
		
		CLI cli = new CLI(System.in, System.out);
		Server server = new Server(34567);
		cli.addPropertyChangeListener(server);
		new Thread(cli).start();
	}
	
}
	