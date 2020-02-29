package com.vecher.friend.dao;

import com.vecher.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description : 不喜欢列表数据访问层
 **/
public interface NoFriendDao extends JpaRepository<NoFriend, String> {
}
