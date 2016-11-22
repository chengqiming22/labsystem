package com.cqm.labsystem.account.persist;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by qmcheng on 2016/11/21 0021.
 */
public class AccountPersistServiceTest {
    @Test
    public void testReadAccount() throws Exception{
        ApplicationContext context = new ClassPathXmlApplicationContext("persist-servlet.xml");
        AccountPersistService service = (AccountPersistService) context.getBean("accountPersistService");

        Account account= service.readAccount("1");
        assertNotNull(account);
        assertEquals("1",account.getId());
    }
}
