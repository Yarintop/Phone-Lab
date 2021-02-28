package demo;

// Java Bean used as Boundary - contains a default constructor + get/set
// JSON: { "message":"hello world"}
public class MessageBoundary {
	private String message;
	
	public MessageBoundary() {
	}

	public MessageBoundary(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
