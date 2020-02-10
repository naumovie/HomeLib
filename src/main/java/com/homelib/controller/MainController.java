package com.homelib.controller;

import com.homelib.model.Book;
import com.homelib.model.User;
import com.homelib.repos.BookRepo;
import com.homelib.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Iterator;
import java.util.List;

@Controller
public class MainController {
   @Autowired
   private BookService bookService;

   @Autowired
   private BookRepo bookRepo;

   @GetMapping("/")
   public String greeting() {
       return "redirect:/main";
   }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/main")
    public String main(@AuthenticationPrincipal User user,
                       @RequestParam(required = false) String filter,
                       Model model) {
        List<Book> books;

        if (!StringUtils.isEmpty(filter)) {
            books = bookRepo.findByTitle(filter);

            for (Book foundBook: bookRepo.findByAuthor(filter)
                 ) {
                if (!books.contains(foundBook)) {
                    books.add(foundBook);
                }
            }
        } else {
            books = bookService.findAll();
        }
        model.addAttribute("books",books);
        return "main";
    }






}
