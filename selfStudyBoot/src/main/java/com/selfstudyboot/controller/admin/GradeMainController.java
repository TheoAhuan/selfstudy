package com.selfstudyboot.controller.admin;

import com.selfstudyboot.mapper.GradeMapper;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class GradeMainController {

    @Resource
    private GradeMapper gradeMapper;

}
