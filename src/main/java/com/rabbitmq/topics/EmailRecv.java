package com.rabbitmq.topics;

import com.rabbitmq.client.*;

public class EmailRecv {


    private final static String EXCHANGE = "topicsexchanage";
    private final static String EMAIL_QUEUE = "email_queue";

    private final static String ROUTING_EMAIL_KEY = "info.#.email.#";


    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC);

        channel.queueBind(EMAIL_QUEUE, EXCHANGE, ROUTING_EMAIL_KEY, null);


        channel.queueDeclare(EMAIL_QUEUE, false, false, false, null);


        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] 收到 '" + message + "'");
        };
        channel.basicConsume(EMAIL_QUEUE, true, deliverCallback, consumerTag -> {
        });
    }


}
