package com.selfstudyboot.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.selfstudyboot.dao.dto.PageInput;
import com.selfstudyboot.entity.Plot;
import com.selfstudyboot.mapper.PlotMapper;
import com.selfstudyboot.service.PlotService;
import com.selfstudyboot.utils.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PlotController {
    @Resource
    private PlotMapper plotMapper;
    @Resource
    private PlotService plotService;
    @PostMapping("/admin/plot")
    public Response<IPage<Plot>> showPlot(@RequestBody PageInput input){
        final QueryWrapper<Plot> wrapper = new QueryWrapper<>();
        if (!input.getSearch().isEmpty()){
            wrapper.like("plot_name",input.getSearch());
            System.out.println(input.getSearch());
        }


        final Page<Plot> page = plotMapper.selectPage(new Page<Plot>(input.getPageNum(),input.getPageSize()), wrapper);
        page.getRecords().forEach(System.out::println);
        return new Response<>(200, "ok", page);
    }


    @PostMapping("/admin/plot/disable")
    public Response disable(@RequestBody Plot plot){
        try {
            plotMapper.disabled(plot.getId());
            return new Response<String>(200,"ok","ok");
        }catch (Exception e){
            return new Response<Exception>(500,"err",e);
        }
    }
    @PostMapping("/admin/plot/able")
    public Response able(@RequestBody Plot plot){
        try {
            plotMapper.abled(plot.getId());
            return new Response<String>(200,"ok","ok");
        }catch (Exception e){
            return new Response<Exception>(500,"err",e);
        }
    }
}
