package com.vecher.base.controller;

import com.vecher.base.pojo.Label;
import com.vecher.base.service.LabelService;
import com.vecher.entity.PageResult;
import com.vecher.entity.Result;
import com.vecher.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController {

    private LabelService labelService;

    @Autowired
    public void setLabelService(LabelService labelService) {
        this.labelService = labelService;
    }

    /**
     * 查询所有label信息
     * @return
     */
    @GetMapping
    public  Result findAll(){
        return new Result(true, StatusCode.OK.getCode(),"查询成功",labelService.findAll());
    }

    /**
     * 根据id查询
     * @param labelId
     * @return
     */
    @RequestMapping(value="/{labelId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String labelId){
        return new Result(true, StatusCode.OK.getCode(),"查询成功", labelService.findById(labelId));
    }

    /**
     * 添加信息
     * @param label
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Label label){
        labelService.save(label);
        return new Result(true, StatusCode.OK.getCode(),"添加成功");
    }

    /**
     * 更新信息
     * @param labelId
     * @param label
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT,value="/{labelId}")
    public Result update(@PathVariable String labelId,@RequestBody Label label){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true, StatusCode.OK.getCode(),"修改成功");
    }

    /**
     * 删除信息
     * @param labelId
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE,value="/{labelId}")
    public Result deleteById(@PathVariable String labelId){
        labelService.deleteById(labelId);
        return new Result(true, StatusCode.OK.getCode(),"删除成功");
    }
    /**
     * 条件查询
     * @param label
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value="/search")
    public Result findSearch(@RequestBody Label label){
         List<Label> list = labelService.findSearch(label);
        return new Result(true, StatusCode.OK.getCode(),"条件查询成功",list);
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @param label
     * @return
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result pageQuery(@PathVariable Integer page,
                            @PathVariable Integer size,
                            @RequestBody Label label) {

        Page<Label> pageData = labelService.pageQuery(label, page, size);
        return new Result(true, StatusCode.OK.getCode(), "查询成功",
                new PageResult<Label>(pageData.getTotalElements(), pageData.getContent()));
    }

}
