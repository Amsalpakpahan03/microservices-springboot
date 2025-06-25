package com.example.transactionservice.mapper;

import java.util.ArrayList;
import java.util.List;

import com.example.transactionservice.dto.TransactionDTO;
import com.example.transactionservice.model.Transaction;

public class TransactionMapper {
    public static Transaction toEntity(TransactionDTO dto) {
        Transaction entity = new Transaction();
        entity.setCustomer(dto.getCustomer());

        List<Transaction.Item> items = new ArrayList<>();
        for (TransactionDTO.ItemDTO itemDTO : dto.getItems()) {
            Transaction.Item item = new Transaction.Item();
            item.setProductId(itemDTO.getProductId()); // sekarang sudah Long
            item.setQuantity(itemDTO.getQuantity());
            items.add(item);
        }

        entity.setItems(items);
        return entity;
    }
}

