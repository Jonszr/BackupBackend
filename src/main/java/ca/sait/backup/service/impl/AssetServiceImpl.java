package ca.sait.backup.service.impl;

import ca.sait.backup.mapper.AssetMapper;
import ca.sait.backup.model.entity.Asset;
import ca.sait.backup.model.entity.AssetFolder;
import ca.sait.backup.model.entity.Category;
import ca.sait.backup.model.entity.Project;
import ca.sait.backup.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;


@Service
@Validated
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetMapper assetMapper;

    public List<Category> getAllCategories() {
        List<Category> categoryList = this.assetMapper.getAllCategories();
        return categoryList;
    }

    public List<Category> getAllCategoriesForProject(Integer projectId) {
        List<Category> categoryList = this.assetMapper.getAllCategoriesForProject(
            projectId
        );
        return categoryList;
    }

    public List<AssetFolder> getAllFoldersForCategory(Category category) {
        List<AssetFolder> assetFolderList = this.assetMapper.getAllFoldersForCategory(
            category.getCategoryId(),
            category.getProjectId()
        );
        return assetFolderList;
    }

    public List<Asset> getAllAssetsForCategory(Category category) {
        List<Asset> assetList = this.assetMapper.getAllAssetsForCategory(
            category.getCategoryId(),
            category.getProjectId()
        );
        return assetList;
    }

    public List<AssetFolder> getAllFoldersInsideFolder(AssetFolder assetFolder) {
        List<AssetFolder> assetFolderList = this.assetMapper.getAllFoldersInsideFolder(
            assetFolder.getId(),
            assetFolder.getProjectId()
        );
        return assetFolderList;
    }

    public List<Asset> getAllAssetsInsideFolder(AssetFolder assetFolder) {
        List<Asset> assetList = this.assetMapper.getAllAssetsInsideFolder(
            assetFolder.getId(),
            assetFolder.getProjectId()
        );
        return assetList;
    }

    public AssetFolder getAssetFolderFromId(Integer id) {
        AssetFolder assetFolder = this.assetMapper.getAssetFolderFromId(id);
        return assetFolder;
    }

}
