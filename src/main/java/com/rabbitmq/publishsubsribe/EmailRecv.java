package com.rabbitmq.publishsubsribe;

import com.rabbitmq.client.*;

import java.nio.charset.StandardCharsets;

public class EmailRecv {

    private final static String EMAIL_QUEUE = "email";

    private final static String EXCHANGE = "noticeinfoexchanage";


    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.FANOUT);

        channel.queueDeclare(EMAIL_QUEUE, false, false, false, null);

        channel.queueBind(EMAIL_QUEUE, EXCHANGE, "");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] 收到 '" + message + "'");
        };
        channel.basicConsume(EMAIL_QUEUE, true, deliverCallback, consumerTag -> {
        });


    }
}
