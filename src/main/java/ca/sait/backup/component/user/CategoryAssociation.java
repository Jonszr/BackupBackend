package ca.sait.backup.component.user;

import ca.sait.backup.model.entity.Asset;
import ca.sait.backup.model.entity.AssetFolder;
import ca.sait.backup.model.entity.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Author: Ibrahim Element
 * Component: CategoryAssociation
 * Purpose: To create a UI entity which contains a category as the parent, with assets and folders as the children.
 */

public class CategoryAssociation {
    @Getter @Setter private Category category;
    @Getter @Setter private List<Asset> assetList;
    @Getter @Setter private List<AssetFolder> folderList;
}
