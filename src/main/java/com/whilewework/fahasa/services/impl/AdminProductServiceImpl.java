package com.whilewework.fahasa.services.impl;

import com.whilewework.fahasa.dto.ProductDto;
import com.whilewework.fahasa.entity.Category;
import com.whilewework.fahasa.entity.Product;
import com.whilewework.fahasa.repository.CategoryRepository;
import com.whilewework.fahasa.repository.ProductRepository;
import com.whilewework.fahasa.services.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

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
        product.setImages(product.getImages()); // up image

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
    public List<ProductDto> getAllProductByTitle(String title){
        List<Product> products = productRepository.findAllByTitleContaining(title);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    @Override
    public Boolean deleteProductById(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()){
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
