package ca.sait.backup.service;


import ca.sait.backup.model.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Experimentation purposes - Ibrahim
 */

public interface CategoryService {
    List<Category> getAllCategories();
}
