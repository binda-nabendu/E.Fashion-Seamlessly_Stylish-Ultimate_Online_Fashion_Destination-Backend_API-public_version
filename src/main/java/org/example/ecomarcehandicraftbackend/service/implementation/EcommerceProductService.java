package org.example.ecomarcehandicraftbackend.service.implementation;

import org.example.ecomarcehandicraftbackend.exception.ProductException;
import org.example.ecomarcehandicraftbackend.model.Category;
import org.example.ecomarcehandicraftbackend.model.Product;
import org.example.ecomarcehandicraftbackend.repository.CategoryRepository;
import org.example.ecomarcehandicraftbackend.repository.ProductRepository;
import org.example.ecomarcehandicraftbackend.request.CreateProductRequestModel;
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
            Category category = new Category();
            category.setName(createProductRequestModel.getTopLabelCategory());
            category.setLevel(1);
            categoryRepository.save(category);
        }
        Category secondLabel = categoryRepository.findByNameAndParent(createProductRequestModel.getSecondLabelCategory(), parent.getName());
        if(secondLabel==null){
            Category category = new Category();
            category.setName(createProductRequestModel.getSecondLabelCategory());
            category.setLevel(2);
            categoryRepository.save(category);
        }
        Category thirdLabel = categoryRepository.findByNameAndParent(createProductRequestModel.getSubCategory(), secondLabel.getName());
        if(thirdLabel==null){
            Category category = new Category();
            category.setName(createProductRequestModel.getSubCategory());
            category.setLevel(3);
            categoryRepository.save(category);
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
        product.setSizes(createProductRequestModel.getSize());
        product.setQuantity(createProductRequestModel.getQuantity());
        product.setCategory(thirdLabel);
        product.setCreatedAt(LocalDateTime.now());
        Product savedProduct = productRepository.save(product);
        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);

        product.getSizes().clear();
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
    public List<Product> findProductByCategory(String category) {
        return null;
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> color, List<String> size, Integer minPrice,
                                       Integer maxPrice, Integer minDiscount, String sort, String stock,
                                       Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Product> products = productRepository.filterProducts(category, minPrice
        ,maxPrice, minDiscount, sort);
        if(!color.isEmpty()){
            products = products.stream().filter(
                    p -> color.stream().anyMatch(
                            c -> c.equalsIgnoreCase(p.getColor())))
                    .collect(Collectors.toList());
        }
        if(stock != null){
            if(stock.equals("in_stock")){
                products = products.stream().filter(p->p.getQuantity() > 0).collect(Collectors.toList());
            }
        }else if(stock.equals("out_of_stock")){
            products = products.stream().filter(p->p.getQuantity() < 1).collect(Collectors.toList());
        }
        int startIndex = (int)pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());
        List<Product> productOfThatPage = products.subList(startIndex, endIndex);

        Page<Product> filteredProduct = new PageImpl<>(productOfThatPage, pageable, products.size());

        return null;
    }
}
