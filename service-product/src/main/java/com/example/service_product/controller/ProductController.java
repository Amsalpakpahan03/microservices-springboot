package com.example.service_product.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.service_product.dto.ProductDTO;
import com.example.service_product.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping
    public List<ProductDTO> getAll() {
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public Optional<ProductDTO> getById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    @GetMapping("/search")
    public List<ProductDTO> searchByNama(@RequestParam String nama) {
        return service.searchByNama(nama);
    }

    @PostMapping
    public ProductDTO create(@RequestBody ProductDTO dto) {
        return service.createProduct(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteProduct(id);
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable Long id, @RequestBody ProductDTO dto) {
        return service.updateProduct(id, dto);
    }

    @GetMapping("/filter/kategori")
    public List<ProductDTO> filterByKategori(@RequestParam String kategori) {
        return service.filterByKategori(kategori);
    }

    @GetMapping("/stok/below")
    public List<ProductDTO> getStockBelow(@RequestParam int jumlah) {
        return service.findByStockLessThan(jumlah);
    }

    @GetMapping("/stok/above")
    public List<ProductDTO> getStockAbove(@RequestParam int jumlah) {
        return service.findByStockGreaterThan(jumlah);
    }

    // ✅ Tambahkan endpoint untuk decrease stock
   @PutMapping("/{id}/decrease-stock/{quantity}")
public ResponseEntity<String> decreaseStock(@PathVariable Long id, @PathVariable int quantity) {
    Optional<ProductDTO> optional = service.getProductById(id);

    if (optional.isEmpty()) {
        return ResponseEntity.status(404).body("Produk tidak ditemukan.");
    }

    ProductDTO product = optional.get();
    if (product.getStok() < quantity) {
        return ResponseEntity.badRequest().body("Stok tidak mencukupi.");
    }

    service.decreaseStock(id, quantity); // ini aman karena sudah dicek
    return ResponseEntity.ok("Stok berhasil dikurangi.");
}


    
}
