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
 * JPA的使用：
 *   1.引入依赖
 *     <dependency>
 *       <groupId>org.springframework.boot</groupId>
 *       <artifactId>spring-boot-starter-data-jpa</artifactId>
 *     </dependency>
 *   2. 创建接口 并实现JpaRepository<T,T的主键的类型>,JpaSpecificationExecutor<T>
 *   3. 直接就可以调用接口里面的一些方法，如果没有满足条件的可以用方法名字如：findByUserNameLike 根据userName 模糊查询
 *   4. 如果想自己写sql 可以通过注解@Query(value="sql语句 以？形式去写，", nativeQuery = true)，
 *      只有一个参数不需要写顺序，多个参数在？后边填写顺序
 *   5. 增删改查需要添加注解 @Modifying 实例如下：
 *     @Modifying
 *     @Query("update User u set u.fanscount = u.fanscount+?2 where u.id=?1")
 *     public void incFanscount(String userid, int x);
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article> {


    @Modifying // 增删改需要添加的注解
    @Query(value = "update tb_article set state=1 where id = ?", nativeQuery = true)
    //nativeQuery = true 本地查询,所谓本地查询，就是使用原生的sql语句（根据数据库的不同，在sql的语法或结构方面可能有所区别）进行查询数据库的操作
    // 简单来说就是把？ 替换掉之后就可以在数据库执行
    public void updateState(String id);

    @Modifying
    @Query(value = "update tb_article set thumbup = thumbup+1 where id=?", nativeQuery = true)
    public void addThumbup(String id);
}
