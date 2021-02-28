package com.example.demo3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo3.model.Role;
import com.example.demo3.model.User;
import com.example.demo3.service.RoleService;
import com.example.demo3.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "admin/user-list")
    public ModelAndView findAllAdmin(Principal principal, User createdUser) {
        User currentUser = userService.findByLastName(principal.getName());
        ModelAndView modelAndView = new ModelAndView();
        List<User> users = userService.findAll();
        Set<Role> uniqueRoles = roleService.findAllRoles();

        modelAndView.setViewName("admin/user-list");
        modelAndView.addObject("users", users);
        modelAndView.addObject("currentUser", currentUser);
        modelAndView.addObject("createdUser", createdUser);
        modelAndView.addObject("roles", uniqueRoles);

//        modelAndView.addObject("updateUser", userService.findById(id));
        modelAndView.addObject("updateRoles", roleService.findAllRoles());

        return modelAndView;
    }


    @GetMapping(value = "user/user-list_")
    public ModelAndView findAllUser(Principal currentUser) {
        User user = userService.findByLastName(currentUser.getName());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/user-list_");
        modelAndView.addObject("users", user);
        return modelAndView;
    }

    @GetMapping("admin/user-create")
    public ModelAndView createUserForm(User user) {
        ModelAndView modelAndView = new ModelAndView();
        Set<Role> uniqueRoles = roleService.findAllRoles();
        modelAndView.addObject("users", user);
        modelAndView.addObject("roles", uniqueRoles);
        modelAndView.setViewName("admin/user-create");
        return modelAndView;
    }

    @PostMapping("admin/user-create")
    public ModelAndView createUser(User user) {
        ModelAndView modelAndView = new ModelAndView();
        userService.saveUser(user);
        modelAndView.setViewName("redirect:/admin/user-list");
        return modelAndView;
    }

    @GetMapping("admin/user-delete/{id}")
    public ModelAndView deleteUser(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        userService.deleteById(id);
        modelAndView.setViewName("redirect:/admin/user-list");
        return modelAndView;
    }

    @GetMapping("admin/user-update/{id}")
    public ModelAndView updateUserForm(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("admin/user-update");
        modelAndView.addObject("users", userService.findById(id));
        modelAndView.addObject("roles", roleService.findAllRoles());
        return modelAndView;
    }

    @PostMapping("admin/user-update")
    public ModelAndView updateUser(User user) {
        ModelAndView modelAndView = new ModelAndView();
        userService.updateUser(user);
        modelAndView.setViewName("redirect:/admin/user-list");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}