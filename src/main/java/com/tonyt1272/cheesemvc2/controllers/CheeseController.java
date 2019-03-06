package com.tonyt1272.cheesemvc2.controllers;

import com.tonyt1272.cheesemvc2.models.Category;
import com.tonyt1272.cheesemvc2.models.Cheese;
import com.tonyt1272.cheesemvc2.models.data.CategoryDao;
import com.tonyt1272.cheesemvc2.models.data.CheeseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("cheese")
public class CheeseController {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired//This implements the CheeseDao interface for us to use below
    private CheeseDao cheeseDao;//and we will automatically have an instance of it
    // Request path: /cheese

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "My Cheeses");

        return "cheese/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model) {
        model.addAttribute("title", "Add Cheese");
        model.addAttribute(new Cheese());

        model.addAttribute("categories", categoryDao.findAll());
        return "cheese/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(@ModelAttribute  @Valid Cheese newCheese,
                                       Errors errors, @RequestParam int categoryId, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Cheese");
            model.addAttribute("categories",categoryDao.findAll());
            return "cheese/add";
        }



        Category cat = categoryDao.findById(categoryId).get();


        newCheese.setCategory(cat);
        cheeseDao.save(newCheese);
        return "redirect:";
    }

    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model) {
        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "Remove Cheese");
        return "cheese/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@RequestParam int[] cheeseIds) {

        for (int cheeseId : cheeseIds) {
            cheeseDao.deleteById(cheeseId);
        }

        return "redirect:";
    }

    @RequestMapping(value = "edit/{cheeseId}", method = RequestMethod.GET)
    public String displayEditForm(Model model, @PathVariable("cheeseId") int cheeseId){

        Cheese theCheese=cheeseDao.findById(cheeseId).get();
        model.addAttribute("cheese", theCheese);
        String theName = theCheese.getName();
        String title = "Edit "+theName+" (id = "+cheeseId+")";
        model.addAttribute("title",title);
        model.addAttribute("categories", categoryDao.findAll());
        Cheese.holdId=cheeseId;
        return "cheese/edit";
        //return "redirect:";
    }


    @RequestMapping(value = "edit",  method = RequestMethod.POST)
    public String processEditForm(Model model, @RequestParam int cheeseId, String name, String description,
                                  int categoryId, @Valid Cheese cheese, Errors errors){

        if(errors.hasErrors()){

            model.addAttribute("categories", categoryDao.findAll());
            model.addAttribute("cheese", cheese);

            String theName =cheeseDao.findById(cheeseId).get().getName();
            String title = "Edit "+theName+" (id = "+cheeseId+")";
            model.addAttribute("title",title);
            model.addAttribute("cheeseId",cheeseId);

            return "cheese/edit";

//            return "cheese/edit";

        }

            Category theCategory = categoryDao.findById(categoryId).get();
            Cheese theCheese = cheeseDao.findById(Cheese.holdId).get();
            theCheese.setName(name);
            theCheese.setCategory(theCategory);
            theCheese.setDescription(description);
            cheeseDao.save(theCheese);
//        System.out.println(CheeseData.holdId);
//        CheeseData.getById(CheeseData.holdId).setName(name);
//        CheeseData.getById(CheeseData.holdId).setDescription(description);
//        CheeseData.getById(CheeseData.holdId).setType(cheese.getType());
//        CheeseData.getById(CheeseData.holdId).setRating(cheese.getRating());
//        CheeseData.holdId = 0;
        return "redirect:";
    }

}
