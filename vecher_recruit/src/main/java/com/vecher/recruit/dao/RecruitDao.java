package com.vecher.recruit.dao;

import com.vecher.recruit.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{
    // where state = "1" order by createtime
	public List<Recruit> findTop6ByStateOrderByCreatetimeDesc(String state);

	// where state != ? order by createtime
	public List<Recruit> findTop6ByStateNotOrderByCreatetimeDesc(String state);
}
