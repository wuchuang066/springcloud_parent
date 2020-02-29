package com.vecher.article.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vecher.article.dao.ArticleDao;
import com.vecher.article.dao.ArticleMapper;
import com.vecher.article.pojo.Article;
import com.vecher.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
@Transactional
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;


    public void updateState(String id) {
        articleDao.updateState(id);
    }

    public void addThumbup(String id) {
        articleDao.addThumbup(id);
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Article> findAll() {
        return articleMapper.selectAll();
    }

    /**
     * 条件查询+分页
     *
     * @param article
     * @param page
     * @param size
     * @return
     */
    public PageInfo<Article> findSearch(Article article, int page, int size) {
        PageHelper.startPage(page, size);
        Example example = new Example(Article.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", article.getId());
        criteria.andEqualTo("columnid", article.getColumnid());
        criteria.andEqualTo("userid", article.getUserid());
        if(!StringUtil.isEmpty(article.getTitle()))
            criteria.andLike("title", "%" + article.getTitle() + "%");
        if(!StringUtil.isEmpty(article.getContent()))
            criteria.andLike("content", "%" + article.getContent() + "%");
        if(!StringUtil.isEmpty(article.getImage()))
            criteria.andLike("image", "%" + article.getImage() + "%");
        criteria.andEqualTo("ispublic", article.getIspublic());
        criteria.andEqualTo("istop", article.getIstop());
        criteria.andEqualTo("visits", article.getVisits());
        criteria.andEqualTo("thumbup", article.getThumbup());
        criteria.andEqualTo("comment", article.getComment());
        criteria.andEqualTo("state", article.getState());
        criteria.andEqualTo("channelid", article.getChannelid());
        criteria.andEqualTo("url", article.getUrl());
        criteria.andEqualTo("type", article.getType());
        List<Article> articles = this.articleMapper.selectByExample(example);
        return new PageInfo<>(articles);
    }


    /**
     * 条件查询
     *
     * @param article
     * @return
     */
    public List<Article> findSearch(Article article) {
        Example example = new Example(Article.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", article.getId());
        criteria.andEqualTo("columnid", article.getColumnid());
        criteria.andEqualTo("userid", article.getUserid());
        if(!StringUtil.isEmpty(article.getTitle()))
        criteria.andLike("title", "%" + article.getTitle() + "%");
        if(!StringUtil.isEmpty(article.getContent()))
        criteria.andLike("content", "%" + article.getContent() + "%");
        if(!StringUtil.isEmpty(article.getImage()))
        criteria.andLike("image", "%" + article.getImage() + "%");
        criteria.andEqualTo("ispublic", article.getIspublic());
        criteria.andEqualTo("istop", article.getIstop());
        criteria.andEqualTo("visits", article.getVisits());
        criteria.andEqualTo("thumbup", article.getThumbup());
        criteria.andEqualTo("comment", article.getComment());
        criteria.andEqualTo("state", article.getState());
        criteria.andEqualTo("channelid", article.getChannelid());
        criteria.andEqualTo("url", article.getUrl());
        criteria.andEqualTo("type", article.getType());
        return this.articleMapper.selectByExample(example);
    }

    /**
     * 根据ID查询实体
     * 使用redis缓存
     *
     * @param id
     * @return
     */
    public Article findById(String id) {
        Article article = (Article) redisTemplate.opsForValue().get("article_" + id);
        if (article == null) {
            article = articleMapper.selectByPrimaryKey(id);
            // 将当前文章信息存进redis 并设置十分钟过期时间
            redisTemplate.opsForValue().set("article_" + id, article, 60 * 10, TimeUnit.SECONDS);
        }
        return article;
    }

    /**
     * 增加
     *
     * @param article
     */
    public Integer add(Article article) {
        article.setId(idWorker.nextId() + "");
       return articleMapper.insert(article);
    }

    /**
     * 修改
     *
     * @param article
     */
    public Integer update(Article article) {
        // 从缓存中删除
        redisTemplate.delete("article_" + article.getId());
        return articleMapper.updateByPrimaryKey(article);
    }

    /**
     * 删除
     *
     * @param id
     */
    public Integer deleteById(String id) {
        // 从缓存中删除
        redisTemplate.delete("article_" + id);
        return articleMapper.deleteByPrimaryKey(id);

    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Article> createSpecification(Map searchMap) {
        // 匿名内部类
        return new Specification<Article>() {

            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                // 专栏ID
                if (searchMap.get("columnid") != null && !"".equals(searchMap.get("columnid"))) {
                    predicateList.add(cb.like(root.get("columnid").as(String.class), "%" + (String) searchMap.get("columnid") + "%"));
                }
                // 用户ID
                if (searchMap.get("userid") != null && !"".equals(searchMap.get("userid"))) {
                    predicateList.add(cb.like(root.get("userid").as(String.class), "%" + (String) searchMap.get("userid") + "%"));
                }
                // 标题
                if (searchMap.get("title") != null && !"".equals(searchMap.get("title"))) {
                    predicateList.add(cb.like(root.get("title").as(String.class), "%" + (String) searchMap.get("title") + "%"));
                }
                // 文章正文
                if (searchMap.get("content") != null && !"".equals(searchMap.get("content"))) {
                    predicateList.add(cb.like(root.get("content").as(String.class), "%" + (String) searchMap.get("content") + "%"));
                }
                // 文章封面
                if (searchMap.get("image") != null && !"".equals(searchMap.get("image"))) {
                    predicateList.add(cb.like(root.get("image").as(String.class), "%" + (String) searchMap.get("image") + "%"));
                }
                // 是否公开
                if (searchMap.get("ispublic") != null && !"".equals(searchMap.get("ispublic"))) {
                    predicateList.add(cb.like(root.get("ispublic").as(String.class), "%" + (String) searchMap.get("ispublic") + "%"));
                }
                // 是否置顶
                if (searchMap.get("istop") != null && !"".equals(searchMap.get("istop"))) {
                    predicateList.add(cb.like(root.get("istop").as(String.class), "%" + (String) searchMap.get("istop") + "%"));
                }
                // 审核状态
                if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                    predicateList.add(cb.like(root.get("state").as(String.class), "%" + (String) searchMap.get("state") + "%"));
                }
                // 所属频道
                if (searchMap.get("channelid") != null && !"".equals(searchMap.get("channelid"))) {
                    predicateList.add(cb.like(root.get("channelid").as(String.class), "%" + (String) searchMap.get("channelid") + "%"));
                }
                // URL
                if (searchMap.get("url") != null && !"".equals(searchMap.get("url"))) {
                    predicateList.add(cb.like(root.get("url").as(String.class), "%" + (String) searchMap.get("url") + "%"));
                }
                // 类型
                if (searchMap.get("type") != null && !"".equals(searchMap.get("type"))) {
                    predicateList.add(cb.like(root.get("type").as(String.class), "%" + (String) searchMap.get("type") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

}
