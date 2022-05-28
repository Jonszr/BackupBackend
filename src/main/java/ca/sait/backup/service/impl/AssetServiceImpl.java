package ca.sait.backup.service.impl;

import ca.sait.backup.mapper.AssetMapper;
import ca.sait.backup.model.entity.Asset;
import ca.sait.backup.model.entity.AssetFolder;
import ca.sait.backup.model.entity.Category;
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

}
