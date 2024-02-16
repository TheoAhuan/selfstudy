package com.selfstudyboot.controller.admin;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.selfstudyboot.dao.dto.NoBanInput;
import com.selfstudyboot.entity.Student;
import com.selfstudyboot.mapper.SchoolMapper;
import com.selfstudyboot.mapper.StudentMapper;
import com.selfstudyboot.service.StudentService;
import com.selfstudyboot.utils.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;

@RestController
public class StudentManageController {
    @Resource
    private StudentService studentService;
    @PostMapping("/noban/banStudent")
    public boolean banStudent(@RequestBody NoBanInput input){


        if (input.getTime()==null){
            return false;
        }
        System.out.println(input.getTime());
        try {
            studentService.update(input);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }





    }
    @Resource
    private StudentMapper studentMapper;
    @GetMapping("/ban/remove")
    public Response<String> remove(@RequestParam Integer id){
        final UpdateWrapper<Student> wrapper = new UpdateWrapper<>();


        try {
            wrapper.set("ban_time",null).set("msg",null).eq("id",id);
            studentMapper.update(new Student(),wrapper);
            return new Response<>(200,"ok","已经删除");
        }catch (Exception e){
            return new Response<>(500,"ok","删除错误");

        }
    }
}
