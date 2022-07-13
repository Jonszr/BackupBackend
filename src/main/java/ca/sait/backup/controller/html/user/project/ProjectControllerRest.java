package ca.sait.backup.controller.html.user.project;

import ca.sait.backup.model.entity.Asset;
import ca.sait.backup.model.entity.AssetFolder;
import ca.sait.backup.model.entity.Category;
import ca.sait.backup.model.entity.Project;
import ca.sait.backup.model.request.AssetRequest;
import ca.sait.backup.model.request.CategoryRequest;
import ca.sait.backup.model.request.FolderRequest;
import ca.sait.backup.service.AssetService;
import ca.sait.backup.service.ProjectService;
import ca.sait.backup.utils.JsonData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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

    @Autowired
    private ProjectService projectService;

    @GetMapping("/folders/{folderId}")
    public JsonData listFoldersInsideFolder(@PathVariable("folderId") Integer folderId) throws Exception {

        AssetFolder currentFolder = this.assetService.getAssetFolderFromId(
            (long) folderId
        );

        List<AssetFolder> childFolders = this.assetService.getAllFoldersInsideFolder(
            currentFolder
        );

        // Convert to json
        GsonBuilder gsonBuilder = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation();

        Gson gson = gsonBuilder.create();

        String jsnChildFolders = gson.toJson(childFolders);

        return JsonData.buildSuccess(jsnChildFolders);
    }

    @GetMapping("/files/{folderId}")
    public JsonData listFilesInsideFolder(@PathVariable("folderId") Integer folderId) throws Exception {

        AssetFolder currentFolder = this.assetService.getAssetFolderFromId(
            (long)folderId
        );

        List<Asset> childAssets = currentFolder.getChildAssets();

        GsonBuilder gsonBuilder = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation();

        Gson gson = gsonBuilder.create();
        String jsnChildAssets = gson.toJson(childAssets);

        return JsonData.buildSuccess(jsnChildAssets);
    }

    @PostMapping("/assets/{projectId}")
    public JsonData createNewAsset(@PathVariable("projectId") Integer projectId, @RequestBody AssetRequest assetRequest) throws Exception {

        Project project = this.projectService.getProjectUsingId((long)projectId);

        List<Category> categoryList = project.getCategories();

        Asset asset = new Asset();

        // If no category id is provided (meaning no category is selected).
        if (assetRequest.getCategoryId() == -1) {
            // Find the category id using the name and project id.
            for (Category category : categoryList) {
                if (assetRequest.getCategorySelection().equals(category.getName())) {
                    asset.setCategory(category);
                }
            }
        } else {
            // Otherwise, we are creating a new asset inside of a folder which is already associated to a category.
            asset.setCategory(
                this.assetService.getCategoryById((long)assetRequest.getCategoryId())
            );
        }

        // If folder id is provided (meaning this is a folder on the root).
        if (assetRequest.getFolderId() != -1) {
            asset.setParent(
                this.assetService.getAssetFolderFromId(
                    (long)assetRequest.getFolderId()
                )
            );
        }

        // Configure values
        asset.setAssetValue(assetRequest.getValue());
        asset.setAssetName(assetRequest.getName());
        asset.setAssetType(assetRequest.getType());

        // Save
        this.assetService.createAsset(asset);

        return JsonData.buildSuccess("");
    }

    @PostMapping("/folders/{projectId}")
    public JsonData createNewFolder(@PathVariable("projectId") Long projectId, @RequestBody FolderRequest folderRequest) throws Exception {

        Project project = this.projectService.getProjectUsingId(projectId);

        List<Category> categoryList = project.getCategories();

        AssetFolder assetFolder = new AssetFolder();

        // If no category id is provided (meaning no category is selected).
        if (folderRequest.getCategoryId() == -1) {
            // Find the category id using the name and project id.
            for (Category category : categoryList) {
                if (folderRequest.getCategorySelection().equals(category.getName())) {
                    assetFolder.setCategory(category);
                }
            }
        } else {
            // Otherwise, we are creating a new asset inside of a folder which is already associated to a category.
            assetFolder.setCategory(
                this.assetService.getCategoryById(
                    (long)folderRequest.getCategoryId()
                )
            );
        }

        // If folder id is provided (meaning this is a folder on the root).
        if (folderRequest.getFolderId() != -1) {

            assetFolder.setParent(
                this.assetService.getAssetFolderFromId(
                    (long)folderRequest.getFolderId()
                )
            );

        }

        // Create new asset folder
        assetFolder.setName(folderRequest.getFolderName());

        this.assetService.createFolder(assetFolder);

        return JsonData.buildSuccess("");
    }

    @PostMapping("/categories/{projectId}")
    public JsonData createNewCategory(@PathVariable("projectId") Integer projectId, @RequestBody CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setProject(
            this.projectService.getProjectUsingId((long)projectId)
        );
        category.setName(categoryRequest.getName());
        category.setDescription(categoryRequest.getDescription());

        this.assetService.createCategory(category);

        return JsonData.buildSuccess("");
    }

}