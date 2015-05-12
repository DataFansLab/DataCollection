/**
 * DataCollection/com.insit.util/RPCClient.java
 * 2014-6-9/上午11:46:15 by nano
 */
package com.insit.util;


import java.util.UUID;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * @author nano
 *
 */
public class RPCClient {

	private static ConnectionFactory factory = null;
	private static Connection connection = null;
    private static Channel channel = null;
    private static String requestQueueName = "rpc_queue";
    private static String replyQueueName;
    private static QueueingConsumer consumer;

    public static String call(String message) {
    	try {
    		String response = null;
            String corrId = UUID.randomUUID().toString();

            if (factory == null) {
            	factory = new ConnectionFactory();
            	// 设置服务器ip  
                factory.setHost("192.168.5.81");  
                  
                // 设置rabbitmq服务器运行的端口  
                factory.setPort(5672);  
                  
                // 设置rabbitmq服务器连接用户  
                factory.setUsername("admin");  
                  
                // 设置rabbitmq服务器连接用户密码  
                factory.setPassword("admin"); 
            }
            if (connection == null || !connection.isOpen()) {
                connection = factory.newConnection();
                channel = connection.createChannel();

                replyQueueName = channel.queueDeclare().getQueue();//这是反馈队列的名称
                consumer = new QueueingConsumer(channel);
                channel.basicConsume(replyQueueName, true, consumer);
            }
            
            BasicProperties props = MessageProperties.PERSISTENT_TEXT_PLAIN;
            props.setReplyTo(replyQueueName);//设置反馈队列的名称
            props.setCorrelationId(corrId);//correlationId作为一次请求的唯一标识，要每次请求都不同，用于关联服务端的反馈消息


            channel.basicPublish("", requestQueueName, props, message.getBytes());

            while (true) {
                QueueingConsumer.Delivery delivery = consumer.nextDelivery();
                if (delivery.getProperties().getCorrelationId().equals(corrId)) {//客户端收到反馈，校验correlationId的值是否与发送的一致
                    response = new String(delivery.getBody(), "UTF-8");
                    break;
                }
            }

            return response;
    	} catch (Exception e) {
    		return e.getMessage();
    	}
    }

    public static void close() throws Exception {
        connection.close();
    }
}
