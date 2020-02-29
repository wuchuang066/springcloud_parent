package com.vecher.article.controller;

import com.github.pagehelper.PageInfo;
import com.vecher.article.pojo.Article;
import com.vecher.article.service.ArticleService;
import com.vecher.entity.PageResult;
import com.vecher.entity.Result;
import com.vecher.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    /**
     * 文章审核 将文章的state 字段修改为1  通过审核
     *
     * @param articleId 文章id
     * @return
     */
    @RequestMapping(value = "/examine/{articleId}", method = RequestMethod.PUT)
    public Result examine(@PathVariable String articleId) {
        articleService.updateState(articleId);
        return new Result(true, StatusCode.OK.getCode(), "审核成功");
    }

    /**
     * 文章点赞
     * 将thumbup 字段＋1 无其他判断
     *
     * @param articleId
     * @return
     */
    @RequestMapping(value = "/thumbup/{articleId}", method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String articleId) {
        articleService.addThumbup(articleId);
        return new Result(true, StatusCode.OK.getCode(), "点赞成功");
    }

    /**
     * 查询全部文章数据信息
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK.getCode(), "查询成功", articleService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK.getCode(), "查询成功", articleService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     * {
     * "id": "string",
     * "columnid": "string",
     * "userid": "string",
     * "title": "string",
     * "content": "string",
     * "image": "string",
     * "createtime": "2020-02-22T12:11:25.395Z",
     * "updatetime": "2020-02-22T12:11:25.395Z",
     * "ispublic": "string",
     * "istop": "string",
     * "visits": 0,
     * "thumbup": 0,
     * "comment": 0,
     * "state": "string",
     * "channelid": "string",
     * "url": "string",
     * "type": "string"
     * }
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Article article, @PathVariable int page, @PathVariable int size) {
        PageInfo<Article> pageInfo = articleService.findSearch(article, page, size);
        return new Result(true, StatusCode.OK.getCode(), "查询成功", new PageResult<Article>(pageInfo.getTotal(), pageInfo.getList()));
    }

    /**
     * 根据条件查询
     *
     * @param article
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Article article) {
        return new Result(true, StatusCode.OK.getCode(), "查询成功", articleService.findSearch(article));
    }

    /**
     * 增加文章
     *
     * @param article {
     *                "columnid": "3",
     *                "userid": "3",
     *                "title": "3",
     *                "content": "3",
     *                "image": "3",
     *                "createtime": "2020-02-22T12:17:27.033Z",
     *                "updatetime": "2020-02-22T12:17:27.033Z",
     *                "ispublic": "3",
     *                "istop": "3",
     *                "visits": 0,
     *                "thumbup": 0,
     *                "comment": 0,
     *                "state": "3",
     *                "channelid": "3",
     *                "url": "3",
     *                "type": "3"
     *                }
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Article article) {
        Integer result = articleService.add(article);
        if (result > 0)
            return new Result(true, StatusCode.OK.getCode(), "增加成功");
        return new Result(true, StatusCode.ERROR.getCode(), "增加失败");
    }

    /**
     * 修改文章
     *
     * @param article {
     *                "columnid": "4",
     *                "userid": "4",
     *                "title": "4",
     *                "content": "4",
     *                "image": "4",
     *                "createtime": "2020-02-22T12:19:15.736Z",
     *                "updatetime": "2020-02-22T12:19:15.736Z",
     *                "ispublic": "4",
     *                "istop": "4",
     *                "visits": 0,
     *                "thumbup": 0,
     *                "comment": 0,
     *                "state": "4",
     *                "channelid": "4",
     *                "url": "4",
     *                "type": "4"
     *                }
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Article article, @PathVariable String id) {
        article.setId(id);
        int result = articleService.update(article);
        if (result > 0)
            return new Result(true, StatusCode.OK.getCode(), "修改成功");
        return new Result(true, StatusCode.ERROR.getCode(), "修改失败");
    }

    /**
     * 删除文章
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        Integer result = articleService.deleteById(id);
        if (result > 0)
            return new Result(true, StatusCode.OK.getCode(), "删除成功");
        return new Result(true, StatusCode.ERROR.getCode(), "删除失败");
    }

}
