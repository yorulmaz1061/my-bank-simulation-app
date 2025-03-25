package com.cydeo.contoller;

import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.UUID;

@Controller
public class TransactionController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    public TransactionController(AccountService accountService, TransactionService transactionService) {
        this.accountService = accountService;
        this.transactionService = transactionService;
    }

    @GetMapping("/make-transfer")
    public String getMakeTransfer(Model model) {
        //what we need to provide to make transfer happen?
        //we need to  provide empty transaction object, we will send it to receiver
        // we need to provide list of all accounts
        // we need list of last 10 transactions to fill the table(Business .logic is missing)
        model.addAttribute("transaction", Transaction.builder().build());
        model.addAttribute("accounts" , accountService.listAllAccount());
        model.addAttribute("transactionLists", transactionService.last10Transactions());

        return "transaction/make-transfer";
    }
    //write a post method that takes transaction object from the UI
    // complete the transfer and return the same page.
    @PostMapping("/transfer")
    public String postMakeTransfer(@ModelAttribute("transaction") Transaction transaction) {
        //I have UUID of accounts.But I need to provide Account object.
        //I need to find Accounts based on ID that I have and use as a parameter to complete makeTransfer method.
        Account sender = accountService.findById(transaction.getSender());
        Account receiver = accountService.findById(transaction.getReceiver());
        transactionService.makeTransfer(sender,receiver,transaction.getAmount(),new Date(),transaction.getMessage());

        return "redirect:/make-transfer";
    }
    @GetMapping("/transaction/{id}")
    public String getTransactions(@PathVariable("id") UUID id , Model model) {
        System.out.println(id);
        //get the list of transactions based on id and return as a model attribute
        model.addAttribute("transactions", transactionService.findTransactionListById(id));
        return "/transaction/transactions";

    }


}
