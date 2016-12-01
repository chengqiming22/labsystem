package com.cqm.labsystem.account.service;

import com.cqm.labsystem.account.captcha.RandomGenerator;
import com.cqm.labsystem.account.email.AccountEmailService;
import com.cqm.labsystem.account.captcha.AccountCaptchaService;
import com.cqm.labsystem.account.persist.Account;
import com.cqm.labsystem.account.persist.AccountPersistException;
import com.cqm.labsystem.account.persist.AccountPersistService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qmcheng on 2016/11/30 0030.
 */
public class AccountServiceImpl implements AccountService {
    private AccountPersistService accountPersistService;
    private AccountEmailService accountEmailService;
    private AccountCaptchaService accountCaptchaService;

    private Map<String,String> activationMap=new HashMap<String, String>();

    public AccountPersistService getAccountPersistService() {
        return accountPersistService;
    }

    public void setAccountPersistService(AccountPersistService accountPersistService) {
        this.accountPersistService = accountPersistService;
    }

    public AccountEmailService getAccountEmailService() {
        return accountEmailService;
    }

    public void setAccountEmailService(AccountEmailService accountEmailService) {
        this.accountEmailService = accountEmailService;
    }

    public AccountCaptchaService getAccountCaptchaService() {
        return accountCaptchaService;
    }

    public void setAccountCaptchaService(AccountCaptchaService accountCaptchaService) {
        this.accountCaptchaService = accountCaptchaService;
    }

    public String generateCaptchaKey() {
        return accountCaptchaService.generateCaptchaKey();
    }

    public byte[] generateCaptchaImage(String captchaKey) {
        return accountCaptchaService.generateCaptchaImage(captchaKey);
    }

    public void signUp(SignUpRequest request) throws AccountServiceException {
        try {
            if (!request.getPassword().equals(request.getConfirmPassword())) {
                throw new AccountServiceException("2 passwords do not match.");
            }
            if (!accountCaptchaService.validateCaptcha(request.getCaptchaKey(), request.getCaptchaValue())) {
                throw new AccountServiceException("Incorrect captcha.");
            }

            Account account = new Account();
            account.setId(request.getId());
            account.setName(request.getName());
            account.setEmail(request.getEmail());
            account.setPassword(request.getPassword());
            account.setActivated(false);
            accountPersistService.createAccount(account);

            String activationId= RandomGenerator.getRandomString();
            activationMap.put(activationId,account.getId());

            String baseUrl = request.getActivateServiceUrl();
            String link =baseUrl.endsWith("/")?baseUrl+activationId:baseUrl+"?key="+activationId;
            accountEmailService.sendMail(account.getEmail(),"Please activate your account",link);

        } catch (AccountPersistException e) {
            throw new AccountServiceException("Unable to create account.");
        }catch (Exception e){
            throw new AccountServiceException("Unable to create account.");
        }
    }

    public void activate(String activationId) {

    }

    public void login(String username, String password) {

    }
}
