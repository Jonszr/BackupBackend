package ca.sait.backup.mapper;

import ca.sait.backup.model.entity.Asset;
import ca.sait.backup.model.entity.AssetFolder;
import ca.sait.backup.model.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssetMapper {
    List<Category> getAllCategories();

    List<Category> getAllCategoriesForProject(
        @Param("iProjectId") Integer iProjectId
    );

    List<AssetFolder> getAllFoldersForCategory(
        @Param("iCategoryId") Integer iCategoryId,
        @Param("iProjectId") Integer iProjectId
    );

    List<Asset> getAllAssetsForCategory(
        @Param("iCategoryId") Integer iCategoryId,
        @Param("iProjectId") Integer iProjectId
    );

    List<AssetFolder> getAllFoldersInsideFolder(
        @Param("iCurrentFolderId") Integer iFolderId,
        @Param("iProjectId") Integer iProjectId
    );

    List<Asset> getAllAssetsInsideFolder(
        @Param("iCurrentFolderId") Integer iFolderId,
        @Param("iProjectId") Integer iProjectId
    );

    AssetFolder getAssetFolderFromId(
        @Param("iAssetFolderId") Integer iAssetFolderId
    );

}
