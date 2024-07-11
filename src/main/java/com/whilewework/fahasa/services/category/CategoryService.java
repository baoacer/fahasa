package com.whilewework.fahasa.services.category;

import com.whilewework.fahasa.dto.CategoryDto;
import com.whilewework.fahasa.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDto categoryDto);

    List<Category> getAllCategories();
}
