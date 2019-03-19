package com.jf.datadict.controller;

import com.jf.datadict.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class TestController {

    @RequestMapping("/test")
    public String userList2(Model model) {
        List<User> userList = new ArrayList<>();
        userList.add(new User("1","aa","123"));
        userList.add(new User("2","bb","123"));
        userList.add(new User("3","cc","123"));

        model.addAttribute("userList", userList);

        model.addAttribute("userName", "ABC");
        return "test";
    }
}