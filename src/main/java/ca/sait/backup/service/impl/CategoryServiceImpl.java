package ca.sait.backup.service.impl;

import ca.sait.backup.mapper.CategoryMapper;
import ca.sait.backup.model.entity.Category;
import ca.sait.backup.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;


@Service
@Validated
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> getAllCategories() {
        List<Category> categoryList = this.categoryMapper.getAllCategories();
        return categoryList;
    }

}
