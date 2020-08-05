package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController

public class UserController {

    @GetMapping("/users")
    // TODO: step_02 is done
    //       there have some bug to fix.
    public  List<UsersResponse> getAllUser(
             @RequestParam int page
            ,int item_per_page) {
        List<UsersResponse> usersResponseList = new ArrayList<>();
        usersResponseList.add(new UsersResponse(1, "User 1"));
        usersResponseList.add(new UsersResponse(2, "User 2"));
        usersResponseList.add(new UsersResponse(3, "User 3"));
        return usersResponseList;
    }

    @GetMapping("/users/{id}")
    public UsersResponse getUserById(@PathVariable int id) {
        return new UsersResponse(id, "User " + id);
    }
}
