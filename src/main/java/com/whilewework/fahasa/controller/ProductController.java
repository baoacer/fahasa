package com.whilewework.fahasa.controller;

import com.whilewework.fahasa.dto.ProductDto;
import com.whilewework.fahasa.entity.Product;
import com.whilewework.fahasa.services.admin.adminProduct.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) throws IOException {
        ProductDto productDto1 = productService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto1);
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductDto>> getAllProduct() {
        List<ProductDto> productsDtos = productService.getAllProducts();
        return ResponseEntity.ok(productsDtos);
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<List<ProductDto>> getAllProductByName(@PathVariable String title) {
        List<ProductDto> productsDtos = productService.getAllProductByTitle(title);
        return ResponseEntity.ok(productsDtos);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long productId) {
        Boolean delete = productService.deleteProductById(productId);
        if(delete){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/getProductDetails/{isSingleProductCheckout}/{productId}")
    public List<Product> getProductDetails(@PathVariable boolean isSingleProductCheckout,
                                           @PathVariable Long productId) {
        return productService.getProductDetails(isSingleProductCheckout, productId);
    }

}
