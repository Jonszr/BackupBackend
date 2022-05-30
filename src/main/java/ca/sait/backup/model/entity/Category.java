package ca.sait.backup.model.entity;


import lombok.Getter;
import lombok.Setter;

public class Category {

    @Getter private int categoryId;
    @Setter @Getter private int projectId;
    @Setter @Getter private String name;
    @Setter @Getter private String description;

}
