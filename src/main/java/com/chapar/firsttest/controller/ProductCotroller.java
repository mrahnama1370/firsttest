package com.chapar.firsttest.controller;

import com.chapar.firsttest.dto.ProductDto;
import com.chapar.firsttest.model.Product;
import com.chapar.firsttest.service.ProductServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductCotroller {

    final ProductServiceImpl productService;
    final ModelMapper modelMapper;

    @Autowired
    public ProductCotroller(ProductServiceImpl productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/products/{productId}")
    private ResponseEntity<ProductDto> readProduct(
            @PathVariable Long productId) {

        Product product = productService.findProductById(productId);

        return new ResponseEntity<>(modelMapper.map(product, ProductDto.class), HttpStatus.OK);

    }

    @PostMapping("/products")
    private ResponseEntity<ProductDto> addNewProduct(
            @RequestBody ProductDto productDto) {

        Product product = productService.addNewProduct(modelMapper.map(productDto, Product.class));

        return new ResponseEntity<>(modelMapper.map(product, ProductDto.class), HttpStatus.OK);

    }

    @PatchMapping("/products/{productId}")
    private ResponseEntity<Long> updateAnProduct(
            @PathVariable Long productId,
            @RequestBody ProductDto productDto) {

        productService.updateProduct(productId, modelMapper.map(productDto, Product.class));

        return new ResponseEntity<>(productId, HttpStatus.OK);

    }

    @DeleteMapping("/products/{productId}")
    private ResponseEntity<Long> deleteAnProduct(
            @PathVariable Long productId) {

        productService.deleteProduct(productId);

        return new ResponseEntity<>(productId, HttpStatus.OK);

    }
}
