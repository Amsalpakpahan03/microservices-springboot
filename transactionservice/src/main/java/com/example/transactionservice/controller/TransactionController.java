package com.example.transactionservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.transactionservice.dto.TransactionDTO;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.service.TransactionService;

import java.util.List;

@RestController
@RequestMapping("/transactions")  
public class TransactionController {

    @Autowired
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public Transaction createTransaction(@RequestBody TransactionDTO dto) {
        return service.createTransaction(dto);
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable String id) {
        return service.getTransactionById(id);
    }

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return service.getAllTransactions();
    }

   
    @DeleteMapping("/{id}")
    public String deleteTransaction(@PathVariable String id) {
        boolean deleted = service.deleteTransaction(id);
        return deleted ? "Transaksi berhasil dihapus" : "Transaksi tidak ditemukan";
    }
}
