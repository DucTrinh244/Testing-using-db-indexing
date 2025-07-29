package com.example.testIndexingInDatabase.Service;

import com.example.testIndexingInDatabase.Model.UserModel;
import com.example.testIndexingInDatabase.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void testQueries() {
        Random random = new Random();

        // 1. Test by ID
        long startId = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            long id = 1 + random.nextInt(100000); // giả sử có 100,000 bản ghi
            userRepository.findById(id);
        }
        long endId = System.currentTimeMillis();
        System.out.println("Truy vấn theo ID mất: " + (endId - startId) + " ms");

        // 2. Test by Username (có index)
        long startUsername = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String username = "user" + random.nextInt(100000);
            userRepository.findByUsername(username);
        }
        long endUsername = System.currentTimeMillis();
        System.out.println("Truy vấn theo Username mất: " + (endUsername - startUsername) + " ms");

        // 3. Test by Email (không có index)
        long startEmail = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String email = "user" + random.nextInt(100000) + "@mail.com";
            userRepository.findByEmail(email);
        }
        long endEmail = System.currentTimeMillis();
        System.out.println("Truy vấn theo Email mất: " + (endEmail - startEmail) + " ms");
    }

    public void generateUsers(){
        if (userRepository.count() >= 100000) {
            System.out.println("Đã có đủ 100.000 người dùng trong cơ sở dữ liệu.");
            return;
        }else {

            List<UserModel> users = new ArrayList<>();
            for (long i = 1; i <= 100_000; i++) {
                UserModel user = new UserModel();
                user.setId(i);
                user.setUsername("user" + i);
                user.setEmail("user" + i + "@mail.com");
                users.add(user);

                if (i % 1000 == 0) {
                    userRepository.saveAll(users);
                    users.clear();
                    System.out.println("Đã chèn đến user: " + i);
                }
            }

            if (!users.isEmpty()) {
                userRepository.saveAll(users);
            }

        }    }
}
