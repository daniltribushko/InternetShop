package org.example.goods.service.db.imp;

import org.example.goods.exceptions.products.ProductAlreadyExistException;
import org.example.goods.exceptions.products.ProductByIdNotFoundException;
import org.example.goods.models.entities.Product;
import org.example.goods.models.entities.ProductCategory;
import org.example.goods.repositories.ProductPaginationRepository;
import org.example.goods.repositories.ProductRepository;
import org.example.goods.service.db.ProductDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author Tribushko Danil
 * @since 03.06.2024
 * <p>
 * Реализация сервиса для работы с товарами в бд
 */
@Service
public class ProductDBServiceImp implements ProductDBService {
    private final ProductRepository productRepository;
    private final ProductPaginationRepository productPaginationRepository;

    @Autowired
    public ProductDBServiceImp(ProductRepository productRepository,
                               ProductPaginationRepository productPaginationRepository) {
        this.productRepository = productRepository;
        this.productPaginationRepository = productPaginationRepository;
    }

    @Override
    public void update(Product product) {
        productRepository.save(product);
    }

    @Override
    public boolean existByNameAndDescription(String name, String description) {
        return productRepository.existsByNameAndDescription(name, description);
    }

    @Override
    public void save(Product product) {
        String name = product.getName();
        String description = product.getDescription();

        if (existByNameAndDescription(name, description)) {
            throw new ProductAlreadyExistException(name, description);
        }

        productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductByIdNotFoundException(id));
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        Product product = findById(id);
        ProductCategory category = product.getCategory();
        if (category != null) {
            Set<Product> products = category.getProducts();
            products.remove(product);
            category.setProducts(products);
        }
        productRepository.delete(product);
    }
}
