package com.cf.test.jms;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class EventSender {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(name = "java:/queue/testqueue")
    private Queue queue;

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void send(final long num, final int size) {

        final String text = createMessage(size);
        logger.info("[OLD] Sending {} messages [{} ({} bytes)]", num, text, size);

        final long start = System.currentTimeMillis();
        send(text);
        final long first = System.currentTimeMillis();
        for (int i = 1; i < num; i++) {
            send(text);
        }
        final long end = System.currentTimeMillis();

        final int totalTime = (int) (end - start);
        final int firstTime = (int) (first - start);
        logger.info("[OLD] Sent {} messages in {}ms (First message took {}ms)", num, totalTime, firstTime);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void sendNew(final long num, final int size) {
        try {
            final String text = createMessage(size);
            logger.info("[NEW] Sending {} messages [{} ({} bytes)]", num, text, size);

            final long start = System.currentTimeMillis();
            final Connection connection = connectionFactory.createConnection();
            final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            for (int i = 0; i < num; i++) {
                final MessageProducer producer = session.createProducer(queue);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                final Message message = session.createTextMessage(text);
                producer.send(message);
                producer.close();
            }
            session.close();
            connection.close();
            final long end = System.currentTimeMillis();

            final int totalTime = (int) (end - start);
            logger.info("[NEW] Sent {} messages in {}ms", num, totalTime);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void send(final String text) {
        try {
            final Connection connection = connectionFactory.createConnection();
            final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            final MessageProducer producer = session.createProducer(queue);
            final Message message = session.createTextMessage(text);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            producer.send(message);
            producer.close();
            session.close();
            connection.close();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String createMessage(final int size) {
        return RandomStringUtils.randomAscii(size);
    }

}
