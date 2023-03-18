package com.rabbitmq.topics;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

//发布订阅模式,声明的交换机type为 fanout
public class Send {

    private final static String EXCHANGE = "topicsexchanage";
    private final static String EMAIL_QUEUE = "email_queue";
    private final static String SMS_QUERE = "sms_queue";

    private final static String ROUTING_EMAIL_KEY = "info.#.email.#";

    private final static String ROUTING_SMS_KEY = "info.#.sms.#";


    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            //声明交换机
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.TOPIC);

            channel.queueDeclare(EMAIL_QUEUE, false, false, false, null);
            channel.queueDeclare(SMS_QUERE, false, false, false, null);

            channel.queueBind(EMAIL_QUEUE, EXCHANGE, ROUTING_EMAIL_KEY, null);
            channel.queueBind(SMS_QUERE, EXCHANGE, ROUTING_SMS_KEY, null);

            //发给 routing key 为 info 的队列
            String message = "topics message ";
            //当我们需要给email用户发送时  routing_key为 info.email
            //当我们需要给sms用户发送时  routing_key为 info.sms
            //当我们需要给sms、email用户发送时  routing_key为  info.email.sms
            channel.basicPublish(EXCHANGE, "info.sms.email", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");

        }


    }
}
