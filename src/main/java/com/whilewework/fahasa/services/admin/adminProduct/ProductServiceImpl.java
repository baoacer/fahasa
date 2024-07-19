package com.whilewework.fahasa.services.admin.adminProduct;

import com.whilewework.fahasa.dto.ProductDto;
import com.whilewework.fahasa.entity.Cart;
import com.whilewework.fahasa.entity.Category;
import com.whilewework.fahasa.entity.Product;
import com.whilewework.fahasa.entity.User;
import com.whilewework.fahasa.filters.JwtRequestFilter;
import com.whilewework.fahasa.mapper.ProductMapper;
import com.whilewework.fahasa.repository.CartRepository;
import com.whilewework.fahasa.repository.CategoryRepository;
import com.whilewework.fahasa.repository.ProductRepository;
import com.whilewework.fahasa.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private ProductMapper mapper;

    @Override
    public ProductDto addProduct(ProductDto productDto) throws IOException {

        Product product = ProductMapper.toEntity(productDto);

        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow();
        product.setCategory(category);

        Product saveProduct = productRepository.save(product);

        return ProductMapper.toDto(saveProduct);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }



    @Override
    public List<ProductDto> getAllProductByTitle(String title) {
        List<Product> products = productRepository.findAllByTitleContaining(title);
        List<ProductDto> productDtos = new ArrayList<>();

        for (Product product : products) {
            ProductDto productDto = ProductMapper.toDto(product);
            productDtos.add(productDto);
        }

        return productDtos;
    }


    @Override
    @Transactional
    public Boolean deleteProductById(Long id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()){
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // checkout
    @Override
    public List<Product> getProductDetails(boolean isSingleProductCheckout, Long productId){
        // checkout with single product (buy now)
        if(isSingleProductCheckout && productId != 0){

            List<Product> productList = new ArrayList<>();
            Product product = productRepository.findById(productId).get();
            productList.add(product);
            return productList;

        }else{
            // checkout with multiple products (add to cart)

            String username = JwtRequestFilter.CURRENT_USER;
            Optional<User> user = userRepository.findByUsername(username);
            List<Cart> carts = cartRepository.findByUser(user);
            return carts.stream().map(Cart::getProduct).collect(Collectors.toList());

        }
    }


}
