package com.cydeo.repository;

import com.cydeo.model.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TransactionRepository {
    public static List<Transaction> transactionList = new ArrayList<>();

    public Transaction save(Transaction transaction) {
        transactionList.add(transaction);
        return transaction;
    }

    public List<Transaction> findAllTransaction() {
        return transactionList;
    }

    public List<Transaction> findLast10Transactions() {
        List<Transaction> last10transactionList = transactionList.stream()
                .sorted(Comparator.comparing(Transaction::getCreateDate).reversed())
                .limit(10).collect(Collectors.toList());
        return last10transactionList;
    }

    public List<Transaction> retrieveTransactionListById(UUID transactionId) {
       return transactionList.stream()
                .filter(x->x.getSender().equals(transactionId)||
                        x.getReceiver().equals(transactionId)).collect(Collectors.toList());

    }
}
