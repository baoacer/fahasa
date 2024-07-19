package com.whilewework.fahasa.services.admin.category;

import com.whilewework.fahasa.dto.CategoryDto;
import com.whilewework.fahasa.entity.Category;
import com.whilewework.fahasa.mapper.CategoryMapper;
import com.whilewework.fahasa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = CategoryMapper.toEntity(categoryDto);

        categoryRepository.save(category);

        CategoryDto dto = CategoryMapper.toDto(category);

        return dto;
    }

    @Override
    public List<CategoryDto> getAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(CategoryMapper::toDto).collect(Collectors.toList());
    }

}
