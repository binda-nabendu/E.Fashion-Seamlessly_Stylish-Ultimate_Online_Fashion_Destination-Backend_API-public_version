package org.example.ecomarcehandicraftbackend.controller;

import org.example.ecomarcehandicraftbackend.exception.ProductException;
import org.example.ecomarcehandicraftbackend.model.Product;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/")
    public ResponseEntity<Page<Product>> findProductsByCategoryHandler(@RequestParam String category, @RequestParam List<String> color,
                      @RequestParam List<String> size, @RequestParam Integer minPrice, @RequestParam Integer maxPrice, @RequestParam Integer minDiscount,
                      @RequestParam String sort, @RequestParam String stock, @RequestParam Integer pageNumber, @RequestParam Integer pageSize){

        Page<Product> productsPage = productService.getAllProduct(category, color, size, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
        return new ResponseEntity<>(productsPage, HttpStatus.OK);
    }
    @GetMapping("/id/{productId}")
    public ResponseEntity<Product> findProductById(@PathVariable Long productId) throws ProductException {
        Product product = productService.findProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.FOUND);
    }
}
