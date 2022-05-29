package ca.sait.backup.controller.html.user.project;

import ca.sait.backup.model.entity.Asset;
import ca.sait.backup.model.entity.AssetFolder;
import ca.sait.backup.service.AssetService;
import ca.sait.backup.utils.JsonData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



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

}
