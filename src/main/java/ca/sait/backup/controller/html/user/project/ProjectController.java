package ca.sait.backup.controller.html.user.project;


import ca.sait.backup.mapper.CategoryMapper;
import ca.sait.backup.model.entity.Category;
import ca.sait.backup.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user/project/{projectId}")
public class ProjectController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String GetProjectHome(@PathVariable("projectId") Integer projectId, Model model) {

        List<Category> categoryList = this.categoryService.getAllCategories();
        model.addAttribute("categories", categoryList);

        return "/user/asset_explorer.html";
    }

}
