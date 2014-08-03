package com.java.asysc;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class AsyncMessageHandler {
	
	private MessageSender msgSender = new MessageSender();
	
	private Thread sender = new Thread(msgSender);
	
	private Object lock = new Object();
	
	private boolean isExit = false;

	private Queue<Message> msgQueue = new LinkedBlockingQueue<Message>();
	
	public AsyncMessageHandler()
	{
		sender.start();
	}
	
	public void sendMessage(Message message)
	{
		msgQueue.add(message);
		synchronized (lock)
		{
			lock.notify();
		}
	}
	
	private void consume()
	{
		while(!isExit)
		{
			if(msgQueue.isEmpty())
			{
				synchronized (lock)
				{
					try
					{
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
				Message msg = msgQueue.poll();
				AsyncMessageHandler.this.onMessageReceived(msg);
			}
		}
	}
	
	/**
	 * This is abstract method to be implemented in class consuming the messages
	 * @param message
	 */
	public abstract  void onMessageReceived(Message message);
	
	
	public class MessageSender implements Runnable
	{

		@Override
		public void run()
		{
			consume();
		}
	}
	
	/**
	 * Consider callling this message when you no longer need message handler
	 * or from destroy method of Bean LifeCycle
	 */
	public void shutDownMessageHandler()
	{
		isExit = true;
		synchronized (lock) {
			lock.notify();
		}
	}
}
