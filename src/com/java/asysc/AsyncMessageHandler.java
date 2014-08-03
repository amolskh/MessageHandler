package com.java.asysc;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class AsyncMessageHandler {
	
	private MessageSender msgSender = new MessageSender();
	
	private Thread sender = new Thread(msgSender);
	
	private Object lock = new Object();

	private Queue<Message> msgQueue = new LinkedBlockingQueue<Message>();
	
	public AsyncMessageHandler()
	{
		sender.start();
	}
	
	public void sendMessage(Message message)
	{
		System.out.println("Adding new message to queue");
		msgQueue.add(message);
		synchronized (lock) {
			lock.notify();
		}
	}
	
	public void consume()
	{
		while(true)
		{
			if(msgQueue.isEmpty())
			{
				synchronized (lock)
				{
					try
					{
						System.out.println("Entering Wait");
						lock.wait();
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
			else
			{
				System.out.println("Queue got new record");
				Message msg = msgQueue.poll();
				AsyncMessageHandler.this.onMessageReceived(msg);
			}
		}
	}
	
	public abstract  void onMessageReceived(Message message);
	
	
	public class MessageSender implements Runnable
	{

		@Override
		public void run()
		{
			consume();
		}
	}
}
