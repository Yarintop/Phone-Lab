package app.boundaries;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Java Bean used as Boundary - contains a default constructor + get/set
// JSON: { 
//	"message":"hello world", 
//	"messageTimestamp":"DATE&TIME IN JS??",
//	"name":{
//		"firstName":"Jane",
//		"lastName":"Smith"
//	},
//	counter:6,
//	important:true,
//	customAttributes:{
//		"A":????, "b":???, "c":{},...
//	},
//	"versionHistory":["v1.0", "v1.1", "v1.2", "v2.0"]
//}
public class MessageBoundary {
	private String message = "no message";
	private Date messageTimestamp = new Date();
	private long counter = -1;
	private Boolean important = false;
	private Map<String, Object> customAttributes = new HashMap<>();
	private List<String> versionHistory = new ArrayList<>();
			
	public MessageBoundary() { /* Default Constructor */}

	public MessageBoundary(String message) {
		this();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getMessageTimestamp() {
		return messageTimestamp;
	}

	public void setMessageTimestamp(Date messageTimestamp) {
		this.messageTimestamp = messageTimestamp;
	}
	
	public long getCounter() {
		return counter;
	}
	
	public void setCounter(long counter) {
		this.counter = counter;
	}

	public Boolean getImportant() {
		return important;
	}
	
	public void setImportant(Boolean important) {
		this.important = important;
	}
	
	public Map<String, Object> getCustomAttributes() {
		return customAttributes;
	}
	
	public void setCustomAttributes(Map<String, Object> customAttributes) {
		this.customAttributes = customAttributes;
	}
	
	public List<String> getVersionHistory() {
		return versionHistory;
	}
	
	public void setVersionHistory(List<String> versionHistory) {
		this.versionHistory = versionHistory;
	}
}
