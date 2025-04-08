package com.cydeo;

import com.cydeo.dto.AccountDTO;
import com.cydeo.enums.AccountType;
import com.cydeo.service.AccountService;
import com.cydeo.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Date;

//This is @Configuration+@EnableAutoConfigurarion+@ComponentScan+RunnerClass
//This is also config class but additional configuration we can use dedicated config class
@SpringBootApplication
public class MyBankSimulationAppApplication {

    public static void main(String[] args) {
       ApplicationContext container =  SpringApplication.run(MyBankSimulationAppApplication.class, args);

        AccountService accountService = container.getBean(AccountService.class);
        TransactionService transactionService = container.getBean(TransactionService.class);

        //create 2 accounts sender and receiver
       /* AccountDTO sender = accountService.createNewAccount(BigDecimal.valueOf(70), new Date(), AccountType.CHECKING, 1L);
        AccountDTO receiver = accountService.createNewAccount(BigDecimal.valueOf(50), new Date(), AccountType.CHECKING, 2L);
        AccountDTO sender2 = accountService.createNewAccount(BigDecimal.valueOf(150), new Date(), AccountType.CHECKING, 3L);*/
        // Account receiver2 = null;

       /* accountService.listAllAccount().forEach(System.out::println);

        transactionService.makeTransfer(sender,receiver, new BigDecimal(28),new Date(),"money from dad");
        transactionService.findAllTransaction().forEach(System.out::println);
        accountService.listAllAccount().forEach(System.out::println);*/
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
