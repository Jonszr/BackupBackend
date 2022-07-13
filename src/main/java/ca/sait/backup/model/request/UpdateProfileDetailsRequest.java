package ca.sait.backup.model.request;


import lombok.Data;

@Data
public class UpdateProfileDetailsRequest {

    private String profilePictureUrl;
    private String phoneNumber;

}
