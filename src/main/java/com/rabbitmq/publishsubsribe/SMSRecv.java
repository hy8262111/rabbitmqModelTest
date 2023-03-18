package com.rabbitmq.publishsubsribe;

import com.rabbitmq.client.*;

public class SMSRecv {

    private final static String SMS_QUEUE = "sms";

    private final static String EXCHANGE = "noticeinfoexchanage";


    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.FANOUT);

        channel.queueDeclare(SMS_QUEUE, false, false, false, null);

        channel.queueBind(SMS_QUEUE, EXCHANGE, "");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] 收到 '" + message + "'");
        };
        channel.basicConsume(SMS_QUEUE, true, deliverCallback, consumerTag -> {
        });


    }
}
