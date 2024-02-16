package com.selfstudyboot.controller.admin;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.selfstudyboot.dao.dto.PageInput;
import com.selfstudyboot.entity.Enroll;
import com.selfstudyboot.entity.Plot;
import com.selfstudyboot.entity.Roler;
import com.selfstudyboot.entity.Student;
import com.selfstudyboot.mapper.EnrollMapper;
import com.selfstudyboot.mapper.PlotMapper;
import com.selfstudyboot.mapper.RolerMapper;
import com.selfstudyboot.service.AdminService;
import com.selfstudyboot.service.EnrollService;
import com.selfstudyboot.service.RolerService;
import com.selfstudyboot.service.StudentService;
import com.selfstudyboot.utils.Response;
import lombok.val;
import net.sf.jsqlparser.schema.Table;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminMainController {
        //管理员的主要业务书写
    //admin业务注入
    @Resource
    private AdminService adminService;
    @Resource
    private StudentService studentService;
    @Resource
    private RolerService rolerService;
    @Resource
    private RolerMapper rolerMapper;
    @PostMapping("/page")
    public Response<List<Roler>> defaultList(@RequestBody PageInput pageInput){

        Page<Roler> rolerPage = null;
             QueryWrapper<Roler> wrapper=new QueryWrapper<>();
             wrapper.ne("role","admin");
        List<Roler> rolerList=null;
        try {
            rolerPage = new Page<>(pageInput.getPageNum(),pageInput.getPageSize());
            rolerList=rolerService.getPage(rolerPage,wrapper);
            return new Response<>(200,"suc",rolerList);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("参数为空");
            rolerPage = new Page<>(1,10);
            rolerList=rolerService.getPage(rolerPage,wrapper);
            return new Response<List<Roler>>(200,"suc",rolerList);
        }
    }
    @GetMapping("show")
    public Response<Page<Roler>> findPage(@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10") Integer pageSize,@RequestParam(defaultValue = "") String search){


      Page<Roler> rolerPage  =rolerMapper.selectPage(new Page<>(pageNum,pageSize), Wrappers.
              <Roler>lambdaQuery()
              .and(i -> i.eq(Roler::getIdCard,search))
              .ne(Roler::getRole,"admin"));
        return new Response<Page<Roler>>(200,"suc",rolerPage);
    }
    //学籍管理中，未禁止考生页面数据的功能实现
    //首先是初始数据的渲染
//    @PostMapping("/noban")
//    public Response createNoBan(@RequestBody PageInput pageInput){
//        final IPage page = new Page<>(pageInput.getPageNum(), pageInput.getPageSize());
//        new QueryWrapper<>()
//
//    }

    @PostMapping("/ban")
    public Response<IPage<Student>> showBan(@RequestBody PageInput input){


        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String format = simpleDateFormat.format(new Date());
        System.out.println(format);
        final QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.select("ban_time","username","card","phone","id","msg").gt("ban_time",format);
        final Page<Student> page = new Page<Student>(input.getPageNum(), input.getPageSize());
         IPage<Student> banPage =  studentService.getBanStudent(page, wrapper);
        banPage.getRecords().forEach(System.out::println);

//            new Date();
//            studentService.selectBan()
        return new Response<>(200, "ok", banPage);

    }
    @PostMapping("/noban")
    public Response<IPage<Student>> showNoBan(@RequestBody PageInput input){
        final Date date = new Date();
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String format = simpleDateFormat.format(date);
        System.out.println();
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.select("ban_time","username","card","phone","id","role").isNull("ban_time").or(i->i.lt("ban_time",format));

        IPage<Student> noPage =  studentService.getBanStudent(new Page<Student>(input.getPageNum(), input.getPageSize()), wrapper);
        noPage.getRecords().forEach(System.out::println);

//            new Date();
//            studentService.selectBan()
        return new Response<>(200, "ok", noPage);
    }


    @Resource
    private EnrollService enrollService;
    @PostMapping("/getTest")
    public Response getTest(@RequestBody PageInput input){
        QueryWrapper<Enroll> wrapper = new QueryWrapper<>();
        if (input.getSearch()!=null){
            wrapper.like("name",input.getSearch());
        }
        final Page<Enroll> page = new Page<>(input.getPageNum(), input.getPageSize());
        final EnrollMapper mapper = enrollService.getMapper();
        final Page<Enroll> enrollPage = mapper.selectPage(page, wrapper);
        return new Response<IPage<Enroll>>(200,"ok",enrollPage);


    }
//@Resource
//private EnrollMapper enrollMapper;
//    @GetMapping("/admin/getEnroll")
//    public Response getE(@RequestParam String search){
//
//        final QueryWrapper<Enroll> wrapper = new QueryWrapper<>();
//        wrapper.like("name",search);
//        try {
//            enrollMapper.selectList(wrapper);
//            return new Response<List>()
//        }
//
//    }

}
