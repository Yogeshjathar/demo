package com.example.demo.controller;

import com.example.demo.entity.Product;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final Map<Long, Product> productRepo = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    // ✅ CREATE Product
    @Counted(value = "product.create.count", description = "Number of products created")
    @Timed(value = "product.create.time", description = "Time taken to create a product")
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        long id = idGenerator.incrementAndGet();
        product.setId(id);
        productRepo.put(id, product);
        return product;
    }

    // ✅ READ ALL Products
    @Counted(value = "product.getall.count", description = "Number of times all products are requested")
    @Timed(value = "product.getall.time", description = "Time taken to fetch all products")
    @GetMapping
    public List<Product> getAllProducts() {
        return new ArrayList<>(productRepo.values());
    }

    // ✅ READ Product by ID
    @Counted(value = "product.getbyid.count", description = "Number of times product is requested by ID")
    @Timed(value = "product.getbyid.time", description = "Time taken to fetch product by ID")
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productRepo.get(id);
    }

    // ✅ UPDATE Product
    @Counted(value = "product.update.count", description = "Number of products updated")
    @Timed(value = "product.update.time", description = "Time taken to update product")
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        if (productRepo.containsKey(id)) {
            product.setId(id);
            productRepo.put(id, product);
            return product;
        } else {
            return null; // or throw exception
        }
    }

    // ✅ DELETE Product
    @Counted(value = "product.delete.count", description = "Number of products deleted")
    @Timed(value = "product.delete.time", description = "Time taken to delete product")
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        if (productRepo.containsKey(id)) {
            productRepo.remove(id);
            return "Deleted Product with id: " + id;
        } else {
            return "Product not found";
        }
    }
}
