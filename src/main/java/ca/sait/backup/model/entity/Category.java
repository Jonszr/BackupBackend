package ca.sait.backup.model.entity;


import lombok.Getter;

public class Category {

    @Getter private int categoryId;
    @Getter private int projectId;
    @Getter private String name;
    @Getter private String description;

}
