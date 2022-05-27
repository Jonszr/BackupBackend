package ca.sait.backup.mapper;

import ca.sait.backup.model.entity.Category;

import java.util.List;

public interface CategoryMapper {
    List<Category> getAllCategories();
}
