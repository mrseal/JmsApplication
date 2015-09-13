/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/
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
