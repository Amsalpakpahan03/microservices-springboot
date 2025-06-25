package com.example.service_product.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.service_product.Mapper.ProductMapper;
import com.example.service_product.dto.ProductDTO;
import com.example.service_product.model.Product;
import com.example.service_product.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

   
    public List<ProductDTO> getAllProducts() {
        return StreamSupport
                .stream(productRepository.findAll().spliterator(), false)
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

   
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper::toDTO);
    }

    public List<ProductDTO> searchByNama(String nama) {
        return productRepository.findByNamaContainingIgnoreCase(nama).stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

 
    public ProductDTO createProduct(ProductDTO dto) {
        Product product = ProductMapper.toEntity(dto);
        Product saved = productRepository.save(product);
        return ProductMapper.toDTO(saved);
    }

   
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

 
    public ProductDTO updateProduct(Long id, ProductDTO dto) {
        Optional<Product> existingProductOpt = productRepository.findById(id);
        if (existingProductOpt.isPresent()) {
            Product product = existingProductOpt.get();
            product.setNama(dto.getNama());
            product.setHarga(dto.getHarga());
            product.setKategori(dto.getKategori());
            product.setStok(dto.getStok());
            Product updated = productRepository.save(product);
            return ProductMapper.toDTO(updated);
        }
        return null;
    }

   
    public List<ProductDTO> filterByKategori(String kategori) {
        return productRepository.findByKategoriIgnoreCase(kategori).stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

   
    public List<ProductDTO> findByStockLessThan(int jumlah) {
        return productRepository.findByStokLessThan(jumlah).stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    
    public List<ProductDTO> findByStockGreaterThan(int jumlah) {
        return productRepository.findByStokGreaterThan(jumlah).stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void decreaseStock(Long id, int quantity) {
     
        ProductDTO product = getProductById(id).get();
        product.setStok(product.getStok() - quantity);
        updateProduct(id, product);
    }

}
