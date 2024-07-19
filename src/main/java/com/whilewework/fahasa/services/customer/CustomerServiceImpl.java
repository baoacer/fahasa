package com.whilewework.fahasa.services.customer;

import com.whilewework.fahasa.dto.ProductDto;
import com.whilewework.fahasa.entity.Product;
import com.whilewework.fahasa.mapper.ProductMapper;
import com.whilewework.fahasa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductDto> getAllProducts(){
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product : products) {
            ProductDto productDto = ProductMapper.toDto(product);
            productDtos.add(productDto);
        }

        return productDtos;
    }

    @Override
    public List<ProductDto> getAllProductByTitle(String title){
        List<Product> products = productRepository.findAllByTitleContaining(title);
        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product : products) {
            ProductDto productDto = ProductMapper.toDto(product);
            productDtos.add(productDto);
        }

        return productDtos;
    }

}
