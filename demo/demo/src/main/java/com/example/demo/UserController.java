package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController

public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")

    public  PagingResponse getAllUser(
             @RequestParam(defaultValue = "1") int page,
             @RequestParam(name = "item_per_page", defaultValue = "10") int item_per_page) {
        PagingResponse pagingResponse = new PagingResponse(page, item_per_page);

        List<UsersResponse> usersResponseList = new ArrayList<>();

        Iterable<User> users = userRepository.findAll();
        for (User user: users) {
            usersResponseList.add(new UsersResponse(user.getId(), user.getName()));
        }

        pagingResponse.setUsersResponse(usersResponseList);
        return pagingResponse;
    }

    @GetMapping("/users/{id}")
    public UsersResponse getUserById(@PathVariable int id) {
        return new UsersResponse(id, "User " + id);
    }

    @PostMapping("/users")
    public UsersResponse createNewUser(@RequestBody NewUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setAge(request.getAge());
        user = userRepository.save(user);
        return new UsersResponse(user.getId(), user.getName() + user.getAge());
    }
}
