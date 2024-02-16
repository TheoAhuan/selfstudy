package com.selfstudyboot.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.selfstudyboot.entity.Enroll;
import com.selfstudyboot.entity.Order;
import com.selfstudyboot.mapper.EnrollMapper;
import com.selfstudyboot.service.EnrollService;
import com.selfstudyboot.utils.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class EnrollController {
    @Resource
    private EnrollService enrollService;
    @Resource
    private EnrollMapper enrollMapper;
    @PostMapping("/enroll/edit")
    public boolean edit(@RequestBody Enroll enroll){

        try {
           enrollMapper.updateById(enroll);
           return true;
       }catch (Exception e){
           e.printStackTrace();
           return false;
       }

    }

    @PostMapping("enroll/secKill")
    public Response<String> secKill(@RequestBody Order order){
        //通过前端输入的学生id和考试id进行查询考试的剩余量，信息
        //1.


        return null;
    }
//    @Resource
//    private EnrollMapper enrollMapper;
    @PostMapping("/admin/create/enroll")
    public Response<String> create(@RequestBody Enroll enroll){
        System.out.println(enroll);
        try {
            enrollMapper.insert(enroll);
            return new Response<>(200,"ok","ok");
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(400,"err","插入失败");

        }


    }

    @PostMapping("/admin/del/enroll")
    public Response<String> del(@RequestBody Enroll enroll){
        try {
            enrollMapper.deleteById(enroll.getId());
            return new Response<>(200,"ok","ok");
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(400,"err","err");

        }
    }


}
