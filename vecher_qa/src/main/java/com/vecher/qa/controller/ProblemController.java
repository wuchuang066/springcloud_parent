package com.vecher.qa.controller;
import com.vecher.entity.PageResult;
import com.vecher.entity.Result;
import com.vecher.entity.StatusCode;
import com.vecher.qa.client.LabelClient;
import com.vecher.qa.pojo.Problem;
import com.vecher.qa.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

	@Autowired
	private ProblemService problemService;

	@Autowired
	private HttpServletRequest httpServletRequest;

	@Autowired
	private LabelClient labelClient;

	@RequestMapping(value = "/newlist/{labelid}/{page}/{size}", method = RequestMethod.GET)
	public Result newlist(@PathVariable String labelid,
						  @PathVariable int page,
						  @PathVariable int size) {
		Page<Problem> pageData = problemService.newlist(labelid, page, size);
		return new Result(true, StatusCode.OK.getCode(), "查询成功",
				new PageResult<Problem>(pageData.getTotalElements(), pageData.getContent()));

	}

	@RequestMapping(value = "/hotlist/{labelid}/{page}/{size}", method = RequestMethod.GET)
	public Result hotlist(@PathVariable String labelid,
						  @PathVariable int page,
						  @PathVariable int size) {
		Page<Problem> pageData = problemService.hotlist(labelid, page, size);
		return new Result(true, StatusCode.OK.getCode(), "查询成功",
				new PageResult<Problem>(pageData.getTotalElements(), pageData.getContent()));

	}

	@RequestMapping(value = "/waitlist/{labelid}/{page}/{size}", method = RequestMethod.GET)
	public Result waitlist(@PathVariable String labelid,
						  @PathVariable int page,
						  @PathVariable int size) {
		Page<Problem> pageData = problemService.waitlist(labelid, page, size);
		return new Result(true, StatusCode.OK.getCode(), "查询成功",
				new PageResult<Problem>(pageData.getTotalElements(), pageData.getContent()));

	}

	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true, StatusCode.OK.getCode(),"查询成功",problemService.findAll());
	}

	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Problem> pageList = problemService.findSearch(searchMap, page, size);
		return  new Result(true, StatusCode.OK.getCode(),"查询成功",  new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true, StatusCode.OK.getCode(),"查询成功",problemService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param problem
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Problem problem  ){
		String token = (String) httpServletRequest.getAttribute("user_claims");
		if(token == null || "".equals(token)){
			return new Result(false, StatusCode.ACCESS_ERROR.getCode(),"权限不足");
		}
		problemService.add(problem);
		return new Result(true, StatusCode.OK.getCode(),"增加成功");
	}
	
	/**
	 * 修改
	 * @param problem
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Problem problem, @PathVariable String id ){
		problem.setId(id);
		problemService.update(problem);		
		return new Result(true, StatusCode.OK.getCode(),"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		problemService.deleteById(id);
		return new Result(true, StatusCode.OK.getCode(),"删除成功");
	}
	/**
	 * 根据ID查询
	 * @param labelid ID 远程调用 查找base里面的标签信息
	 * @return
	 */
	@RequestMapping(value="/label/{labelid}",method= RequestMethod.GET)
	public Result findById(@PathVariable String labelid){
		return labelClient.findById(labelid);
	}
}
