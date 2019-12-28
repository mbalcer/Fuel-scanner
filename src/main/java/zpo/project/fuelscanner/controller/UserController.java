package zpo.project.fuelscanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zpo.project.fuelscanner.model.User;
import zpo.project.fuelscanner.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public List<User> getUsers() {
        List<User> userList = userService.getUsers();
        return userList;
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
          return userService.getUser(id);
    }
}
