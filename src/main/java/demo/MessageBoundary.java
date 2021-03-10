package demo;

import java.util.Date;
import java.util.HashMap;
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
	private String message;
	private Date messageTimestamp;
	private Long counter;
	private Boolean important;
	private Map<String, Object> customAttributes;
	private String[] versionHistory;
			
	public MessageBoundary() {
		this.messageTimestamp = new Date();
		this.important = false;
		this.customAttributes = new HashMap<>();
		this.versionHistory = new String[0];
	}

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
	
	public Long getCounter() {
		return counter;
	}
	
	public void setCounter(Long counter) {
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
	
	public String[] getVersionHistory() {
		return versionHistory;
	}
	
	public void setVersionHistory(String[] versionHistory) {
		this.versionHistory = versionHistory;
	}
}
