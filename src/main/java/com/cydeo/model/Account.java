package com.cydeo.model;

import com.cydeo.enums.AccountType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
@Data
@Builder
public class Account {
    //UUID gives  16 digit random id. to give unique fields.
    private UUID id;
    private BigDecimal balance;
    private AccountType accountType;
    private Date creationDate;
    private Long userId;

}
