package jpabook.jpashop.service;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long saveCategory(Category category) {
        categoryRepository.save(category);
        return category.getId();
    }

    public List<Category> findCategories() {
        return categoryRepository.findAll();
    }

    public Category findOne(Long categoryId) {
        return categoryRepository.findById(categoryId).get();
    }

}
