package ca.sait.backup.model.entity;

import lombok.Getter;

public class Asset {

    @Getter private int assetId;
    @Getter private int projectId;
    @Getter private int folderId;
    @Getter private int categoryId;
    @Getter private String assetName;
    @Getter private String assetType;
    @Getter private String assetValue;

}
