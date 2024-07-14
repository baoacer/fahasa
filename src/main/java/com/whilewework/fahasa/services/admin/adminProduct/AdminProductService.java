package com.whilewework.fahasa.services.admin.adminProduct;


import com.whilewework.fahasa.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface AdminProductService {


    ProductDto addProduct(ProductDto productDto) throws IOException;

    List<ProductDto> getAllProducts();

    List<ProductDto> getAllProductByTitle(String title);

    Boolean deleteProductById(Long id);
}
