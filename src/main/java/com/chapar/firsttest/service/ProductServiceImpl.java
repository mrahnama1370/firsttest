package com.chapar.firsttest.service;

import com.chapar.firsttest.model.Product;
import com.chapar.firsttest.model.QProduct;
import com.chapar.firsttest.repository.ProductRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @PersistenceContext
    private EntityManager entityManager;

    final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product findProductById(long productId) {

        QProduct product = QProduct.product;

        JPAQueryFactory query = new JPAQueryFactory(entityManager);

        return Optional.ofNullable
                (query.selectFrom(product).where(product.id.eq(productId)).fetchOne())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product " + productId + " not found!"));
    }

    @Override
    public Product addNewProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Long updateProduct(Long productId, Product product) {

        QProduct qProduct = QProduct.product;

        JPAQueryFactory query = new JPAQueryFactory(entityManager);

        long resault = query.update(qProduct)
                .set(qProduct.name, product.getName())
                .set(qProduct.price, product.getPrice())
                .where(qProduct.id.eq(productId)).execute();

        if (resault == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product " + productId + " not found!");

        return resault;

    }

    @Override
    public void deleteProduct(long productId) {
        productRepository.deleteById(productId);
    }
}
