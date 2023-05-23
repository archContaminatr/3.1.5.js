package com.example.springsecuritytask.controller;

import com.example.springsecuritytask.entities.User;
import com.example.springsecuritytask.repository.RoleRepository;
import com.example.springsecuritytask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    private RoleRepository roleRepository;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping()
    public String modelMainPage(Model model, Principal principal) {
        model.addAttribute("usersList", userService.findAllUsers());
        model.addAttribute("roles", userService.findAllRoles());
        model.addAttribute("user", userService.findUserByEmail(principal.getName()));
        return "admin";
    }

    @PatchMapping("/{id}")
    public ModelAndView editUser(@ModelAttribute("user") User user) {
        userService.deleteById(user.getId());
        userService.saveUser(user);
        return new ModelAndView("redirect:/admin");
    }

    @PostMapping("/add")
    public ModelAndView addUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return new ModelAndView("redirect:/admin");
    }

    @DeleteMapping("/{id}")
    public ModelAndView deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return new ModelAndView("redirect:/admin");
    }

}

