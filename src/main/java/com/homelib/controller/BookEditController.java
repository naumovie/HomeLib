package com.homelib.controller;

import com.homelib.model.Book;
import com.homelib.model.Genre;
import com.homelib.model.User;
import com.homelib.repos.BookRepo;
import com.homelib.repos.UserRepo;
import com.homelib.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class BookEditController {
    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserRepo userRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/bookedit")
    public String bookedit(Model model)
    {
        model.addAttribute("genres",Genre.values());
        return "bookedit";
    }

    @PostMapping("/bookedit")
    public String add(@AuthenticationPrincipal User user,
                      @Valid Book book,
                      BindingResult bindingResult,
                      Model model,
                      @RequestParam(required = false, name = "file") MultipartFile cover,
                      @RequestParam Map<String,String> form) throws IOException {


        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = ControllerUtils.getErrors(bindingResult);
            model.addAttribute("errors",errorMap);
            model.mergeAttributes(errorMap);
            model.addAttribute("book",book);
            model.addAttribute("message","Book wasnt added, something goes wrong");

        } else {
            bookService.addBook(user,book,model,cover,form);
        }


        return "redirect:/bookedit";
    }

    @GetMapping("book/{book}")
    public String bookPage(@PathVariable Book book,
                           @AuthenticationPrincipal User user,
                           Model model) {
        model.addAttribute("book",book);
        model.addAttribute("user",user);
        return "bookpage";
    }

    @GetMapping("book/edit/{book}")
    public String editBook(@PathVariable Book book,
                           @AuthenticationPrincipal User user,
                           Model model) {
        model.addAttribute("book",book);
        model.addAttribute("user",user);
        model.addAttribute("genres",Genre.values());

        return "editpage";
    }

    @PostMapping("book/edit/{book}")
    public String saveChanges(@RequestParam("userId") User user,

                              @RequestParam("bookId") @Valid Book book,

                               Model model,
                              @RequestParam(required = false, name = "file") MultipartFile cover,
                              @RequestParam Map<String,String> form) throws IOException {

       if (bookService.saveBook(user,book,cover,form,model)) {
           return "redirect:/main";
       } else {
           model.addAttribute("titleError","title cant be empty");
           return "redirect:/book/edit/" + book.getId();
       }


    }

    @GetMapping("book/add/{book}")
    @Transactional
    public String addToUser(@AuthenticationPrincipal User user,
                            @PathVariable Book book,
                            Model model) {

        bookService.addToUser(user, book);


        return "redirect:/book/" + book.getId();
    }






}
