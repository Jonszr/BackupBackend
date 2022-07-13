package ca.sait.backup.service.impl;

import ca.sait.backup.mapper.AssetFolderRepository;
import ca.sait.backup.mapper.AssetRepository;
import ca.sait.backup.mapper.CategoryRepository;
import ca.sait.backup.mapper.ProjectRepository;
import ca.sait.backup.model.entity.Asset;
import ca.sait.backup.model.entity.AssetFolder;
import ca.sait.backup.model.entity.Category;
import ca.sait.backup.model.entity.Project;
import ca.sait.backup.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;


@Service
@Validated
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetFolderRepository assetFolderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public int createCategory(Category category) {
        this.categoryRepository.save(category);
        return 1;
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = this.categoryRepository.findAll();
        return categoryList;
    }

    public Category getCategoryById(Long categoryId) throws Exception {
        Optional<Category> cat = this.categoryRepository.findById(categoryId);
        if (!cat.isPresent()) throw new Exception("Category doesn't exist");
        return cat.get();
    }


    public List<Category> getAllCategoriesForProject(Long projectId) throws Exception {


        Optional<Project> project = this.projectRepository.findById(projectId);

        if (!project.isPresent()) {
            throw new Exception("Project does not exist");
        }

        return project.get().getCategories();
    }

    public List<AssetFolder> getAllFoldersForCategory(Category category) {

//        List<AssetFolder> assetFolderList = this.assetMapper.getAllFoldersForCategory(
//            category.getCategoryId(),
//            category.getProjectId()
//        );
//
        return null;

    }

    public List<Asset> getAllAssetsForCategory(Category category) {
//        List<Asset> assetList = this.assetMapper.getAllAssetsForCategory(
//            category.getCategoryId(),
//            category.getProjectId()
//        );

        return null;
//        return assetList;
    }

    public void createFolder(AssetFolder assetFolder) {

        this.assetFolderRepository.save(assetFolder);

    }

    public List<AssetFolder> getAllFoldersInsideFolder(AssetFolder assetFolder) {

        List<AssetFolder> children = this.assetFolderRepository.findByParent(assetFolder);

        return children;
    }

    public List<Asset> getAllAssetsInsideFolder(AssetFolder assetFolder) {
        List<Asset> assetList = assetFolder.getChildAssets();
        return assetList;
    }

    public AssetFolder getAssetFolderFromId(Long id) throws Exception {
        Optional<AssetFolder> assetFolder = this.assetFolderRepository.findById(id);
        if (!assetFolder.isPresent()) throw new Exception("Folder does not exist");
        return assetFolder.get();
    }

    public void createAsset(Asset asset) {
        this.assetRepository.save(asset);
    }

    public void updateAsset(Asset asset, Integer categoryId) {

    }

    public void deleteAsset(Asset asset) {

    }

}
