package org.example.ecomarcehandicraftbackend.service.implementation;

import org.example.ecomarcehandicraftbackend.exception.ProductException;
import org.example.ecomarcehandicraftbackend.model.Category;
import org.example.ecomarcehandicraftbackend.model.Product;
import org.example.ecomarcehandicraftbackend.repository.CategoryRepository;
import org.example.ecomarcehandicraftbackend.repository.ProductRepository;
import org.example.ecomarcehandicraftbackend.model.request.CreateProductRequestModel;
import org.example.ecomarcehandicraftbackend.service.service_interfaces.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EcommerceProductService implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final EcommerceUserService ecommerceUserService;

    public EcommerceProductService(ProductRepository productRepository, CategoryRepository categoryRepository, EcommerceUserService ecommerceUserService) {
        this.productRepository = productRepository;

        this.categoryRepository = categoryRepository;

        this.ecommerceUserService = ecommerceUserService;
    }

    @Override
    public Product createProduct(CreateProductRequestModel createProductRequestModel) throws ProductException {
        Category parent = categoryRepository.findByName(createProductRequestModel.getTopLabelCategory());
        if(parent==null){
            parent = new Category();
            parent.setName(createProductRequestModel.getTopLabelCategory());
            parent.setLevel(1);
//            parent.setParentCategory(parent);
            categoryRepository.save(parent);
        }
        Category secondLabel = categoryRepository.findByNameAndParent(createProductRequestModel.getSecondLabelCategory(), parent.getName());
        if(secondLabel==null){
            secondLabel = new Category();
            secondLabel.setName(createProductRequestModel.getSecondLabelCategory());
            secondLabel.setLevel(2);
            secondLabel.setParentCategory(parent);
            categoryRepository.save(secondLabel);
        }
        Category thirdLabel = categoryRepository.findByNameAndParent(createProductRequestModel.getThirdLabelCategory(), secondLabel.getName());
        System.out.println("ecommerce product service: " + secondLabel.getName());
        if(thirdLabel==null){
            thirdLabel = new Category();
            thirdLabel.setName(createProductRequestModel.getThirdLabelCategory());
            thirdLabel.setLevel(3);
            thirdLabel.setParentCategory(secondLabel);
            categoryRepository.save(thirdLabel);
        }

        Product product = new Product();
        product.setTitle(createProductRequestModel.getTitle());
        product.setColor(createProductRequestModel.getColor());
        product.setDescription(createProductRequestModel.getDescription());
        product.setDiscountPercent(createProductRequestModel.getDiscountPercent());
        product.setImageUrl(createProductRequestModel.getImageUrl());
        product.setBrand(createProductRequestModel.getBrand());
        product.setPrice(createProductRequestModel.getActualPrice());
        product.setDiscountedPrice(createProductRequestModel.getDiscountPrice());
        product.setSize(createProductRequestModel.getSize());
        product.setQuantity(createProductRequestModel.getQuantity());
        product.setCategory(thirdLabel);
        product.setCreatedAt(LocalDateTime.now());
        System.out.println(product);
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);

        product.getSize().clear();
        productRepository.delete(product);
        return "Product delete done";
    }

    @Override
    public Product updateProduct(Long productId, Product prd) throws ProductException {
        Product product = findProductById(productId);
        if(product != null) {
            return productRepository.save(prd);
        }
        else return null;
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        Optional<Product> opProduct = productRepository.findById(id);
        if(opProduct.isPresent()){
            return opProduct.get();
        }else{
            throw new ProductException("Product Not Found with ID: "+ id);
        }
    }

    @Override
    public List<Product> findAllProduct() throws ProductException {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return null;
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice,
                                       Integer maxPrice, Integer minDiscount, String sort, String stock,
                                       Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Product> products = productRepository.filterProducts(category, minPrice
        ,maxPrice, minDiscount, sort);
//        System.out.println("product list: " + products.toString());
        if(!colors.isEmpty()){
            products = products.stream().filter(
                    p -> colors.stream().anyMatch(
                            c -> c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList());
        }
//        if(stock != null){
//            if(stock.equals("in_stock")){
//                products = products.stream().filter(p->p.getQuantity() > 0).collect(Collectors.toList());
//            }
//        }else if(stock.equals("out_of_stock")){
//            products = products.stream().filter(p->p.getQuantity() < 1).collect(Collectors.toList());
//        }
        int startIndex = (int)pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());
        List<Product> productOfThatPage = products.subList(startIndex, endIndex);
//
        Page<Product> filteredProduct = new PageImpl<>(productOfThatPage, pageable, products.size());
//        System.out.println(filteredProduct);
//
        return filteredProduct;
//        return null;
    }
}
