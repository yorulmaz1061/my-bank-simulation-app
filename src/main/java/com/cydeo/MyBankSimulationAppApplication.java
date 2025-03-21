package com.cydeo;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.math.BigDecimal;
import java.util.Date;

//This is @Configuration+@EnableAutoConfigurarion+@ComponentScan+RunnerClass
//This is also config class but additional configuration we can use dedicated config class
@SpringBootApplication
public class MyBankSimulationAppApplication {

    public static void main(String[] args) {
        //since we haven't added MVC architecture yet we create bean as below;
        ApplicationContext container = SpringApplication.run(MyBankSimulationAppApplication.class, args);

        //get account service and transaction service beans
        AccountService accountService = container.getBean(AccountService.class);
        TransactionService transactionService = container.getBean(TransactionService.class);

        //create 2 accounts sender and receiver
        Account sender = accountService.createNewAccount(BigDecimal.valueOf(70), new Date(), AccountType.CHECKING, 1L);
        Account receiver = accountService.createNewAccount(BigDecimal.valueOf(50), new Date(), AccountType.CHECKING, 2L);
        Account receiver2 = null;

        accountService.listAllAccount().forEach(System.out::println);

        transactionService.makeTransfer(sender,receiver, new BigDecimal(28),new Date(),"money from dad");
        transactionService.findAllTransaction().forEach(System.out::println);
        accountService.listAllAccount().forEach(System.out::println);

    }

}
