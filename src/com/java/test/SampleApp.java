package com.java.test;

import com.java.asysc.AsyncMessageHandler;
import com.java.asysc.Message;

public class SampleApp  extends Thread{
	
	public AsyncMessageHandler msg = new AsyncMessageHandler()
	{
		@Override
		public void onMessageReceived(Message message)
		{
			System.out.println("Received message " + message.getMessageName());
		}
	};
	
	@Override
	public void run()
	{
		System.out.println("REUnnning thread");
		msg.sendMessage(new Message(2));

		msg.sendMessage(new Message(3));
		System.out.println("REUnnning thread1");
		msg.sendMessage(new Message(4));
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg.shutDownMessageHandler();
	}
}
