package xyz.itwill08.mvc;

import java.util.HashMap;
import java.util.Map;

public class HandlerMapping {
	private Map<String,Controller> mappings;
	
	public HandlerMapping() {
	mappings= new HashMap<>();
	mappings.put("/list.itwill", new ListController());
	mappings.put("/view.itwill", new ViewController());
	
	}
	public Controller getController(String command) {
		return mappings.get(command);
	}
}
