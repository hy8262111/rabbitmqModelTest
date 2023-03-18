package com.rabbitmq.routingkeyModel;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

//发布订阅模式,声明的交换机type为 fanout
public class Send {

    private final static String EXCHANGE = "directexchanage";
    private final static String INFO_ERR0R_QUEUE = "info_error_queue";
    private final static String ERROR_QUERE = "error_queue";


    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            //声明交换机
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.DIRECT);

            channel.queueDeclare(INFO_ERR0R_QUEUE, false, false, false, null);
            channel.queueDeclare(ERROR_QUERE, false, false, false, null);

            channel.queueBind(INFO_ERR0R_QUEUE, EXCHANGE, "info", null);
            channel.queueBind(ERROR_QUERE, EXCHANGE, "error", null);

            //发给 routing key 为 info 的队列
            /*String message = "routingkye message ";
            channel.basicPublish(EXCHANGE, "info", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");*/

            //发给 routing key 为 error 的队列
            String message = "routingkye message ";
            channel.basicPublish(EXCHANGE, "error", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");

            //同时发送给error info  把这两个队列同事绑定到同一个routing key 上
           /* String message = "routingkye message";
            channel.queueBind(INFO_ERR0R_QUEUE, EXCHANGE, "all", null);
            channel.queueBind(ERROR_QUERE, EXCHANGE, "all", null);
            channel.basicPublish(EXCHANGE, "all", null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");*/
        }


    }
}
