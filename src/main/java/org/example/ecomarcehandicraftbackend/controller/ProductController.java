package org.example.ecomarcehandicraftbackend.controller;

import org.example.ecomarcehandicraftbackend.exception.ProductException;
import org.example.ecomarcehandicraftbackend.model.PageDto;
import org.example.ecomarcehandicraftbackend.model.Product;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/")
    public ResponseEntity<Page<Product>> findProductsByCategoryHandler(@RequestParam String category, @RequestParam List<String> colors,
                      @RequestParam List<String> sizes, @RequestParam Integer minPrice, @RequestParam Integer maxPrice, @RequestParam Integer minDiscount,
                      @RequestParam String sort, @RequestParam String stock, @RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        System.out.println(category);
        Page<Product> productsPage = productService.getAllProduct(category, colors, sizes, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
//        System.out.println(convertToPageDto(productsPage).getContent());
        return new ResponseEntity<>(productsPage, HttpStatus.OK);
//        List<Product> alala = new ArrayList<>();
//        for(Product p : productsPage.getContent()){
//            alala.add(p);
//        }
//        return alala;
    }
//    private PageDto<Product> convertToPageDto(Page<Product> page) {
//        PageDto<Product> pageDto = new PageDto<Product>();
//        pageDto.setContent(page.getContent());
//        pageDto.setPageNumber(page.getNumber());
//        pageDto.setPageSize(page.getSize());
//        pageDto.setTotalElements(page.getTotalElements());
//        pageDto.setTotalPages(page.getTotalPages());
//        // set other necessary fields
//        return pageDto;
//    }
    @GetMapping("/id/{productId}")
    public ResponseEntity<Product> findProductById(@PathVariable Long productId) throws ProductException {
        Product product = productService.findProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.FOUND);
    }
}
