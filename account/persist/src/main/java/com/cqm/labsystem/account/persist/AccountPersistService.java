package com.cqm.labsystem.account.persist;

/**
 * Created by qmcheng on 2016/11/21 0021.
 */
public interface AccountPersistService {
    Account createAccount() throws AccountPersistException;
    Account readAccount(String id) throws AccountPersistException;
    Account updateAccount(Account account) throws AccountPersistException;
    void deleteAccount(String id) throws AccountPersistException;
}
