package com.chen.jms.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author: cz
 * Date： 2020/12/21 15:54
 * 消费者用来消费消息
 */
public class AppConsumer {
    /**
     * 服务器地址
     */
    private static final String url = "tcp://localhost:61616";
    /**
     * 队列名字
     */
    private static final String queueName = "queue-test";

    public static void main(String[] args) throws JMSException {
        //1、创建ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        //2、创建Connection
        Connection connection = connectionFactory.createConnection();

        //3、启动连接
        connection.start();

        //4、创建会话(参数false不在事务中处理，后面是自动应答)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //5、创建一个目标(队列)
        Destination destination = session.createQueue(queueName);

        //6、创建一个消费者
        MessageConsumer consumer = session.createConsumer(destination);

        //7、创建一个监听器
        consumer.setMessageListener((message) -> {
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println("接受消息" + textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });

        //8、关闭连接
//        connection.close();

    }
}

