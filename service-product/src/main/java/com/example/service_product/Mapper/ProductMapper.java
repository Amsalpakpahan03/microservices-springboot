package com.example.service_product.Mapper;

import com.example.service_product.dto.ProductDTO;
import com.example.service_product.model.Product;

public class ProductMapper {
    public static ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setNama(product.getNama());
        dto.setKategori(product.getKategori());
        dto.setStok(product.getStok());
        dto.setHarga(product.getHarga());
        return dto;
    }

    public static Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setNama(dto.getNama());
        product.setKategori(dto.getKategori());
        product.setStok(dto.getStok());
        product.setHarga(dto.getHarga());
        return product;
    }
}

