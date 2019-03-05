package com.tonyt1272.cheesemvc2.controllers;


import com.tonyt1272.cheesemvc2.models.Menu;
import com.tonyt1272.cheesemvc2.models.data.CheeseDao;
import com.tonyt1272.cheesemvc2.models.data.MenuDao;
import com.tonyt1272.cheesemvc2.models.forms.AddMenuItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private MenuDao menuDao;

    @Autowired//This implements the CheeseDao interface for us to use below
    private CheeseDao cheeseDao;//and we will automatically have an instance of it
    // Request path: /cheese

    @RequestMapping(value = "")
    public String index(Model model){
        model.addAttribute("title","Menus");
        model.addAttribute("menus", menuDao.findAll());
        return "menu/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("title", "Add Menu");
        model.addAttribute(new Menu());//we submit the empty menu for the validation
        return "menu/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Menu menu, Errors errors){

        if (errors.hasErrors()){
            model.addAttribute("title", "Add Menu");
            return "menu/add";
        }

        menuDao.save(menu);

        return "redirect:view/" +menu.getId();
    }

    @RequestMapping(value ="view/{menuId}", method = RequestMethod.GET)
    public String viewMenu(Model model, @PathVariable int menuId){

        Menu menu = menuDao.findById(menuId).get();
        model.addAttribute("title", menu.getName());
        model.addAttribute("menu",menu);

        return "menu/view";
    }

    @RequestMapping(value ="add-item/{menuId}", method = RequestMethod.GET)
    public String addItem(Model model, @PathVariable int menuId){

        Menu menu = menuDao.findById(menuId).get();

        AddMenuItemForm form = new AddMenuItemForm(cheeseDao.findAll(),menu);
        model.addAttribute("title", "Add item to menu " +menu.getName());
        model.addAttribute("form",form);

        return "menu/add-item";

    }



}
