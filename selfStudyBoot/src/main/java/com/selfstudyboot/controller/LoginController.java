package com.selfstudyboot.controller;

import com.selfstudyboot.dao.dto.LoginInput;
import com.selfstudyboot.entity.Roler;
import com.selfstudyboot.entity.Student;
import com.selfstudyboot.mapper.StudentMapper;
import com.selfstudyboot.service.AdminService;
import com.selfstudyboot.service.RolerService;
import com.selfstudyboot.service.StudentService;
import com.selfstudyboot.utils.JwtUtil;
import com.selfstudyboot.utils.Response;
import com.selfstudyboot.utils.ResultCode;
import com.selfstudyboot.utils.UnifiedResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
public class LoginController {
    @Resource
    private AdminService adminService;
    @Resource
    private RolerService rolerService;
    @Resource
    private StudentService studentService;

    @PostMapping("/student/login")
    public Response<Object> login(@RequestBody Student student){
        System.out.println(student);
        Map<String,String> payload=null;
        HashMap<String, Object> map=null;


        try {
            final StudentMapper mapper = studentService.getMapper();
            final Student studentDB = mapper.login(student.getCard(), student.getPassword());
            payload=new HashMap<>();//token的负载
            payload.put("id",studentDB.getId().toString());
            payload.put("username",studentDB.getUsername());
            String token= JwtUtil.getToken(payload);
//             response = new Response<>();
//            response.setCode(200);
//            response.setMsg("true");
             map = new HashMap<>();
            map.put("token",token);
            map.put("student",studentDB);

            return new Response<>(200,"true",map);
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(500,"false",null);
        }
    }

    @PostMapping("/login")
    public UnifiedResult<Map<String,Object>> login(@RequestBody LoginInput loginInput) {
        System.out.println(loginInput.toString());

        Roler rolerDB =null;
        UnifiedResult<Map<String,Object>> unifiedResult = new UnifiedResult<Map<String,Object>>();
        HashMap<String, Object> data = new HashMap<>();
        try {
            rolerDB= rolerService.queryByIdCardAndPWD(loginInput.getIdCard(),loginInput.getPassword());
            System.out.println(rolerDB.toString());
            Map<String,String> payload=new HashMap<>();//token的负载
            payload.put("id",rolerDB.getId().toString());
            payload.put("username",rolerDB.getUsername());
            //生成JWT
            String token= JwtUtil.getToken(payload);
             unifiedResult.setResultCode(ResultCode.SUCCESS);
             data.put("token",token);
             data.put("user",rolerDB);
             unifiedResult.setData(data);
        }catch (Exception e){
            e.printStackTrace();
            unifiedResult.setResultCode(ResultCode.FAILURE);

        };

        return unifiedResult;
    }
    @GetMapping("/login/user")
    public Response<List<Roler>> show(){
        final List<Roler> strings = new ArrayList<>();
        final Roler roler = new Roler();
        roler.setUsername("caohuan");
        strings.add(roler);
        return new Response<List<Roler>>(200,"suc",strings);


    }
}
