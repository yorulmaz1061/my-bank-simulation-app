package com.cydeo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//This is @Configuration+@EnableAutoConfigurarion+@ComponentScan+RunnerClass
//This is also config class but additional configuration we can use dedicated config class
@SpringBootApplication
public class MyBankSimulationAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyBankSimulationAppApplication.class, args);
    }

}
