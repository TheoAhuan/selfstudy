package com.selfstudyboot.controller;

import com.selfstudyboot.dao.dto.PageInput;
import com.selfstudyboot.dao.dto.Remain;
import com.selfstudyboot.dao.dto.SecKillGet;
import com.selfstudyboot.dao.dto.SecKillInput;
import com.selfstudyboot.entity.Roler;
import com.selfstudyboot.entity.Student;
import com.selfstudyboot.entity.Subject;
import com.selfstudyboot.mapper.CourseMapper;
import com.selfstudyboot.mapper.StudentMapper;
import com.selfstudyboot.mapper.SubjectMapper;
import com.selfstudyboot.service.AdminService;
import com.selfstudyboot.service.EnrollService;
import com.selfstudyboot.service.RolerService;
import com.selfstudyboot.service.SubjectService;
import com.selfstudyboot.utils.Response;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {


    @Resource
    private AdminService adminService;
    @Resource
    private RolerService rolerService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private EnrollService enrollService;
    @Resource
    private SubjectService subjectService;

    @RequestMapping("/")
    public String hello(){
        return "index";
    }

    @PostMapping("/test")
    public boolean kill(@RequestBody SecKillGet secKillGet){
        return true;

    }
    @PostMapping("/test/noban")
    public Response<List<Roler>> noBan(@RequestBody PageInput input){
        try {
             List<Roler> page = rolerService.getPageNoBan(input);
          return new Response<>(200, "suc", page);

        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(500,"err",null);
        }
    }
    @PostMapping("/test/ban")
    public Response<List<Roler>> Ban(@RequestBody PageInput input){

//
//        final ArrayList<Roler> rolers = new ArrayList<>();
//
//        final Roler roler = new Roler();
//        roler.setUsername("ca");
//        roler.setBan(new Date());
//        roler.setIdCard("ascjhabk");
//        rolers.add(roler);
//        return new Response<>(200,"sad",rolers);

//
        try {
            List<Roler> page = rolerService.getPageBan(input);
            return new Response<>(200, "suc", page);

        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(500,"err",null);
        }
    }
//    @PostConstruct
//    public void init(){
//           List<Remain> remains  =enrollService.getRemainList();
//           for (Remain r:remains){
//               stringRedisTemplate.opsForValue().set(r.getId().toString(),r.getRemain().toString());
//           }
//        System.out.println("redis的缓存初始化已经成功");
////           new Thread(()->{},"redis");
//
//
//    }
    @PostMapping("test/secKill")
    public boolean secKill(SecKillInput input){
         Long remain = stringRedisTemplate.opsForValue().decrement(input.getTestId().toString());
        System.out.println(remain);
        if (remain<0){
            return false;
        }
        try{
            return enrollService.secKill(input.getTestId());


        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    @Resource
    private SubjectMapper subjectMapper;
    @RequestMapping("subject/getSubject")
    public Subject getSubjectInstance(Integer subjectId){
        return subjectMapper.selectById(subjectId);
    }
    @Resource
    private CourseMapper courseMapper;
    @PostMapping("/student/getCourse")
    private Response<List<String>> getCourseName(@RequestBody Subject  subject){
//        return courseMapper.getCourseName(subjectName);
        System.out.println("当前专业名称"+subject.getName());
        final List<String> courseName = courseMapper.getCourseName(subject.getName());
        System.out.println(courseName.isEmpty());


        return new Response<List<String>>(200,"ok",courseName);


    }
    @Resource
    private StudentMapper studentMapper;
    @PostMapping("/student/login2")
    public Response<Student> login(Student student){
        final Student login = studentMapper.login(student.getCard(), student.getPassword());
        final Response<Student> response = new Response<>();


        return null;
    }


}
