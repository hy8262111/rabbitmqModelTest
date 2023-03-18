package com.rabbitmq.routingkeyModel;

import com.rabbitmq.client.*;

public class ErrorAndInfoRecv {


    private final static String EXCHANGE = "directexchanage";
    private final static String INFO_ERR0R_QUEUE = "info_error_queue";

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.DIRECT);

        channel.queueBind(INFO_ERR0R_QUEUE, EXCHANGE, "info", null);
        channel.queueBind(INFO_ERR0R_QUEUE, EXCHANGE, "all", null);

        channel.queueDeclare(INFO_ERR0R_QUEUE, false, false, false, null);


        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] 收到 '" + message + "'");
        };
        channel.basicConsume(INFO_ERR0R_QUEUE, true, deliverCallback, consumerTag -> {
        });
    }


}
