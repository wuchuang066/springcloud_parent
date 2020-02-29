package com.vecher.article.dao;

import com.vecher.article.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 * JpaSpecificationExecutor 实现sql的复杂查询 例如进行分页
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{


    @Modifying // 增删改需要添加的注解
    @Query(value = "update tb_article set state=1 where id = ?", nativeQuery = true)
    //nativeQuery = true 本地查询,所谓本地查询，就是使用原生的sql语句（根据数据库的不同，在sql的语法或结构方面可能有所区别）进行查询数据库的操作
    // 简单来说就是把？ 替换掉之后就可以在数据库执行
    public void updateState(String id);

    @Modifying
    @Query(value = "update tb_article set thumbup = thumbup+1 where id=?", nativeQuery = true)
    public void addThumbup(String id);
}
