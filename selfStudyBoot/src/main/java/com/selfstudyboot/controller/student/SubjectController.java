package com.selfstudyboot.controller.student;

import com.selfstudyboot.dao.dto.SubjectInInput;
import com.selfstudyboot.entity.Qualification;
import com.selfstudyboot.service.QualificationService;
import com.selfstudyboot.service.SubjectService;
import com.selfstudyboot.utils.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SubjectController {
    @Resource
    private SubjectService subjectService;
    @Resource
    private QualificationService qualificationService;
    @PostMapping("/subject/getQualification")
    public Response<Map<String,Object>> createQualification(@RequestBody SubjectInInput input){
        System.out.println("报名学籍时的学生id是"+input.getStudentId());
        System.out.println("报名学籍时的专业id是"+input.getSubjectId());
        final HashMap<String, Object> map = new HashMap<>();
        try {

            Qualification record= qualificationService.getRecord(input.getStudentId());
            if (record!=null){
                map.put("500","你已经报名了");
                return new Response<>(500, "err", map);
            }
            final Integer q = qualificationService.create(input);
            return new Response<>(200, "ok", null);

        }catch (RuntimeException runtimeException){
            runtimeException.printStackTrace();
            map.put("err","请求超时");
        }catch (Exception e){
            map.put("GG","未定义错误");
            e.printStackTrace();
            return new Response<>(200, "ok", map);

        }


        map.put("403","前端请求数据有误");
        return new Response<>(403, "bad request", map);
    }


}
