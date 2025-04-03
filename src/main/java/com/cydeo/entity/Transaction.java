package com.cydeo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {
    private BigDecimal amount;

    private String message;

    @Column(columnDefinition = "TIMESTAMP")
    private Date createDate;

    @ManyToOne
    private Account sender;

    @ManyToOne
    private Account receiver;

}
