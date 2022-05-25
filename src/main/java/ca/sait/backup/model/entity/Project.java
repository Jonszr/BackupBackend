package ca.sait.backup.model.entity;


public class Project {

    // Storage
    private Integer projectId;

    private String name;

    private String bannerLocation;

    private String description;


    // Getters/Setters
    public void setId(Integer id) {
        this.projectId = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBannerUrl(String url) {
        this.bannerLocation = url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return this.projectId;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getUrl() {
        return this.bannerLocation;
    }

}
