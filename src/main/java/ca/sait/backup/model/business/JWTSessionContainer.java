package ca.sait.backup.model.business;

import ca.sait.backup.model.entity.UserRole;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import lombok.Data;


@Data
public class JWTSessionContainer {

    // User id
    @Expose
    private Long userId;

    // Personal user info
    @Expose
    private String name;
    @Expose
    private String email;
    @Expose
    private String phoneNumber;

    // User type
    @Expose
    private UserRole userRole;

    // Methods
    public static JWTSessionContainer Process(String json) {

        // Convert to json
        Gson gson = new Gson();
        JWTSessionContainer session = gson.fromJson(json, JWTSessionContainer.class);
        return session;

    }


}
