package com.cf.test.jms;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

@WebServlet("/jmstestnew")
public class NewEventSenderServlet extends HttpServlet {

    private static final long serialVersionUID = 5339733144881034101L;

    @Inject
    EventSender sender;

    @Override
    public void doGet(final HttpServletRequest request, final HttpServletResponse response) {
        final long num = Long.valueOf(request.getParameter("num"));
        final int size = Integer.valueOf(request.getParameter("size"));
        sender.sendNew(num, size);
    }

}
