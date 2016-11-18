package com.cqm.labsystem.account;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.mail.Message;

import static org.junit.Assert.assertEquals;

/**
 * Created by qmcheng on 2016/11/18 0018.
 */
public class AccountEmailServiceTest {

    private GreenMail greenMail;

    @Before
    public void startMailServer() {
        ServerSetup serverSetup = new ServerSetup(3025, "localhost", ServerSetup.PROTOCOL_SMTP);
        greenMail = new GreenMail(serverSetup);
        greenMail.setUser("test@cqm.com", "123456");
        greenMail.start();
    }

    @Test
    public void testSendEmail() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("account-email.xml");
        AccountEmailService accountEmailService = (AccountEmailService) ctx.getBean("accountEmailService");

        String subject = "test subject";
        String htmlText = "<h3>test</h3>";
        accountEmailService.sendMail("784571419@qq.com", subject, htmlText);

        greenMail.waitForIncomingEmail(2000, 1);

        Message[] msgs = greenMail.getReceivedMessages();
        assertEquals(1, msgs.length);
        assertEquals(subject, msgs[0].getSubject());
        assertEquals(htmlText, GreenMailUtil.getBody(msgs[0]));
    }

    @After
    public void stopMailServer() {
        greenMail.stop();
    }
}
