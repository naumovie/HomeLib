package com.homelib.service;

import com.homelib.model.Book;
import com.homelib.model.Genre;
import com.homelib.model.Role;
import com.homelib.model.User;
import com.homelib.repos.BookRepo;
import com.homelib.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepo bookRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    private UserRepo userRepo;


    public List<Book> findAll() {
        return bookRepo.findAll();
    }


    public boolean addBook(User user, Book book, Model model, MultipartFile cover, Map<String, String> form) throws IOException {
        if (cover != null && !cover.isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + cover.getOriginalFilename();

            cover.transferTo(new File(uploadPath + "/" + resultFilename));
            book.setCover(resultFilename);
        }
        model.addAttribute("message","Book was succesfully added");

        Set<String> genres = Arrays.stream(Genre.values())
                .map(Genre::name)
                .collect(Collectors.toSet());



        book.getGenres().clear();

        Set<String> strings = form.keySet();
        for(String key : form.keySet()) {

            if (genres.contains(key)) {
                 book.getGenres().add(Genre.valueOf(key));
            }
        }


        bookRepo.save(book);
        return true;
    }

    public boolean saveBook(User user, Book book, MultipartFile cover, Map<String, String> form, Model model) throws IOException {
        List<String> fields = new ArrayList<>(Arrays.asList("title", "year", "author", "isbn", "description"));
        Set<String> strings = form.keySet();
        Iterator<String> iter = fields.iterator();

        String title = form.get(iter.next());
        if (StringUtils.isEmpty(title)) {

            model.addAttribute("titleError", "title cant be empty!");
            return false;
        } else {


            book.setTitle(title);
            book.setYear(form.get(iter.next()));
            book.setAuthor(form.get(iter.next()));
            book.setIsbn(form.get(iter.next()));
            book.setDescription(form.get(iter.next()));


            if (cover != null && !cover.isEmpty()) {
                File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }

                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + cover.getOriginalFilename();

                cover.transferTo(new File(uploadPath + "/" + resultFilename));
                book.setCover(resultFilename);
            }


            Set<String> genres = Arrays.stream(Genre.values())
                    .map(Genre::name)
                    .collect(Collectors.toSet());


            book.getGenres().clear();


            for (String key : form.keySet()) {

                if (genres.contains(key)) {
                    book.getGenres().add(Genre.valueOf(key));
                }
            }


            bookRepo.save(book);
            return true;
        }
    }

    @Transactional
    public void addToUser(User user,Book book) {
        user.getAddedBooks().add(book);
        userRepo.save(user);
    }
}
