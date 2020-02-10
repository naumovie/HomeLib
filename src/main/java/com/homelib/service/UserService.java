package com.homelib.service;

import com.homelib.model.Book;
import com.homelib.model.Role;
import com.homelib.model.User;
import com.homelib.repos.BookRepo;
import com.homelib.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Transient;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;


@Transient
@Service
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private BookRepo bookRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public boolean addUser(User user) {
        User userDB = userRepo.findByUsername(user.getUsername());

        if (userDB != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());
        user.setActivationCode(UUID.randomUUID().toString());

        userRepo.save(user);

        String message = String.format(
                "Hello, %s \n " +
                       "Welcome to HomeLib. Click to the link:" +
                        "http://localhost:8080/activate/%s",
                user.getUsername(), user.getActivationCode()
        );
        mailSender.send(user.getEmail(),"Activation code",
                message);

        return true;
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        //User user1 = userRepo.getByActivationCode(code);

        if (user == null) {
            return false;
        }

       user.setActivationCode(null);
        //userRepo.setActivationCode("aaa",user.getId());
        userRepo.save(user);

        return true;
    }


    public boolean updateProfile(User user, Map<String,String> form) {
        Set<String> strings = form.keySet();

        List<String> fields = new ArrayList<>(Arrays.asList("username","password","email"));
        Iterator<String> iter = fields.iterator();

         String username = form.get(iter.next());
         String password = form.get(iter.next());
         String email = form.get(iter.next());

        if (StringUtils.isEmpty(username)
                || StringUtils.isEmpty(password)
        || StringUtils.isEmpty(email)) {
            return false;
        } else {

            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            userRepo.save(user);
            return true;
        }
    }


    public Iterable<Book> getUserBook(User user) {
        return bookRepo.findAll();
    }
}
