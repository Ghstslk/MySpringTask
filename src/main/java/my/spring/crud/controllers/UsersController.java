package my.spring.crud.controllers;

import my.spring.crud.dao.UserDAO;
import my.spring.crud.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
public class UsersController {

    private final UserDAO userDAO;

    @Autowired
    public UsersController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping("/admin")
    public String allUsers(Model model) {
        model.addAttribute("users", userDAO.allUsers());
        return "admin/allusers";
    }


    @GetMapping("/admin/newuser")
    public String newUserForm(@ModelAttribute User user) {
        return "admin/newuser";
    }

    @PostMapping("/admin")
    public String addUser(@ModelAttribute User user) {
        userDAO.addUser(user);
        userDAO.addRole(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/{id}/edit")
    public String updateForm(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userDAO.getUser(id));
        return "admin/update";
    }

    @PatchMapping("/admin/{id}")
    public String updateUser(@ModelAttribute User user, @PathVariable("id") int id) {
        userDAO.updateUser(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userDAO.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String getUser(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        User user = userDAO.findUserByUsername(userDetails.getUsername());
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "user";
    }
}

