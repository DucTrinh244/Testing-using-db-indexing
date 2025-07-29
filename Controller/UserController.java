package com.example.testIndexingInDatabase.Controller;

import com.example.testIndexingInDatabase.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController     {
    @Autowired
    private UserService userTestService;

    @GetMapping("/test-performance")
    public String testPerformance() {
        userTestService.testQueries();
        return "Đã test xong, xem console để biết thời gian";
    }
    @GetMapping("/generate-users")
    public String generateUsers() {
        userTestService.generateUsers();
        return "Đã tạo người dùng, xem console để biết kết quả";
    }
}
