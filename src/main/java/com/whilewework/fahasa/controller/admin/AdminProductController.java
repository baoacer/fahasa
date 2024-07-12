package com.whilewework.fahasa.controller.admin;

import com.whilewework.fahasa.dto.ProductDto;
import com.whilewework.fahasa.entity.Product;
import com.whilewework.fahasa.services.admin.products.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminProductController {

    private AdminProductService adminProductService;

    @Autowired
    public AdminProductController(AdminProductService adminProductService) {
        this.adminProductService = adminProductService;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDto> addProduct(@ModelAttribute ProductDto productDto) throws IOException {
        ProductDto productDto1 = adminProductService.addProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDto1);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProduct() {
        List<ProductDto> productsDtos = adminProductService.getAllProducts();
        return ResponseEntity.ok(productsDtos);
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<List<ProductDto>> getAllProductByName(@PathVariable String title) {
        List<ProductDto> productsDtos = adminProductService.getAllProductByTitle(title);
        return ResponseEntity.ok(productsDtos);
    }



}
