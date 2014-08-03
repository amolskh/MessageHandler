package com.java.asysc;

import java.util.HashMap;
import java.util.Map;

public class Message {
	
	private int messageName;
	
	private Map<String,Object>  messageParams;

	public  Message()
	{
	}
	
	public Message(int messageName)
	{
		this.messageName = messageName;
	}

	public int getMessageName() {
		return messageName;
	}

	public void setMessageName(int messageName) {
		this.messageName = messageName;
	}
	
	public Map<String, Object> getMessageParams() {
		return messageParams;
	}

	public void setMessageParams(Map<String, Object> messageParams) {
		this.messageParams = messageParams;
	}
}
