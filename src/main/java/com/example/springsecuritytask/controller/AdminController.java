package com.example.springsecuritytask.controller;

import com.example.springsecuritytask.entities.Role;
import com.example.springsecuritytask.entities.User;
import com.example.springsecuritytask.repository.RoleRepository;
import com.example.springsecuritytask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

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

    @GetMapping("/")
    public String modelMainPage(Model model) {
        model.addAttribute("usersList", userService.findAllUsers());
        return "users";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", roleRepository.findAll());
        return "editPage";
    }

    @PatchMapping("/{id}")
    public ModelAndView editUser(@ModelAttribute("user") User user) {
        userService.deleteById(user.getId());
        userService.saveUser(user);
        return new ModelAndView("redirect:/admin/");
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("user", new User());
        return "addPage";
    }

    @PostMapping("/add")
    public ModelAndView addUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return new ModelAndView("redirect:/admin/");
    }

    @DeleteMapping("/{id}")
    public ModelAndView deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return new ModelAndView("redirect:/admin/");
    }

}

