package ca.sait.backup.service.impl;

import ca.sait.backup.mapper.ProjectRepository;
import ca.sait.backup.model.entity.Project;
import ca.sait.backup.model.request.CreateNewProjectRequest;
import ca.sait.backup.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Service
@Validated
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public List<Project> getAllProjects() {
        List<Project> projectList = this.projectRepository.findAll();
        return projectList;
    }

    @Override
    public Project getProjectUsingId(Long id) {
        return this.projectRepository.getById(id);
    }

    @Override
    public boolean createNewProject(Long userId, CreateNewProjectRequest projectRequest){



        return false;
    }

}
