package ca.sait.backup.model.request;


import lombok.Data;

@Data
public class CreateNewProjectRequest {
    private String name;
    private String description;
    private String bannerLocation;
}
