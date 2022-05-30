package ca.sait.backup.model.entity;

import lombok.Getter;
import lombok.Setter;

public class AssetFolder {

    @Getter private int id;
    @Getter @Setter private int projectId;
    @Getter @Setter private int categoryId;
    @Getter @Setter int parentId;
    @Getter @Setter private String name;

}
