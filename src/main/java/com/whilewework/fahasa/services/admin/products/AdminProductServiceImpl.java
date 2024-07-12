package com.whilewework.fahasa.services.admin.products;

import com.whilewework.fahasa.dto.ProductDto;
import com.whilewework.fahasa.entity.Category;
import com.whilewework.fahasa.entity.Product;
import com.whilewework.fahasa.repository.CategoryRepository;
import com.whilewework.fahasa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminProductServiceImpl implements AdminProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;


    @Autowired
    public AdminProductServiceImpl(ProductRepository productRepository,CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) throws IOException {

        Product product = new Product();

        product.setTitle(productDto.getTitle());
        product.setPublisher(productDto.getPublisher());
        product.setPublisherBy(productDto.getPublisherBy());
        product.setAuthor(productDto.getAuthor());
        product.setCoverType(productDto.getCoverType());
        product.setPrice(productDto.getPrice());
        product.setOriginalPrice(productDto.getOriginalPrice());
        product.setDiscount(productDto.getDiscount());
        product.setCurrency(productDto.getCurrency());
        product.setImages(productDto.getImg().getBytes()); // up image

        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow();

        product.setCategory(category);
        return productRepository.save(product).getDto();
    }

    @Override
    public List<ProductDto> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllProductByName(String name){
        List<Product> products = productRepository.findAllByNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }


}
