package com.example.transactionservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.transactionservice.model.Transaction;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
