package ca.sait.backup.service.impl;

import ca.sait.backup.mapper.*;
import ca.sait.backup.model.business.JWTSessionContainer;
import ca.sait.backup.model.entity.*;
import ca.sait.backup.model.request.LockAssetRequest;
import ca.sait.backup.service.AssetService;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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

    @Autowired
    private AssetSecurityProfileRepository assetSecurityProfileRepository;

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

    // Asset Security

    @Override
    public void lockAsset(LockAssetRequest lockRequest) {

        Asset asset = new Asset();
        AssetFolder assetFolder = new AssetFolder();

        if (lockRequest.getAssetType().equals("folder")) {
            assetFolder = this.assetFolderRepository.findById(
                lockRequest.getAssetId()
            ).get();
        }else {
            asset = this.assetRepository.findById(
                lockRequest.getAssetId()
            ).get();
        }

        AssetSecurityProfile securityProfile = new AssetSecurityProfile();

        if (lockRequest.getLockType().equals("PASSWORD")) {
            securityProfile.setSecurityType(
                AssetSecurityProfileTypeEnum.PASSWORD_PROTECTED
            );
        }else if(lockRequest.getLockType().equals("TIME")) {
            securityProfile.setSecurityType(
                AssetSecurityProfileTypeEnum.TIME_RELEASE
            );
        }else if(lockRequest.getLockType().equals("REQUEST")) {
            securityProfile.setSecurityType(
                AssetSecurityProfileTypeEnum.REQUEST_EXCLUSIVE
            );
        }

        securityProfile.setSecurityConfiguration(
            lockRequest.getLockConfiguration()
        );

        this.assetSecurityProfileRepository.save(
            securityProfile
        );

        // Associate the asset with the created profile
        if (lockRequest.getAssetType().equals("folder")) {
            assetFolder.setSecurityProfile(
                securityProfile
            );

            this.assetFolderRepository.save(
                assetFolder
            );
        }else {
            asset.setSecurityProfile(
                securityProfile
            );

            this.assetRepository.save(
                asset
            );
        }



    }

    @Override
    public String getSecurityConfig(LockAssetRequest assetInfo) {

        Asset asset = new Asset();
        AssetFolder assetFolder = new AssetFolder();

        String configuration = "";
        JsonObject jsonObject = new JsonObject();

        AssetSecurityProfile profile = new AssetSecurityProfile();

        if (assetInfo.getAssetType().equals("folder")) {
            profile = this.assetFolderRepository.findById(
                    assetInfo.getAssetId()
            ).get().getSecurityProfile();
        }else {
            profile = this.assetRepository.findById(
                    assetInfo.getAssetId()
            ).get().getSecurityProfile();
        }

        // Need to do this to prevent sensitive data being returned
        if (profile.getSecurityType().equals(AssetSecurityProfileTypeEnum.PASSWORD_PROTECTED)) {
            jsonObject.addProperty("type", "password");
        }else if (profile.getSecurityType().equals(AssetSecurityProfileTypeEnum.TIME_RELEASE)) {
            jsonObject.addProperty("type", "time-release");
            jsonObject.addProperty("config", profile.getSecurityConfiguration());
        }else if (profile.getSecurityType().equals(AssetSecurityProfileTypeEnum.REQUEST_EXCLUSIVE)) {
            jsonObject.addProperty("type", "request-exclusive");
            jsonObject.addProperty("config", profile.getSecurityConfiguration());
        }

        return jsonObject.toString();
    }

    @Override
    public void dev_approveMember(Long userId, Long assetId) {

    }

    @Override
    public boolean ui_isApprovedMember(JWTSessionContainer sessionContainer, Asset asset){

        // Check if there is a security profile attached in the first place
        if (asset.getSecurityProfile() == null) {
            return true;
        }

        // Check if this user is the owner
        boolean isOwner = asset.getCategory().getProject().getUser().getId() == sessionContainer.getUserId();
        if (isOwner == true) {
            return true;
        }

        // Otherwise, check if the user is approved under the security profile.
        return asset.getSecurityProfile().getApprovalList().stream().filter( (AssetSecurityApproval app) -> {
            if (app.getUser().getId() == sessionContainer.getUserId()) {
                return true;
            }
            return false;
        }).collect(Collectors.toList()).size() > 0;

    }

    @Override
    public boolean ui_isApprovedMember(JWTSessionContainer sessionContainer, AssetFolder assetFolder) {

        // Check if there is a security profile attached in the first place
        if (assetFolder.getSecurityProfile() == null) {
            return true;
        }

        // Check if this user is the owner
        boolean isOwner = assetFolder.getCategory().getProject().getUser().getId() == sessionContainer.getUserId();
        if (isOwner == true) {
            return true;
        }

        // Otherwise, check if the user is approved under the security profile.
        return assetFolder.getSecurityProfile().getApprovalList().stream().filter( (AssetSecurityApproval app) -> {
            if (app.getUser().getId() == sessionContainer.getUserId()) {
                return true;
            }
            return false;
        }).collect(Collectors.toList()).size() > 0;

    }

}
