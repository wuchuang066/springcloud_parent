package com.vecher.friend.service;

import com.vecher.friend.client.UserClient;
import com.vecher.friend.dao.FriendDao;
import com.vecher.friend.dao.NoFriendDao;
import com.vecher.friend.pojo.Friend;
import com.vecher.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description :
 **/
@Service
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    @Autowired
    private UserClient userClient;

    @Transactional(rollbackFor = Exception.class)
    public int addFriend(String userid, String friendid) {

        // 判断如果用户已经添加了这个好友，则不进行任何操作，返回0
        if (friendDao.selectCount(userid, friendid) > 0) {
            return 0;
        }

        // 向喜欢表中添加记录
        Friend friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);

        if (friendDao.selectCount( friendid, userid) > 0) {
            friendDao.updateLike(userid, friendid, "1");
            friendDao.updateLike(friendid, userid, "1");
            // 增加自己的关注数
            userClient.incFanscount(userid, 1);
            // 增加对方的粉丝数
            userClient.incFanscount(friendid, 1);
        }

        return 1;
    }


    public void deleteFriend(String userid, String friendid) {
        friendDao.deleteFriend(userid, friendid);
        friendDao.updateLike(friendid, userid, "0");
        // 向不喜欢的表中添加记录
        addNoFriend(userid, friendid);
        // 减少对方的粉丝数
        userClient.incFanscount(friendid, -1);
        // 减少自己的关注数
        userClient.incFollowcount(userid, -1);

    }

    public void addNoFriend(String userid, String friendid) {
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }


}
