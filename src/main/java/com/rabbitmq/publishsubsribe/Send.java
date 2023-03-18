package com.rabbitmq.publishsubsribe;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

//发布订阅模式,声明的交换机type为 fanout
public class Send {

    private final static String SMS_QUEUE = "sms";
    private final static String EMAIL_QUEUE = "email";

    private final static String EXCHANGE = "noticeinfoexchanage";


    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            //声明交换机
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.FANOUT);

            channel.queueDeclare(SMS_QUEUE, false, false, false, null);
            channel.queueDeclare(EMAIL_QUEUE, false, false, false, null);

            channel.queueBind(SMS_QUEUE, EXCHANGE, "");
            channel.queueBind(EMAIL_QUEUE, EXCHANGE, "");

            for (int i = 0; i < 5; i++) {
                String message = "publish subscribe message " + i;
                channel.basicPublish(EXCHANGE, "", null, message.getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + message + "'");
            }

        }
    }
}
