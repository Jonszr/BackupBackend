package ca.sait.backup.service;


import ca.sait.backup.model.entity.Asset;
import ca.sait.backup.model.entity.AssetFolder;
import ca.sait.backup.model.entity.Category;

import java.util.List;

/**
 Author: Ibrahim Element
 Service: Asset Service
 Purpose: Serve data-collection purposes and unifies all Asset related tables through one interface.
 Models: Asset, Category, Folder
 */

public interface AssetService {

    List<Category> getAllCategories();

    List<AssetFolder> getAllFoldersForCategory(Category category);

    List<Asset> getAllAssetsForCategory(Category category);

}
