package org.example.ecomarcehandicraftbackend.controller;

import org.example.ecomarcehandicraftbackend.exception.ProductException;
import org.example.ecomarcehandicraftbackend.model.Product;
import org.example.ecomarcehandicraftbackend.model.request.CreateProductRequestModel;
import org.example.ecomarcehandicraftbackend.model.response.ApiResponse;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {
    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequestModel req){
        try {
            Product product = productService.createProduct(req);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>(new Product(),HttpStatus.EXPECTATION_FAILED);
    }
    @PostMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) throws ProductException {
        productService.deleteProduct(productId);
        ApiResponse response = new ApiResponse("Product Deleted Done", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Product>> filndAllProduct() throws ProductException{
        List<Product> products= productService.findAllProduct();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @PutMapping("/{productId}/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable Long productId) throws ProductException{
        return new ResponseEntity<>(productService.updateProduct(productId, product), HttpStatus.CREATED);
    }

    @PostMapping("/create-many")
    public ResponseEntity<ApiResponse> createMultipleProducts(@RequestBody CreateProductRequestModel[] createProductRequestModels)throws ProductException{
        for (CreateProductRequestModel cPRM : createProductRequestModels){
            productService.createProduct(cPRM);
        }
        System.out.println(createProductRequestModels.toString());
        return new ResponseEntity<>(new ApiResponse("All Product Added Done", true), HttpStatus.CREATED);
    }
}
