package com.atguigu;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static final String QUEUE_NAME = "simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
//        1，创建链接工厂
        ConnectionFactory  connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.200.128");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");//选择虚拟主机
        connectionFactory.setUsername("zhang3");
        connectionFactory.setPassword("zhang3");
//        2,创建链接
        Connection connection = connectionFactory.newConnection();
//        3，创建信道
        Channel channel = connection.createChannel();
//        4,创建队列
        /**
         * Declare a queue
         * @see com.rabbitmq.client.AMQP.Queue.Declare
         * @see com.rabbitmq.client.AMQP.Queue.DeclareOk
         * @param queue the name of the queue   1,队列的名字         这个  队列  将   幸存  于   服务器  重启
         * @param durable true if we are declaring a durable queue (the queue will survive a server restart)   ，2是否支持持久化
         * @param exclusive true if we are declaring an exclusive queue (restricted to this connection) ，3代表是否排他
         * @param autoDelete true if we are declaring an autodelete queue (server will delete it when no longer in use)，4代表是否自动删除
         * @param arguments other properties (construction arguments) for the queue  ，5是否有参数  如超时时间  长度等
         * @return a declaration-confirm method to indicate the queue was successfully declared
         * @throws java.io.IOException if an error is encountered
         */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
//        5，发送消息

        String message = "Hello RabbitMQ";
//        1，交换机名称     2，队列与交换机绑定时设置的的名字     3，消息的属性 消息的长度，类型等   4，消息本身
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
//        6，关闭资源
        channel.close();
        connection.close();
    }
}
