package com.chapar.firsttest.service;

import com.chapar.firsttest.model.Product;

public interface ProductService {

    Product findProductById(long productId);

    Product addNewProduct(Product product);

    Long updateProduct(Long productId, Product product);

    void deleteProduct(long productId);

}
