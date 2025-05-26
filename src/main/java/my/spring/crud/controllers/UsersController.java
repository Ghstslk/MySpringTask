package my.spring.crud.controllers;

import my.spring.crud.dao.UserDAO;
import my.spring.crud.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserDAO userDAO;

    @Autowired
    public UsersController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping()
    public String allUsers(Model model) {
        model.addAttribute("users", userDAO.allUsers());
        return "/users/allusers";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userDAO.getUser(id));
        return "users/userdetails";
    }

    @GetMapping("/newuser")
    public String newUserForm(@ModelAttribute User user) {
        return "users/newuser";
    }

    @PostMapping()
    public String addUser(@ModelAttribute User user) {
        userDAO.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String updateForm(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userDAO.getUser(id));
        return "users/update";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute User user, @PathVariable("id") int id) {
        userDAO.updateUser(id, user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userDAO.deleteUser(id);
        return "redirect:/users";
    }
}