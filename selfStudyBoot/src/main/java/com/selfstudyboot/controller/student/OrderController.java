package com.selfstudyboot.controller.student;

import com.selfstudyboot.dao.dto.OrderInput;
import com.selfstudyboot.dao.dto.Remain;
import com.selfstudyboot.entity.Enroll;
import com.selfstudyboot.entity.Order;
import com.selfstudyboot.entity.Ticket;
import com.selfstudyboot.mapper.OrderMapper;
import com.selfstudyboot.service.EnrollService;
import com.selfstudyboot.service.OrderService;
import com.selfstudyboot.utils.Response;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
public class OrderController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderService orderService;
    @Resource
    private EnrollService enrollService;
    ConcurrentHashMap<String,Object> concurrentHashMap;
//        @PostConstruct
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
    @PostMapping("/student/order/create")
    public Response createOrder(@RequestBody OrderInput orderInput) throws ExecutionException, InterruptedException, TimeoutException {
        System.out.println(orderInput+"报名信息");
        //1.验证是否存在订单
        //目标1，查询缓存里的订单数据
        //然后查询数据库里的订单数据
        Order order;
        try {
              order= orderMapper.queryOrder(orderInput.getStudentId(),orderInput.getCourseId());
              //如果数据库里订单数据不为空，则说明数据库有该订单，直接返回信息，用户已经存在该考试的订单
              if (order!=null){
                  return new Response<String>(200,"ok","您已经报名了该考试");
              }
        }catch (Exception e){
            e.printStackTrace();
            return new Response<String>(200,"ok","查询订单异常");
        }
        //订单已经被判断为空，则可以创建订单
        order=new Order();
        order.setCourseId(orderInput.getCourseId());
        order.setStudentId(orderInput.getStudentId());
        order.setSchoolId(orderInput.getSchool());
        //但是创建订单的前提是要将数据库考试库存减1
        //插叙订单的条件是考试时间为input框中输入的月分和考试课程的id
        //目标1,在数据缓存中查找，找不到则查询数据库并更新缓存
        Enroll enroll =null;
        Integer idDB=0;
          try {
             enroll= enrollService.getRemain(orderInput.getCourseId(), orderInput.getMonth()-1);

          }catch (NullPointerException nullPointerException){
              nullPointerException.printStackTrace();
              return new Response<String>(200,"ok","未查询到相关的考试");
          }
          catch (Exception e){
              e.printStackTrace();
              return new Response<String>(200,"ok","失败");
          }
        //
             if (enroll.getRemain()<=0){
                 return new Response<String>(200,"ok","考试已经抢完");
             }else {
                 //更新数据库的剩余量
                synchronized (this){
                    enrollService.secKill(enroll.getId());
                    try {

                        orderMapper.createByOrder(order);
                        idDB=order.getId();

                    }catch (Exception e){
                        e.printStackTrace();
                        return new Response<String>(500,"gg","创建失败");
                    }
                }
             }
             //报名订单已经生成
        //接下来该生成准考证号了
        Ticket ticket = new Ticket();
        //
        ticket.setCourseId(orderInput.getCourseId());//设置课程id
        ticket.setStudentId(orderInput.getStudentId());//设置学生id
        //设置考试学校的id
        ticket.setSchoolId(orderInput.getSchool());
        ticket.setCourseId(enroll.getId());
        ticket.setId(idDB);
        //异步调用生成准考证方法
        try {
            orderService.asyncSetTicket(ticket);
        }catch (Exception e){

            e.printStackTrace();
            System.out.println("更新失败");
        }
        return new Response<String>(200,"ok","你已经报名成功");
    }




}
