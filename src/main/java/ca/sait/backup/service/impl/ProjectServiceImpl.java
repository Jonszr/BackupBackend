package ca.sait.backup.service.impl;

import ca.sait.backup.mapper.ProjectMapper;
import ca.sait.backup.model.entity.Project;
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
    private ProjectMapper projectMapper;

    @Override
    public List<Project> getAllProjects() {
        List<Project> projectList = this.projectMapper.getAllProjects();
        return projectList;
    }

}
