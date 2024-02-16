package com.selfstudyboot.controller.student;

import com.selfstudyboot.dao.dto.OrderInput;
import com.selfstudyboot.dao.vo.GradeVo;
import com.selfstudyboot.entity.Qualification;
import com.selfstudyboot.entity.Subject;
import com.selfstudyboot.mapper.ComplexMapper;
import com.selfstudyboot.mapper.QualificationMapper;
import com.selfstudyboot.mapper.SubjectMapper;
import com.selfstudyboot.service.QualificationService;
import com.selfstudyboot.utils.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
public class GradeController {
    @Resource
    private QualificationService qualificationService;
    @Resource
    private QualificationMapper qualificationMapper;

    @Resource
    private ComplexMapper complexMapper;
    @Resource
    private SubjectMapper subjectMapper;
    @PostMapping("/student/grade/getMyGrade")
    public Response getMyGrade(@RequestBody Qualification qualification){

        //拿到学生的id，查询学生的学籍资格，在数据库中的qualification中，查询学生的专业，需要考试的科目
        System.out.println(qualification.getStudentId());
        //通过学生id查学证明
        qualification= qualificationMapper.getDataBySID(qualification.getStudentId());

        System.out.println(qualification+"当前学生的学籍");
        //查询学生需要报考的课程
        final List<GradeVo> vo = complexMapper.getGradeVoBySID(qualification.getStudentId());

        final HashMap<String, Object> map = new HashMap<>();
        //通过科目id查询科目名称
        Subject subject = subjectMapper.selectById(qualification.getSubjectId());
        map.put("subjectName",subject.getName());
        map.put("vo",vo);
        return new Response<>(200,"ok",map);
    }




}
