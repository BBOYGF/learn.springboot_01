package com.learn.controller;


import com.learn.bean.Education;
import com.learn.mapper.UserMapper;
import com.learn.pojo.User;
import com.learn.service.myData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
class mycontroller {

    @Autowired
    myData mydata;
    @RequestMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session)
    {
        System.out.println("密码是："+password);
        if(password.equals("123"))
        {
            System.out.println("密码正确！");
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
    public String getAllUser(Model model)
    {
        List<User> list=userMapper.querUserList();
        model.addAttribute("userAll",list);
        return "/pages/user";
    }

    @RequestMapping("/getPiugin")
    public String getPiugin()
    {
        return "/pages/piugin";
    }

    @GetMapping("/adduser")
    public String addUser(Model model)
    {
        List<Education> education = mydata.getEducation();
        model.addAttribute("educations",education);
        return "pages/add";
    }
    @PostMapping("/adduser")
    public String addUsers(User user)
    {
        System.out.println(user.toString());
        userMapper.addUser(user);
        return "redirect:/getAllUaer";
    }
    @GetMapping("/updateUser/{id}")
    public String update(@PathVariable int id, Model model)
    {
        User user = userMapper.querUserById(id);
        List<Education> education = mydata.getEducation();
        model.addAttribute("educations",education);
        model.addAttribute("user",user);

        return "/pages/update";
    }
    @PostMapping("/updateUser")
    public String update(User user)
    {
        userMapper.updateUser(user);
        System.out.println(user);
        return "redirect:/getAllUaer";
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id )
    {
        userMapper.deleteUser(id);
        return  "redirect:/getAllUaer";
    }


}
