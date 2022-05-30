package ca.sait.backup.service;


import ca.sait.backup.model.entity.Asset;
import ca.sait.backup.model.entity.AssetFolder;
import ca.sait.backup.model.entity.Category;
import ca.sait.backup.model.entity.Project;

import java.util.List;

/**
 Author: Ibrahim Element
 Service: Asset Service
 Purpose: Serve data-collection purposes and unifies all Asset related tables through one interface.
 Models: Asset, Category, Folder
 */

public interface AssetService {

    // Categories
    int createCategory(Category category);

    List<Category> getAllCategories();

    List<Category> getAllCategoriesForProject(Integer projectId);

    List<AssetFolder> getAllFoldersForCategory(Category category);

    List<Asset> getAllAssetsForCategory(Category category);

    // Folders

    void createFolder(AssetFolder folder);

    List<AssetFolder> getAllFoldersInsideFolder(AssetFolder assetFolder);

    List<Asset> getAllAssetsInsideFolder(AssetFolder assetFolder);

    AssetFolder getAssetFolderFromId(Integer assetFolderId);

    // Assets
    void createAsset(Asset asset);

    void updateAsset(Asset asset, Integer categoryId);

    void deleteAsset(Asset asset);

}
