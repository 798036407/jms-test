package com.chen.jms.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author: cz
 * Date： 2020/12/21 15:30
 * 消息提供者，给消息中间件发送消息
 */
public class AppProducer {
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

        //6、创建一个生产者
        final MessageProducer producer = session.createProducer(destination);

        for (int i = 0; i < 100; i++) {
            //7、创建消息
            TextMessage textMessage = session.createTextMessage("test"+i);
            //8、发布消息
            producer.send(textMessage);
            System.out.println("发送消息成功：" + textMessage.getText());
        }
        //9、关闭连接
        connection.close();

    }
}
