package com.cqm.labsystem.account.captcha;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by qmcheng on 2016/11/28 0028.
 */
public class AccountCaptchaServiceTest {
    private AccountCaptchaService service;

    @Before
    public void prepare() {
        ApplicationContext context = new ClassPathXmlApplicationContext("account-captcha.xml");
        service = context.getBean("captchaService", AccountCaptchaService.class);
    }

    @Test
    public void testGenerateCaptcha() throws Exception {
        String captchaKey = service.generateCaptchaKey();
        assertNotNull(captchaKey);

        byte[] captchaImage = service.generateCaptchaImage(captchaKey);
        assertTrue(captchaImage.length > 0);

        File image = new File("target/" + captchaKey + ".jpg");
        OutputStream output = null;
        try {
            output = new FileOutputStream(image);
            output.write(captchaImage);
        } finally {
            if (output != null) {
                output.close();
            }
        }
        assertTrue(image.exists() && image.length() > 0);
    }
}
