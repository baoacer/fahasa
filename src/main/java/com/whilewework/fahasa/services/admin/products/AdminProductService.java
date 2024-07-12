package com.whilewework.fahasa.services.admin.products;


import com.whilewework.fahasa.dto.ProductDto;
import com.whilewework.fahasa.entity.Product;

import java.io.IOException;
import java.util.List;

public interface AdminProductService {


    ProductDto addProduct(ProductDto productDto) throws IOException;

    List<ProductDto> getAllProducts();

    List<ProductDto> getAllProductByTitle(String title);

    Boolean deleteProductById(Long id);
}
