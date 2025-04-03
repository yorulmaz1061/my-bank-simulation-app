package com.cydeo.service.impl;

import com.cydeo.dto.AccountDTO;
import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDTO createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId) {
        //we need to create account object then save into the database(repository) return the object created
        //What is the point of UUID? randomUUID() is a method in UUID class. One user can have multiple accounts
        //it is auto generated in constructor.builder() is like a all args constructor.
        //If the object is used for the carrying data from UI-API-Backend, we manage it by creating AllArgConstructor
        // That's why we don't add @Component into POJO Account
        AccountDTO accountDTO = new AccountDTO();
        //save into dB (repository) and return the object


        return accountRepository.save(accountDTO);
    }

    @Override
    public List<AccountDTO> listAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        //find the account belongs the id
        //set status to deleted
        AccountDTO accountDTO = accountRepository.findById(id);
        accountDTO.setAccountStatus(AccountStatus.DELETED);
    }

    @Override
    public void activateAccount(Long id) {
        AccountDTO accountDTO = accountRepository.findById(id);
        accountDTO.setAccountStatus(AccountStatus.ACTIVE);
    }

    @Override
    public AccountDTO findById(Long id) {
      return accountRepository.findById(id);
    }

}
