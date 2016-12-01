package com.cqm.labsystem.account.persist;

/**
 * Created by qmcheng on 2016/11/21 0021.
 */
public class AccountPersistException extends Exception {
    public AccountPersistException(Exception innerException){
        super(innerException);
    }

    public AccountPersistException(String msg){
        super(msg);
    }
}
