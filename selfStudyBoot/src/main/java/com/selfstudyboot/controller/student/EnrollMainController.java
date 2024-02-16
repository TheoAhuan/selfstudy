package com.selfstudyboot.controller.student;

import com.selfstudyboot.entity.School;
import com.selfstudyboot.mapper.EnrollMapper;
import com.selfstudyboot.mapper.SchoolMapper;
import com.selfstudyboot.service.EnrollService;
import com.selfstudyboot.utils.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class EnrollMainController {
    @Resource
    private EnrollService enrollService;



}
