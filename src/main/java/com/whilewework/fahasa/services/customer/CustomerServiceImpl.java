package com.whilewework.fahasa.services.customer;

import com.whilewework.fahasa.dto.ProductDto;
import com.whilewework.fahasa.entity.Product;
import com.whilewework.fahasa.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final ProductRepository productRepository;

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

}
