package ca.sait.backup.controller.html.user.project;

import ca.sait.backup.model.entity.Asset;
import ca.sait.backup.model.entity.AssetFolder;
import ca.sait.backup.model.entity.Category;
import ca.sait.backup.model.request.AssetRequest;
import ca.sait.backup.model.request.CategoryRequest;
import ca.sait.backup.model.request.FolderRequest;
import ca.sait.backup.service.AssetService;
import ca.sait.backup.utils.JsonData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/** Author: Ibrahim Element
 * Description: Rest APIs for user/agency project management
 * Usage: Asset Exploration
 */

import java.util.List;

@RestController
@RequestMapping("api/v1/pri/user/project")
public class ProjectControllerRest {

    @Autowired
    private AssetService assetService;

    @GetMapping("/folders/{folderId}")
    public JsonData listFoldersInsideFolder(@PathVariable("folderId") Integer folderId) {

        AssetFolder currentFolder = this.assetService.getAssetFolderFromId(folderId);
        List<AssetFolder> childFolders = this.assetService.getAllFoldersInsideFolder(
                currentFolder
        );

        // Convert to json
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String jsnChildFolders = gson.toJson(childFolders);

        return JsonData.buildSuccess(jsnChildFolders);
    }

    @GetMapping("/files/{folderId}")
    public JsonData listFilesInsideFolder(@PathVariable("folderId") Integer folderId) {

        AssetFolder currentFolder = this.assetService.getAssetFolderFromId(folderId);
        List<Asset> childAssets = this.assetService.getAllAssetsInsideFolder(
                currentFolder
        );

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        String jsnChildAssets = gson.toJson(childAssets);

        return JsonData.buildSuccess(jsnChildAssets);
    }

    @PostMapping("/assets/{projectId}")
    public JsonData createNewAsset(@PathVariable("projectId") Integer projectId, @RequestBody AssetRequest assetRequest) {

        List<Category> categoryList = this.assetService.getAllCategoriesForProject(
                projectId
        );

        int categoryId = -1;
        int folderId = -1;

        // If no category id is provided (meaning no category is selected).
        if (assetRequest.getCategoryId() == -1) {
            // Find the category id using the name and project id.
            for (Category category : categoryList) {
                if (assetRequest.getCategorySelection().equals(category.getName())) {
                    categoryId = category.getCategoryId();
                }
            }
        } else {
            // Otherwise, we are creating a new asset inside of a folder which is already associated to a category.
            categoryId = assetRequest.getCategoryId();
        }

        // If folder id is provided (meaning this is a folder on the root).
        if (assetRequest.getFolderId() != -1) {
            folderId = assetRequest.getFolderId();
        }

        Asset asset = new Asset();

        // Configure location
        asset.setProjectId(projectId);
        asset.setCategoryId(categoryId);
        asset.setFolderId(folderId);

        // Configure values
        asset.setAssetValue(assetRequest.getValue());
        asset.setAssetName(assetRequest.getName());
        asset.setAssetType(assetRequest.getType());

        // Save
        this.assetService.createAsset(asset);

        return JsonData.buildSuccess("");
    }

    @PostMapping("/folders/{projectId}")
    public JsonData createNewFolder(@PathVariable("projectId") Integer projectId, @RequestBody FolderRequest folderRequest) {

        List<Category> categoryList = this.assetService.getAllCategoriesForProject(
                projectId
        );

        int categoryId = -1;
        int folderId = -1;

        // If no category id is provided (meaning no category is selected).
        if (folderRequest.getCategoryId() == -1) {
            // Find the category id using the name and project id.
            for (Category category : categoryList) {
                if (folderRequest.getCategorySelection().equals(category.getName())) {
                    categoryId = category.getCategoryId();
                }
            }
        } else {
            // Otherwise, we are creating a new asset inside of a folder which is already associated to a category.
            categoryId = folderRequest.getCategoryId();
        }

        // If folder id is provided (meaning this is a folder on the root).
        if (folderRequest.getFolderId() != -1) {
            folderId = folderRequest.getFolderId();
        }

        // Create new asset folder
        AssetFolder assetFolder = new AssetFolder();

        assetFolder.setName(folderRequest.getFolderName());
        assetFolder.setProjectId(projectId);
        assetFolder.setParentId(folderId);
        assetFolder.setCategoryId(categoryId);

        this.assetService.createFolder(assetFolder);

        return JsonData.buildSuccess("");
    }

    @PostMapping("/categories/{projectId}")
    public JsonData createNewFolder(@PathVariable("projectId") Integer projectId, @RequestBody CategoryRequest categoryRequest) {

        Category category = new Category();
        category.setProjectId(projectId);
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());

        this.assetService.createCategory(category);

        return JsonData.buildSuccess("");
    }

}