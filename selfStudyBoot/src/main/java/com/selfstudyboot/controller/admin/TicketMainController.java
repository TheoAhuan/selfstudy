package com.selfstudyboot.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.selfstudyboot.dao.dto.PageInput;
import com.selfstudyboot.entity.Grade;
import com.selfstudyboot.entity.Plot;
import com.selfstudyboot.entity.Ticket;
import com.selfstudyboot.mapper.GradeMapper;
import com.selfstudyboot.mapper.TicketMapper;
import com.selfstudyboot.utils.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TicketMainController {

    @Resource
    private TicketMapper ticketMapper;
    @PostMapping("/admin/getTicket")
    public Response<IPage<Ticket>> getTickets(@RequestBody PageInput input){
        QueryWrapper<Ticket> wrapper = new QueryWrapper<>();
        if (!input.getSearch().isEmpty()){
            wrapper.like("ticket",input.getSearch());
            System.out.println(input.getSearch());
        }

        final Page<Ticket> page = ticketMapper.selectPage(new Page<Ticket>(input.getPageNum(), input.getPageSize()), wrapper);

        return new Response<>(200, "ok", page);
    }

    @Resource
    private GradeMapper gradeMapper;
    @PostMapping("/admin/setGrade")
    public Response setGrades(@RequestBody Ticket ticket){
        //根据ticket的id更新grade
        System.out.println(ticket);

        try {
            //修改本次考试的考试成绩从准考证表中
            ticketMapper.setGradeById(ticket.getId(),ticket.getGrade());
            //通过本次的考试成绩修改学生的最高成绩
          //如果不为空则比较
//            return new Response(200,"ok","设置成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Response(400,"err",e);
        }
        //以上则是成功修改
        //现在修改表中数据
        Grade grade=null;
        try {
            //尝试查询grade表中是否有课程成绩
            grade= gradeMapper.queryGradeByCourseId(ticket.getCourseId());
            System.out.println(grade.getTopGrade());
        }catch (NullPointerException nullPointerException){
            System.out.println("恒明显没有");
            nullPointerException.printStackTrace();
            grade=new Grade();
            grade.setStudentId(ticket.getStudentId());
            grade.setTopGrade(ticket.getGrade());
            grade.setCourseId(ticket.getCourseId());
            gradeMapper.insert(grade);
        }
        //经过以上证明数据库中应当有数据
        if (grade.getTopGrade()<ticket.getGrade()){
            grade.setTopGrade(ticket.getGrade());
            final UpdateWrapper<Grade> wrapper = new UpdateWrapper<>();
            wrapper.eq("course_id",ticket.getCourseId());
            gradeMapper.update(grade,wrapper);
        }
        return new Response(200,"ok","ok");
    }

}

