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

import javax.ejb.*;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class EventSenderService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    EventSender sender;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void send(final int num) {
        logger.info("Sending {} messages", num);
        for (int i = 0; i < num; i++) {
            try {
                logger.info("Sending message {}", i);
                sender.send();
            } catch (final Exception e) {
                logger.info("==== SEND METHOD GOT EXCEPTION: ", e);
            }
        }
    }

}
