package com.selfstudyboot.controller.student;

import com.selfstudyboot.entity.School;
import com.selfstudyboot.mapper.SchoolMapper;
import com.selfstudyboot.utils.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class SchoolController {
    @Resource
    private SchoolMapper schoolMapper;


    @GetMapping("/student/getSchool")
    public Response getSchool(){
        try {
            final List<School> school = schoolMapper.getSchool();
            return new Response<List<School>>(200,"ok",school);
        }catch (Exception e){
            e.printStackTrace();
            return new Response<String>(500,"ERR","GG");
        }

    }


}
