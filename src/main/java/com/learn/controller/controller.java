package com.learn.controller;


import com.learn.bean.Education;
import com.learn.mapper.UserMapper;
import com.learn.pojo.User;
import com.learn.service.myData;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


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
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) throws IOException {
        //根据相对路径获取保存文件的绝对路径
        String realPath = System.getProperty("user.dir");//request.getSession().getServletContext().getRealPath("");
        System.out.println("图片保存位置是："+realPath);
        //获取文件的后缀
        String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
        //自定义文件名
        String fileName = UUID.randomUUID().toString().replace("-", "")
                + new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"."+extension;
        System.out.println("图片的位置是："+fileName);
        //处理文件上传
        multipartFile.transferTo(new File(realPath,fileName));
        System.out.println("getOriginalFilename是："+multipartFile.getOriginalFilename());
        System.out.println("getName是："+multipartFile.getName());
        System.out.println("getName是：");
        System.out.println("当前项目的路径地址是："+System.getProperty("user.dir"));
        System.out.println("classes目录绝对路径1："+ ClassUtils.getDefaultClassLoader().getResource("").getPath());
        System.out.println("classes目录绝对路径2："+ ResourceUtils.getURL("classpath:").getPath());
        System.out.println("file是:"+request.getSession().getServletContext().getRealPath(""));
        return "redirect:/getAllUaer";
    }

}
