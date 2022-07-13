package ca.sait.backup.controller.html.user.project;

import ca.sait.backup.model.business.RowContainer;
import ca.sait.backup.model.entity.Project;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ca.sait.backup.service.ProjectService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user/project")
public class OverviewController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/overview")
    public String overview(Model model) {

        // Get list of projects from Project Service.
        List<Project> projectList = this.projectService.getAllProjects();

        // Process project list into a grid format.
        RowContainer<Project> gridContainer = new RowContainer<>(projectList, 3);

        // Add attribute to rendering system (Thyme).
        model.addAttribute("projectGrid", gridContainer.getGrid());

        // Trigger template processing and return.
        return "user/project_overview";

    }

}
