package com.cqm.labsystem.account.service;

/**
 * Created by qmcheng on 2016/11/30 0030.
 */
public interface AccountService {
    String generateCaptchaKey();
    byte[] generateCaptchaImage(String captchaKey);
    void signUp(SignUpRequest request) throws AccountServiceException;
    void activate(String activationId);
    void login(String username,String password);
}
