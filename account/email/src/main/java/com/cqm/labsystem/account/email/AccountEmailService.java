package com.cqm.labsystem.account.email;

/**
 * Created by qmcheng on 2016/11/18 0018.
 */
public interface AccountEmailService {
    void sendMail(String to,String subject,String htmlText) throws Exception;
}
