package ca.sait.backup.model.entity;

import lombok.Getter;

public class AssetFolder {

    @Getter private int id;
    @Getter private int projectId;
    @Getter private int categoryId;
    @Getter private int parentId;
    @Getter private String name;

}
