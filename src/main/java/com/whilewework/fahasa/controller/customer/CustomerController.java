package com.whilewework.fahasa.controller.customer;

import com.whilewework.fahasa.dto.ProductDto;
import com.whilewework.fahasa.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/product")
    public ResponseEntity<List<ProductDto>> getAllProduct() {
        List<ProductDto> productsDtos = customerService.getAllProducts();
        return ResponseEntity.ok(productsDtos);
    }

    @GetMapping("/search/{title}")
    public ResponseEntity<List<ProductDto>> getAllProductByName(@PathVariable String title) {
        List<ProductDto> productsDtos = customerService.getAllProductByTitle(title);
        return ResponseEntity.ok(productsDtos);
    }



}
