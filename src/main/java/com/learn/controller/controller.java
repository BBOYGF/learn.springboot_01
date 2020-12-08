package com.learn.controller;


import com.learn.bean.Education;
import com.learn.mapper.UserMapper;
import com.learn.pojo.User;
import com.learn.service.myData;
import com.sun.org.glassfish.external.probe.provider.annotations.ProbeParam;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
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
import sun.misc.BASE64Decoder;
import sun.misc.UUDecoder;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    @ResponseBody
    @PostMapping("/upload2")
    public String upload2( String fileStr) throws IOException, TranscoderException {

        System.out.println(fileStr);
        String fileName=savePic(fileStr);
        return  fileName;
    }
    public String savePic(String base64) throws IOException, TranscoderException {
        //定义一个字节数组
        byte[] imageByte;
        BASE64Decoder decoder=new BASE64Decoder();//创建一个解码类
        imageByte=decoder.decodeBuffer(base64);//解码为字节数组
        ByteArrayInputStream bis=new ByteArrayInputStream(imageByte);//将字节数组转换为字节数组流
        TranscoderInput transcoderInput = new TranscoderInput(bis);//将流转换为图片流
        PNGTranscoder pngTranscoder = new PNGTranscoder();//创建PNG转换流
        // 文件名称可根据自己的业务需求自定
        String s = UUID.randomUUID().toString().replace("-", "");//获取随机名称
        String fileName = s + ".png";
        // 文件路径也可以根据自己的需求自定义
        File outputfile = new File("D:\\Desktop\\" + fileName);

        FileOutputStream pngFileStream = new FileOutputStream(outputfile);

        TranscoderOutput outputPngImage = new TranscoderOutput(pngFileStream);
        pngTranscoder.transcode(transcoderInput, outputPngImage);
        return fileName;

    }
}
