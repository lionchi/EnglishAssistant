package org.gavrilov.service;

import org.gavrilov.domain.Category;
import org.gavrilov.dto.CategoryDTO;
import org.gavrilov.repository.CategoryRepository;
import org.gavrilov.specification.CategorySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(Category::getCategoryDTO)
                .collect(Collectors.toList());
    }

    public void saveCategory(CategoryDTO categoryDTO) {
        Category newCategory = new Category();
        newCategory.setNameCategory(categoryDTO.getNameCategory());
        categoryRepository.save(newCategory);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public void updateCategory(CategoryDTO categoryDTO) {
        categoryRepository.updateCategory(categoryDTO.getNameCategory(), categoryDTO.getId());
    }

    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> getPagetOfCategory(Pageable pageable) {
        Page<Category> all = categoryRepository.findAll(pageable);
        return all.map(Category::getCategoryDTO);
    }

    @Transactional(readOnly = true)
    public boolean isExist(String nameCategory) {
        return categoryRepository.findOne(CategorySpecification.findByNameCategory(nameCategory)).isPresent();
    }
}
