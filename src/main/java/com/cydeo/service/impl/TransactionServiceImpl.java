package com.cydeo.service.impl;

import com.cydeo.enums.AccountType;
import com.cydeo.exception.AccountOwnershipException;
import com.cydeo.exception.BadRequestException;
import com.cydeo.exception.BalanceNotSufficentException;
import com.cydeo.exception.UnderConstructionException;
import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.repository.AccountRepository;
import com.cydeo.repository.TransactionRepository;
import com.cydeo.service.TransactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionServiceImpl implements TransactionService {
    @Value("${under_construction}")
    private boolean underConstruction;
    //if you put private final it forces you to create constructor to initialize
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;


    public TransactionServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }


    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {
        if (!underConstruction) {
       /*
       Questions:
       -if sender or receiver is null?
       -if sender and receiver is the same account?
       -if sender has enough balance to make transfer?
       -if both accounts are checking, if not, one of them is saving, it needs to be same userId
        */
            // we will write a small helper method to validate above
            // conditions inside the method enabled account validation. Those barriers are if statement.
            // Conditions should be passed before transaction phase.

            validateAccount(sender, receiver);
            checkAccountOwnership(sender, receiver);
            executeBalanceAndUpdateIfRequired(amount, sender, receiver);
            //make transfer
            //after all validations are completed and money is transferred we need to create Transaction object and save/return it.
            Transaction transaction = Transaction.builder().amount(amount).sender(sender.getId()).receiver(receiver.getId())
                    .createDate(creationDate).message(message).build();
            // save into dB and return it.
            return transactionRepository.save(transaction);
        } else {
            throw new UnderConstructionException("App is under construction. Please try again later");
        }
    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, Account sender, Account receiver) {
        if (checkSenderBalance(sender, amount)) {
            // if above is true then update sender and receiver amount
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));

        } else {
            throw new BalanceNotSufficentException("Balance is not enough for this transfer");
        }

    }

    private boolean checkSenderBalance(Account sender, BigDecimal amount) {
        //verify sender has enough balance to send
        //When subtraction happen leftover must be zero or bigger.
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;

    }

    private void checkAccountOwnership(Account sender, Account receiver) {
        /*
        write an if statement that checks if one of the account is saving,
        and user of sender or receiver is not the same, throw AccountOwnershipException
        If((isSenderOrReceiverAccount)&&(userIdMustBeDifferent) then throw exception
         */
        // be careful UserId is not bank accountId. It is like unique name
        if ((sender.getAccountType().equals(AccountType.SAVING) || receiver.getAccountType().equals(AccountType.SAVING))
                && !sender.getUserId().equals(receiver.getUserId())) {
            throw new AccountOwnershipException("If one of the account is saving, user must be the same for sender and receiver");
        }
    }

    private void validateAccount(Account sender, Account receiver) {
        /*
        -if any of the account is null
        -if account ids are the same (same account)
        -if the account exist in dB (repository)
         */
        //if conditions is wrong throw the exception. Backend part must be its own validation dedicated from ui
        if (sender == null || receiver == null) {
            // it is custom exception (to be) created in exception package
            throw new BadRequestException("Sender or Receiver cannot be null");
        }
        //if sender and receiver accounts are the same throw BadRequestException
        if (sender.getId().equals(receiver.getId())) {
            throw new BadRequestException("Sender and Receiver cannot be the same");
        }
        //check if accounts of sender and receiver exist in dB
        findAccountById(sender.getId());
        findAccountById(receiver.getId());

    }

    private void findAccountById(UUID id) {
        accountRepository.findById(id);
    }


    @Override
    public List<Transaction> findAllTransaction() {
        return transactionRepository.findAllTransaction();

    }
}
