package com.example;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Receiver {

	public static void main(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(Const.HOST);

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(Const.QUEUE_NAME, false, false, false, null);
		System.out.println("[*] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery( //
					String consumerTag, //
					Envelope envelope, //
					AMQP.BasicProperties properties, //
					byte[] body) throws IOException {
				String message = new String(body, Const.CHARSET_NAME);
				System.out.println("[x] Recieved '" + message + "'");
			}
		};
		channel.basicConsume(Const.QUEUE_NAME, true, consumer);
	}

}
