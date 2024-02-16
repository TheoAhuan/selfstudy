package com.selfstudyboot.controller.student;

import com.selfstudyboot.dao.dto.RegisterInput;
import com.selfstudyboot.entity.Student;
import com.selfstudyboot.mapper.StudentMapper;
import com.selfstudyboot.service.StudentService;
import com.selfstudyboot.utils.Response;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController

public class RegisterController {
    @Resource
    private StudentService studentService;
    @PostMapping("/register/checkID")
    public boolean checkCard(@RequestBody Student student){
        System.out.println(student.getCard()+"..............");


        return studentService.checkCard(student.getCard());
    }

    @Resource
    private JavaMailSender javaMailSender;
    @PostMapping("/register/checkEmail")
    public Response<String> checkMali(@RequestBody Student student){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    //邮件设置值
       try {
           simpleMailMessage.setSubject("测试邮件-java邮件任务");//邮件主题

        simpleMailMessage.setText("前往别告诉别人，自考注册验证码8825385");//邮件内容
        simpleMailMessage.setTo(student.getEmail());//邮件发给谁
        simpleMailMessage.setFrom("2061360807@qq.com"); //邮件来自于谁
        javaMailSender.send(simpleMailMessage);
        return new Response<>(200,"ok","发送成功");
    }catch (Exception e){
           e.printStackTrace();
           return new Response<>(400,"err","错误");
       }

    }
    @Resource
    private StudentMapper studentMapper;
    @PostMapping("/register/student")
    public Response createStudent(@RequestBody RegisterInput input){
        System.out.println(input);

        if (!input.getMsg().equals("8825385")){
            return new Response(201,"ok","验证码错误");
        }
        Student student = new Student();
        try {
        student.setPassword(input.getPassword());
        student.setCard(input.getIdCard());
        student.setEmail(input.getEmail());
        studentMapper.insert(student);
            return new Response(200,"ok","你已经成功注册");
    }catch (Exception e){
            e.printStackTrace();
            return new Response(500,"ok","注册失败");
        }

    }
//    SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//    //邮件设置值
//        simpleMailMessage.setSubject("测试邮件-java邮件任务");//邮件主题
//        simpleMailMessage.setText("测试邮件,测试java发送邮件任务......");//邮件内容
//        simpleMailMessage.setTo("1686185944@qq.com");//邮件发给谁
//        simpleMailMessage.setFrom("2061360807@qq.com"); //邮件来自于谁
//        javaMailSender.send(simpleMailMessage);

}
