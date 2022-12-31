package com.hspedu.furn.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hspedu.furn.bean.Furn;
import com.hspedu.furn.bean.Msg;
import com.hspedu.furn.service.FurnService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: guorui fu
 * @versiion: 1.0
 */
@Controller
public class FurnController {

    //配置FurnService
    @Resource
    private FurnService furnService;
    /**
     * @ResponseBody 按照http协议中规定格式返回数据 往往用来json格式
     * @RequestBody 将提交来的json数据，封装成JavaBean
     * 响应客户端的添加请求
     * @param furn
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public Msg save(@Validated @RequestBody Furn furn, Errors errors){
        Map<String, Object> map = new HashMap<>();
        List<FieldError> fieldErrors = errors.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            map.put(fieldError.getField(),fieldError.getDefaultMessage());
        }

        if (map.isEmpty()){//后端校验 没有错误
            furnService.save(furn);

            return Msg.success();
        }else{
            //把校验的错误信息封装到Msg对象，并返回
            return Msg.fail().add("errorMsg",map);
        }

    }

    @RequestMapping("/furns")
    @ResponseBody
    public Msg listFurns(){
        List<Furn> furnList = furnService.findAll();
        Msg msg = Msg.success();
        //家具信息封装到msg对象
        msg.add("furnList",furnList);
        return msg;
    }

    @PutMapping("/update")
    @ResponseBody
    public Msg update(@RequestBody Furn furn){
        furnService.update(furn);
        return Msg.success();
    }

    @DeleteMapping("/del/{id}")
    @ResponseBody
    public Msg del(@PathVariable Integer id){
        furnService.del(id);
        return Msg.success();
    }

    @RequestMapping("/furn/{id}")
    @ResponseBody
    public Msg findById(@PathVariable Integer id){
        Furn furn = furnService.findById(id);
        Msg msg = Msg.success();
        msg.add("furn",furn);
        return msg;
    }

    /**
     * 分页请求接口
     * @param pageNum 显示第几页 默认为1
     * @param pageSize 每页显示几条记录 默认为5
     * @return
     */
    @ResponseBody
    @RequestMapping("/furnsByPage")
    public Msg listFurnsByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "5") Integer pageSize){
        //设置分页参数
        //完成查询，底层会进行物理分页 而不是逻辑分页
        //已经引入分页插件，方法不一样。会根据参数来计算  发出语句时会带limit
        PageHelper.startPage(pageNum,pageSize);

        List<Furn> furnList = furnService.findAll();

        //将分页查询的结果封装到PageInfo
        //pageInfo 包含了分页的各种信息，如当前页，记录总数等
        PageInfo pageInfo = new PageInfo(furnList, pageSize);

        //将pageInfo封装到Msg对象，返回
        return Msg.success().add("pageInfo",pageInfo);
    }

    /**
     * 根据家居名 进行分页查询-条件
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("/furnsByConditionPage")
    public Msg listFurnsByConditionPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "5") Integer pageSize,
                                @RequestParam(defaultValue = "") String search){
        PageHelper.startPage(pageNum,pageSize);

        List<Furn> furnList = furnService.findByCondition(search);

        PageInfo pageInfo = new PageInfo(furnList, pageSize);

        //将pageInfo封装到Msg对象，返回
        return Msg.success().add("pageInfo",pageInfo);
    }
}
