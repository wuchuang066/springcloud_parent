package com.vecher.spit.dao;

import com.vecher.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @description :
 **/
public interface SpitDao extends MongoRepository<Spit, String> {

    public Page<Spit> findByParentId(String parentid, Pageable pageable);

}
