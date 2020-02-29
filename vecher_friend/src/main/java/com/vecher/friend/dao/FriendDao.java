package com.vecher.friend.dao;

import com.vecher.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

/**
 * @description : 交友数据访问层
 **/
@Component
public interface FriendDao extends JpaRepository<Friend, String> {

    /**
     * 根据用户ID与被关注用户ID查询记录个数
     *
     * @param userId
     * @param friendid
     * @return
     */
    @Query(value = "select count(f) from Friend f where f.userid=?1 and f.friendid=?2", nativeQuery = true)
    public int selectCount(String userId, String friendid);

    /**
     * 更新为互相喜欢
     *
     * @param userid
     * @param friendid
     * @param islike
     */
    @Modifying
    @Query(value = "update Friend  f set f.islike=?3 where f.userid=?1 and f.friendid=?2", nativeQuery = true)
    public void updateLike(String userid, String friendid, String islike);

    /**
     * 删除喜欢
     *
     * @param userid
     * @param friendid
     */
    @Modifying
    @Query(value = "delete from friend f where f.userid=?1 and f.friendid=?2", nativeQuery = true)
    public void deleteFriend(String userid, String friendid);
}
