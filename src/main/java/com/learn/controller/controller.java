package com.learn.controller;


import com.learn.mapper.UserMapper;
import com.learn.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
class mycontroller {


    @RequestMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session)
    {
        System.out.println("密码是："+password);
        if(password.equals("123"))
        {
            System.out.println("密码真确！");
            session.setAttribute("loginUser",username);
            return "redirect:/main";
        }else {
            System.out.println("密码错误！！");
            model.addAttribute("msg","用户名或者密码错误！");
            return "pages/login";
        }

    }
    @RequestMapping("/index")
    public String login()
    {

            return "redirect:/main";
    }
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/getAllUaer")
    public List<User> getUser()
    {
        List<User> list=userMapper.querUserList();
        return list;
    }

}
