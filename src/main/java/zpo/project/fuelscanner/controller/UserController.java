package zpo.project.fuelscanner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zpo.project.fuelscanner.model.User;
import zpo.project.fuelscanner.service.UserService;

import java.util.List;
import java.util.Optional;

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

    @RequestMapping(value = "/{login}",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("login") String login) {
        Optional<User> user = userService.getUserByLogin(login);

        if(!user.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/validate/{login}/{password}",
            method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> validate(@PathVariable("login") String login,
                                    @PathVariable("password") String password) {
        Optional<User> user = userService.checkUser(login, password);

        if(!user.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            Long id = user.get().getId();
            return new ResponseEntity<>(id, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity create(@RequestBody User user) {

        String login = user.getLogin();
        String name = user.getName();
        String password = user.getPassword();
        String email = user.getEmail();

        if(login.equals("") || userService.checkLogin(login)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(name.equals("") || password.equals("")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
