package com.selfstudyboot.controller.student;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.selfstudyboot.entity.Ticket;
import com.selfstudyboot.mapper.TicketMapper;
import com.selfstudyboot.utils.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class TicketController {

    @Resource
    private TicketMapper ticketMapper;


    @GetMapping("/student/getMyticket")
    public Response<List<Ticket>> getTicker(@RequestParam Integer studentId){
        QueryWrapper<Ticket> wrapper=null;
        try {
            wrapper= new QueryWrapper<>();
            wrapper.eq("student_id",studentId);
            final List<Ticket> tickets = ticketMapper.selectList(wrapper);
            return new Response<>(200,"ok",tickets);
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(500,"err",null);
        }
    }

}
