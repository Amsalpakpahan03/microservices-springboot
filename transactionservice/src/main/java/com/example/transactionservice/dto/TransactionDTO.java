package com.example.transactionservice.dto;

import java.util.ArrayList;
import java.util.List;


public class TransactionDTO {
    private String customer;
    private List<ItemDTO> items = new ArrayList<>();

    public List<ItemDTO> getItems() {
        return this.items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }


    public static class ItemDTO {
        private Long productId;
        private int quantity;

        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
