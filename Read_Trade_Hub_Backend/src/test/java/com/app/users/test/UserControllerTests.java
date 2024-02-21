package com.app.users.test;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.app.BooksSpringbootApplication;
import com.app.users.models.User;
import com.app.users.repositories.IUserRepository;

@SpringBootTest(classes = BooksSpringbootApplication.class)
@Transactional
public class UserControllerTests {

    private final IUserRepository userRepository;

    @Autowired
    public UserControllerTests(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    @Rollback(false)
    @Order(1)
    public void testFindUsers() {
        List<User> list = userRepository.findAll();
        Assertions.assertEquals(4, list.size());
    }

    @Test
    @Rollback(false)
    @Order(2)
    public void testCreateUser() {
        String email = "50@gmail.com";
        userRepository.save(new User(email, "ACCOUNTING", "Lucy", "Smith"));
        User savedUser = userRepository.findById(email).orElse(null);
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals(savedUser.getEmail(), email);
    }

    @Test
    @Rollback(false)
    @Order(3)
    public void testFindUserByEmail() {
        List<User> firstFindAllList = userRepository.findAll();
        if (firstFindAllList.isEmpty()) return;
        String email = firstFindAllList.get(0).getEmail();
        User user = userRepository.findById(email).orElse(null);
        Assertions.assertNotNull(user);
        Assertions.assertEquals(email, user.getEmail());
    }

    @Test
    @Rollback(false)
    @Order(4)
    public void testUpdateUser() {
        List<User> firstFindAllList = userRepository.findAll();
        if (firstFindAllList.isEmpty()) return;
        String email = firstFindAllList.get(0).getEmail();
        String changedFirstName = "Lucy";
        User user = userRepository.findById(email).orElse(null);
        Assertions.assertNotNull(user);
        user.setFirstName(changedFirstName);
        userRepository.save(user);
        User changedUser = userRepository.findById(email).orElse(null);
        Assertions.assertNotNull(changedUser);
        Assertions.assertEquals(changedUser.getFirstName(), changedFirstName);
    }

    @Test
    @Rollback(false)
    @Order(5)
    public void testDeleteUser() {
        List<User> firstFindAllList = userRepository.findAll();
        if (firstFindAllList.isEmpty()) return;
        int firstFindAllListNums = firstFindAllList.size();
        String email = firstFindAllList.get(0).getEmail();
        userRepository.deleteById(email);
        List<User> secondFindAllList = userRepository.findAll();
        Assertions.assertEquals(firstFindAllListNums - 1, secondFindAllList.size());
    }
}
