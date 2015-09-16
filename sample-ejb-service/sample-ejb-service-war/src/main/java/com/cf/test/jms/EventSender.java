package com.cf.test.jms;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.jms.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class EventSender {

    private final static String MESSAGE = "Hello, TEST Hello, TEST Hello, TEST Hello, TEST";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "java:/JmsXA")
    private ConnectionFactory connectionFactory;

    @Resource(name = "java:/queue/testqueue")
    private Queue queue;

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void send() {
        try {
            final Connection connection = connectionFactory.createConnection();
            final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            final Message message = session.createTextMessage(MESSAGE);
            final MessageProducer producer = session.createProducer(queue);
            producer.send(message);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
