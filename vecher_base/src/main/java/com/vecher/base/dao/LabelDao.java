package com.vecher.base.dao;

import com.vecher.base.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Classname BaseDao
 * @Description TODO
 * @Date 2020/2/15 18:45
 * @Created by 74541
 */
public interface LabelDao extends JpaRepository<Label,String>, JpaSpecificationExecutor<Label> {
}
