package com.example.payment_service.dto;

import java.util.List;

public class TransactionDTO {
    private String customer;
    private List<ItemDTO> items;

    public String getCustomer() { return customer; }
    public void setCustomer(String customer) { this.customer = customer; }

    public List<ItemDTO> getItems() { return items; }
    public void setItems(List<ItemDTO> items) { this.items = items; }

    public static class ItemDTO {
        private Long productId;
        private int quantity;

        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }

        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }
}
