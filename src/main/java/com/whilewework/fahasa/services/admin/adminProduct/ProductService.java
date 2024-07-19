package com.whilewework.fahasa.services.admin.adminProduct;


import com.whilewework.fahasa.dto.ProductDto;
import com.whilewework.fahasa.entity.Product;

import java.io.IOException;
import java.util.List;

public interface ProductService {


    ProductDto addProduct(ProductDto productDto) throws IOException;

    List<ProductDto> getAllProducts();

    List<ProductDto> getAllProductByTitle(String title);

    Boolean deleteProductById(Long id);

    List<Product> getProductDetails(boolean isSingleProductCheckout, Long productId);
}
