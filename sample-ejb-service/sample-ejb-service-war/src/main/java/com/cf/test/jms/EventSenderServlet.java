package com.cf.test.jms;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

@WebServlet("/jmstest")
public class EventSenderServlet extends HttpServlet {

    private static final long serialVersionUID = 5339733144881034101L;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Inject
    EventSenderService senderService;

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) {
        final int num = Integer.valueOf(request.getParameter("num"));
        try {
            senderService.send(num);
        } catch (final Exception e) {
            logger.info("==== TRANSACTION METHOD GOT EXCEPTION: ", e);
        }
    }

}
