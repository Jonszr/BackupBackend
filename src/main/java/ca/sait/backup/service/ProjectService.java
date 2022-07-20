package ca.sait.backup.service;

import ca.sait.backup.model.entity.Project;
import ca.sait.backup.model.entity.User;
import ca.sait.backup.model.request.CreateNewProjectRequest;

import java.util.List;


public interface ProjectService {

    boolean createNewProject(Long userId, CreateNewProjectRequest projectRequest);

    Project getProjectUsingId(Long projectId);

    List<Project> getAllProjects();
}
