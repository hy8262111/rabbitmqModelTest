package com.rabbitmq.routingkeyModel;

import com.rabbitmq.client.*;

public class ErrorRecv {

    private final static String EXCHANGE = "directexchanage";
    private final static String ERROR_QUERE = "error_queue";

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.DIRECT);

        channel.queueBind(ERROR_QUERE, EXCHANGE, "error", null);
        channel.queueBind(ERROR_QUERE, EXCHANGE, "all", null);

        channel.queueDeclare(ERROR_QUERE, false, false, false, null);


        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] 收到 '" + message + "'");
        };
        channel.basicConsume(ERROR_QUERE, true, deliverCallback, consumerTag -> {
        });
    }
}
