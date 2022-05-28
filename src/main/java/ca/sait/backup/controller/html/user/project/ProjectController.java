package ca.sait.backup.controller.html.user.project;


import ca.sait.backup.component.user.CategoryAssociation;
import ca.sait.backup.model.entity.Category;
import ca.sait.backup.service.AssetService;

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
    private AssetService assetService;

    @GetMapping("/")
    public String GetProjectHome(@PathVariable("projectId") Integer projectId, Model model) {

        // Call asset service to get a full list of categories
        List<Category> categoryList = this.assetService.getAllCategories();

        // Initialize a CategoryAssociation UI Component
        ArrayList<CategoryAssociation> categoryAssociationList = new ArrayList<CategoryAssociation>();

        // For every category, loop and find linked assets/folders - while populating association list.
        for (Category cat: categoryList) {
            CategoryAssociation categoryAssociation = new CategoryAssociation();
            categoryAssociation.setCategory(cat);

            categoryAssociation.setAssetList(
                this.assetService.getAllAssetsForCategory(cat)
            );
            categoryAssociation.setFolderList(
                this.assetService.getAllFoldersForCategory(cat)
            );

            categoryAssociationList.add(
                categoryAssociation
            );
        }

        // Feed the rendering agent the data through the UI component.
        model.addAttribute("categoryAssociationList", categoryAssociationList);

        return "/user/asset_explorer.html";
    }

}
