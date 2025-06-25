package com.example.payment_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.payment_service.dto.*;
import com.example.payment_service.model.Payment;
import com.example.payment_service.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository repository;
    private final WebClient webClient;

    public PaymentService(PaymentRepository repository, WebClient.Builder builder) {
        this.repository = repository;
        this.webClient = builder.build();
    }

    public Payment makePayment(PaymentRequestDTO request) {
        String transactionId = request.getTransactionId();
        double amountPaid = request.getAmountPaid();
        String method = request.getPaymentMethod();

        if (repository.existsByTransactionId(transactionId)) {
            throw new RuntimeException("Transaksi sudah dibayar. Tidak dapat membayar dua kali.");
        }

        TransactionDTO transaction = webClient.get()
                .uri("http://transactionservice/transactions/{id}", transactionId)
                .retrieve()
                .bodyToMono(TransactionDTO.class)
                .block();

        if (transaction == null || transaction.getItems() == null) {
            throw new RuntimeException("Transaksi tidak ditemukan atau tidak valid");
        }

        double total = 0;
        for (TransactionDTO.ItemDTO item : transaction.getItems()) {
            ProductDTO product = webClient.get()
                    .uri("http://product-service/products/{id}", item.getProductId())
                    .retrieve()
                    .bodyToMono(ProductDTO.class)
                    .block();

            if (product == null) {
                throw new RuntimeException("Produk dengan ID " + item.getProductId() + " tidak ditemukan.");
            }

            total += product.getHarga() * item.getQuantity();
        }

        if (amountPaid < total) {
            throw new RuntimeException("Uang yang dibayarkan kurang dari total pembayaran.");
        }

        Payment payment = new Payment();
        payment.setTransactionId(transactionId);
        payment.setTotalAmount(total);
        payment.setAmountPaid(amountPaid);
        payment.setChangeAmount(amountPaid - total);
        payment.setMethod(method);
        payment.setPaymentTime(LocalDateTime.now());

        return repository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return repository.findAll();
    }

}
