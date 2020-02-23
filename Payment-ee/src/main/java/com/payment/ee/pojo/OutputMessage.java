package com.payment.ee.pojo;

public class OutputMessage {
	
	private String operation;
	private String status;
	private String message;
	
	public OutputMessage(String operation, String status, String message) {
		super();
		this.operation = operation;
		this.status = status;
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder msg = new StringBuilder();
		msg.append(this.operation); 
		msg.append(": status=\"");
		msg.append(this.status);
		msg.append("\"");
		if(this.message != null) {
			msg.append(", message=\"");
			msg.append(this.message);
			msg.append("\"");
		}
		return  msg.toString();
	}
	
	

}
