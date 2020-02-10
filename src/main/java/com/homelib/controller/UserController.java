package com.homelib.controller;

import com.homelib.model.Book;
import com.homelib.model.User;
import com.homelib.repos.UserRepo;
import com.homelib.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Transient;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller


@RequestMapping("/user")
public class UserController {


    @Autowired
    UserRepo userRepo;

    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal User user,
                              Model model) {

        model.addAttribute("user",user);
        return "/profile";
    }

    @PostMapping("/profile")
    public String changeProfile(
            @RequestParam("userId") User user,
            @RequestParam Map<String,String> form
    )
    {
        if (userService.updateProfile(user,form)) {
            return "redirect:/main";
        }
        return "redirect:/user/profile";

    }

    @GetMapping("/mybook")
    public String myBook(@AuthenticationPrincipal User user,
                         @RequestParam(required = false) String filter,
                         Model model) {
        Iterable<Book> books;
        if (!StringUtils.isEmpty(filter)) {
             books = userService.getUserBook(user);
        } else {
             books = userService.getUserBook(user);
        }

        model.addAttribute("books",books);

        return "mybook";
    }
}
