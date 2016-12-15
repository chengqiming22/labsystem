package com.cqm.labsystem.service.web;

import com.cqm.labsystem.service.entities.Account;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by qmcheng on 2016/12/15 0015.
 */
@Controller
@RequestMapping("/account")
public class AccountController {
    @RequestMapping(value = "/getaccount")
    @ResponseBody
    public Account getAccount(){
        Account account = new Account();
        account.setId("cqm");
        account.setName("程啟明");
        return account;
    }
}
