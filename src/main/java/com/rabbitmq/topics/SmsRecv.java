package com.rabbitmq.topics;

import com.rabbitmq.client.*;

public class SmsRecv {

    private final static String EXCHANGE = "topicsexchanage";
    private final static String SMS_QUERE = "sms_queue";

    private final static String ROUTING_SMS_KEY = "info.#.sms.#";

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC);

        channel.queueBind(SMS_QUERE, EXCHANGE, ROUTING_SMS_KEY, null);


        channel.queueDeclare(SMS_QUERE, false, false, false, null);


        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] 收到 '" + message + "'");
        };
        channel.basicConsume(SMS_QUERE, true, deliverCallback, consumerTag -> {
        });
    }
}
