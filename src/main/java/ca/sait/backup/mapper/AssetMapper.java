package ca.sait.backup.mapper;

import ca.sait.backup.model.entity.Asset;
import ca.sait.backup.model.entity.AssetFolder;
import ca.sait.backup.model.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssetMapper {
    List<Category> getAllCategories();

    List<AssetFolder> getAllFoldersForCategory(
        @Param("iCategoryId") Integer iCategoryId,
        @Param("iProjectId") Integer iProjectId
    );

    List<Asset> getAllAssetsForCategory(
        @Param("iCategoryId") Integer iCategoryId,
        @Param("iProjectId") Integer iProjectId
    );

}
