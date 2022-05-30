package ca.sait.backup.model.entity;

import lombok.Getter;
import lombok.Setter;

public class Asset {

    @Getter private int assetId;
    @Getter @Setter private int projectId;
    @Getter @Setter private int folderId;
    @Getter @Setter private int categoryId;
    @Getter @Setter private String assetName;
    @Getter @Setter private String assetType;
    @Getter @Setter private String assetValue;

}
