package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        Pageable firstPageWithOneElement = PageRequest.of(page-1, item_per_page);

        Pageable secondPageWithTwoElements = PageRequest.of(1, 2);

        Page<User> users = userRepository.findAll(firstPageWithOneElement);
//        Page<User> result = userRepository.findAll(PageRequest.of(page-1, item_per_page));
//        List<User> allUsersId = userRepository.findAllById(2, secondPageWithTwoElements);
//        Iterable<User> users = userRepository.findAll();
        for (User user: users) {
            usersResponseList.add(new UsersResponse(user.getId(), user.getName()));
        }

        pagingResponse.setUsersResponse(usersResponseList);
        return pagingResponse;
    }

    @GetMapping("/users/{id}")
    public UsersResponse getUserById(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        return new UsersResponse(user.get().getId(), user.get().getName());
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
