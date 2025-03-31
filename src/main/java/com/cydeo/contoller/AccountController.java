package com.cydeo.contoller;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

@Controller
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    @GetMapping("/index")
    public String getIndexPage(Model model) {
        model.addAttribute("accountList",accountService.listAllAccount());
        return "account/index";
    }
    @GetMapping("/create-form")
    public String getCreateForm(Model model) {
        //we need to provide empty account object
        //builder acts like noArgCons
        model.addAttribute("account", Account.builder().build());
        //we need to provide accountType object for filling dropdown
        model.addAttribute("accountTypes", AccountType.values());

        return "account/create-account";
    }
    //create a method to capture information from ui;
    //print them on the console
    //trigger createNewAccount method, create the account based on the user input.
    //once user created return back to the index page.
    @PostMapping ("/create")
    public String createAccount(@Valid @ModelAttribute("account") Account account, BindingResult bindingResult,Model model ) {
        if (bindingResult.hasErrors()) {
            // we just loaded dropdowns no need to load other empty objects again
            model.addAttribute("accountTypes", AccountType.values());
            return "account/create-account";
        }
        System.out.println(account);
        accountService.createNewAccount(account.getBalance(),new Date(),account.getAccountType(),account.getUserId());
        return "redirect:/index";

    }
    @GetMapping("delete/{id}")
    public String getDeleteAccount(@PathVariable("id") UUID id) {
        System.out.println(id);
        accountService.deleteById(id);
        return "redirect:/index";
    }
    @GetMapping("activate/{id}")
    public String getActivateAccount(@PathVariable("id") UUID id) {
        accountService.activateAccount(id);
        return "redirect:/index";
    }



}
