package com.cydeo.repository;

import com.cydeo.dto.TransactionDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionRepository {
    public static List<TransactionDTO> transactionDTOList = new ArrayList<>();

    public TransactionDTO save(TransactionDTO transactionDTO) {
        transactionDTOList.add(transactionDTO);
        return transactionDTO;
    }

    public List<TransactionDTO> findAllTransaction() {
        return transactionDTOList;
    }

    public List<TransactionDTO> findLast10Transactions() {
        List<TransactionDTO> last10TransactionDTOList = transactionDTOList.stream()
                .sorted(Comparator.comparing(TransactionDTO::getCreateDate).reversed())
                .limit(10).collect(Collectors.toList());
        return last10TransactionDTOList;
    }

    public List<TransactionDTO> retrieveTransactionListById(Long transactionId) {
       return transactionDTOList.stream()
                .filter(x->x.getSender().equals(transactionId)||
                        x.getReceiver().equals(transactionId)).collect(Collectors.toList());

    }
}
