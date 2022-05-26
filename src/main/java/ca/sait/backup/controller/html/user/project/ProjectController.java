package ca.sait.backup.controller.html.user.project;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user/project/{projectId}")
public class ProjectController {

    @GetMapping("/")
    public String GetProjectHome(@PathVariable("projectId") Integer projectId) {
        return "/user/project_home";
    }

}
