package com.cqm.labsystem.account.captcha;

import java.util.List;

/**
 * Created by qmcheng on 2016/11/25 0025.
 */
public interface AccountCaptchaService {
    String generateCaptchaKey();
    byte[] generateCaptchaImage(String captchaKey);
    boolean validateCaptcha(String captchaKey, String captchaValue);
    List<String> getPredefinedTexts();
    void setPredefinedTexts(List<String> predefinedTexts);
}
