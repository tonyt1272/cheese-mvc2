package com.tonyt1272.cheesemvc2.controllers;

import com.tonyt1272.cheesemvc2.models.Category;
import com.tonyt1272.cheesemvc2.models.data.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("categories", categoryDao.findAll());
        model.addAttribute("title", "Categories");

        return "category/index";
    }



    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {

        model.addAttribute(new Category());
        model.addAttribute("title","Add Category");


        return "Category/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model,@ModelAttribute @Valid Category category,
                                       Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute(new Category());
            model.addAttribute("title","Add Category");
            return "cheese/add";
        }

        categoryDao.save(category);

        return "redirect:";
    }


}
