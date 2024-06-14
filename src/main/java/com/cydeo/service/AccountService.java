package com.cydeo.service;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountService {
    //Account Id will be auto generated, we will skip as a parameter
    Account createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId);

    List<Account> listAllAccount();
}
