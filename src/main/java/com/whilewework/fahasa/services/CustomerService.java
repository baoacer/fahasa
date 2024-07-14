package com.whilewework.fahasa.services;

import com.whilewework.fahasa.dto.ProductDto;

import java.util.List;

public interface CustomerService {
    List<ProductDto> getAllProducts();

    List<ProductDto> getAllProductByTitle(String title);
}
