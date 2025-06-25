package com.example.service_product.repository;

import com.example.service_product.model.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findByNamaContainingIgnoreCase(String nama);

    List<Product> findByKategoriIgnoreCase(String kategori);

    List<Product> findByStokLessThan(int jumlah);

    List<Product> findByStokGreaterThan(int jumlah);

}
