package com.cf.test.jms;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Startup
public class EventConsumer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Resource(name = "java:/queue/testqueue")
    private Queue queue;

    @PostConstruct
    public void init() {
        startEventConsumer();
    }

    private void startEventConsumer() {
        try {
            final Connection connection = connectionFactory.createConnection();
            final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            for (int i = 0; i < 50; i++) {
                final MessageConsumer consumer = session.createConsumer(queue);
                consumer.setMessageListener(new MessageListener() {
                    @Override
                    public void onMessage(final Message arg0) {
                        logger.info("Message Consumed");
                    }
                });
            }
            connection.start();
        } catch (final JMSException e) {
            e.printStackTrace();
        }
    }

}
