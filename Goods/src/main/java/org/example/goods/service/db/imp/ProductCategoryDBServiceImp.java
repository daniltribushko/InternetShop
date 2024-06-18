package org.example.goods.service.db.imp;

import org.example.goods.exceptions.categories.ProductCategoryAlreadyExistException;
import org.example.goods.exceptions.categories.ProductCategoryByIdNotFoundException;
import org.example.goods.models.entities.ProductCategory;
import org.example.goods.repositories.ProductCategoryRepository;
import org.example.goods.service.db.ProductCategoryDBService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public ProductCategoryDBServiceImp(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
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
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
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
