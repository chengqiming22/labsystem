package com.cqm.labsystem.account.captcha;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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

    @Test
    public void testValidateCaptchaCorrect() {
        List<String> predefinedTexts = new ArrayList<String>();
        predefinedTexts.add("12345");
        predefinedTexts.add("abcde");
        service.setPredefinedTexts(predefinedTexts);

        String captchaKey = service.generateCaptchaKey();
        service.generateCaptchaImage(captchaKey);
        assertTrue(service.validateCaptcha(captchaKey, "12345"));

        captchaKey = service.generateCaptchaKey();
        service.generateCaptchaImage(captchaKey);
        assertTrue(service.validateCaptcha(captchaKey, "abcde"));
    }

    @Test
    public void testValidateCaptchaInCorrect() {
        List<String> predefinedTexts = new ArrayList<String>();
        predefinedTexts.add("12345");
        service.setPredefinedTexts(predefinedTexts);

        String captchaKey = service.generateCaptchaKey();
        service.generateCaptchaImage(captchaKey);
        assertFalse(service.validateCaptcha(captchaKey, "67890"));
    }
}
