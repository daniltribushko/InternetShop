package org.example.goods.service.db.imp;

import org.example.goods.exceptions.categories.ProductCategoryAlreadyExistException;
import org.example.goods.exceptions.categories.ProductCategoryByIdNotFoundException;
import org.example.goods.models.entities.ProductCategory;
import org.example.goods.repositories.ProductCategoryPaginationRepository;
import org.example.goods.repositories.ProductCategoryRepository;
import org.example.goods.repositories.ProductPaginationRepository;
import org.example.goods.service.db.ProductCategoryDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author Tribushko Danil
 * @since 03.06.2024
 * <p>
 * Реализация сервиса по работе с категориями товаров в бд
 */
@Service
public class ProductCategoryDBServiceImp implements ProductCategoryDBService {
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryPaginationRepository productCategoryPaginationRepository;

    @Autowired
    public ProductCategoryDBServiceImp(ProductCategoryRepository productCategoryRepository,
                                       ProductCategoryPaginationRepository productCategoryPaginationRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.productCategoryPaginationRepository = productCategoryPaginationRepository;
    }


    @Override
    public void updateProductCategory(ProductCategory productCategory) {
        productCategoryRepository.save(productCategory);
    }

    @Override
    public boolean existByTitle(String title) {
        return productCategoryRepository.existsByTitle(title);
    }

    @Override
    public void save(ProductCategory entity) {
        String title = entity.getTitle();

        if (existByTitle(title)) {
            throw new ProductCategoryAlreadyExistException(title);
        }

        productCategoryRepository.save(entity);
    }

    @Override
    public ProductCategory findById(Long id) {
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new ProductCategoryByIdNotFoundException(id));
    }

    @Override
    public Page<ProductCategory> findAll(Pageable pageable) {
        return productCategoryPaginationRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        ProductCategory productCategory = findById(id);
        Set<ProductCategory> categories = productCategory.getCategories();
        if (categories != null){
            categories.forEach(c -> c.setParentCategory(null));
        }
        productCategoryRepository.delete(productCategory);
    }
}
