package com.example.transactionservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.transactionservice.dto.TransactionDTO;
import com.example.transactionservice.mapper.TransactionMapper;
import com.example.transactionservice.model.Transaction;
import com.example.transactionservice.repository.TransactionRepository;

import reactor.core.publisher.Mono;

@Service
public class TransactionService {

    @Autowired
    private final TransactionRepository repository;
    private final WebClient webClient;

    public TransactionService(TransactionRepository repository, WebClient.Builder webClientBuilder) {
        this.repository = repository;

        this.webClient = webClientBuilder
                .baseUrl("lb://product-service") 
                .build();
    }

    public Transaction createTransaction(TransactionDTO dto) {
        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new IllegalArgumentException("List item tidak boleh kosong atau null");
        }

        for (TransactionDTO.ItemDTO item : dto.getItems()) {
            decreaseStock(item.getProductId(), item.getQuantity());
        }

        Transaction transaction = TransactionMapper.toEntity(dto);
        return repository.save(transaction);
    }

    public void decreaseStock(Long productId, int quantity) {
        try {
            webClient.put()
                    .uri("/products/{id}/decrease-stock/{qty}", productId, quantity)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> response.createException().flatMap(error
                                    -> Mono.error(new RuntimeException("Gagal mengurangi stok: " + error.getMessage()))
                            )
                    )
                    .bodyToMono(Void.class)
                    .block();
        } catch (Exception e) {
            System.err.println(" Error saat mengurangi stok produk: " + e.getMessage());
            throw new RuntimeException("Gagal memanggil product-service", e);
        }
    }

    public List<Transaction> getAllTransactions() {
        return repository.findAll();
    }

    public Transaction getTransactionById(String id) {
        Optional<Transaction> result = repository.findById(id);
        return result.orElse(null);
    }

    public boolean deleteTransaction(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
