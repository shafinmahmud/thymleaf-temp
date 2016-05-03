package shafin.web.dto;

public class Message {

	private String messageTitle;
	private String messageBody;
	
	
	public Message() {
	}

	public Message(String messageTitle, String messageBody) {
		this.messageTitle = messageTitle;
		this.messageBody = messageBody;
	}
	
	public String getMessageTitle() {
		return messageTitle;
	}
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	public String getMessageBody() {
		return messageBody;
	}
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	
	
}
