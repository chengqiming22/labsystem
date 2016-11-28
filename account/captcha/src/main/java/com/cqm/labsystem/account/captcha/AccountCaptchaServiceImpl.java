package com.cqm.labsystem.account.captcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.InitializingBean;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by qmcheng on 2016/11/28 0028.
 */
public class AccountCaptchaServiceImpl implements AccountCaptchaService, InitializingBean {
    private DefaultKaptcha producer;
    private Map<String, String> captchaMap = new HashMap<String, String>();
    private List<String> predefinedTexts;
    private int textCount = 0;

    public String generateCaptchaKey() {
        String key = RandomGenerator.getRandomString();
        String value = getCaptchaText();
        captchaMap.put(key, value);
        return key;
    }

    private String getCaptchaText() {
        if (predefinedTexts != null && !predefinedTexts.isEmpty()) {
            String text = predefinedTexts.get(textCount);
            textCount = (textCount + 1) % predefinedTexts.size();
            return text;
        } else {
            return producer.createText();
        }
    }

    public byte[] generateCaptchaImage(String captchaKey) {
        String text = captchaMap.get(captchaKey);
        BufferedImage image = producer.createImage(text);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", out);
        } catch (IOException e) {

        }
        return out.toByteArray();
    }

    public boolean validateCaptcha(String captchaKey, String captchaValue) {
        String text = captchaMap.get(captchaKey);
        if (text != null && text.equals(captchaValue)) {
            captchaMap.remove(captchaKey);
            return true;
        }
        return false;
    }

    public List<String> getPredefinedTexts() {
        return predefinedTexts;
    }

    public void setPredefinedTexts(List<String> predefinedTexts) {
        this.predefinedTexts = predefinedTexts;
    }

    public void afterPropertiesSet() throws Exception {
        producer = new DefaultKaptcha();
        producer.setConfig(new Config(new Properties()));
    }
}
