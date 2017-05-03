package com.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender {

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(Const.HOST);

		try (Connection connection = factory.newConnection()) {
			Channel channel = connection.createChannel();
			channel.queueDeclare(Const.QUEUE_NAME, false, false, false, null);
			String message = "Hello World!";
			channel.basicPublish("", Const.QUEUE_NAME, null, message.getBytes(Const.CHARSET_NAME));
			System.out.println("[x] Sent '" + message + "'");

		}

	}
}
