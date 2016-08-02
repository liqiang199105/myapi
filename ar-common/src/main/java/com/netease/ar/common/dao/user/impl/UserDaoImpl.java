package com.netease.ar.common.dao.user.impl;


import com.netease.ar.common.dao.user.UserDao;
import com.netease.ar.common.model.user.UserModel;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;


@Repository
public class UserDaoImpl implements UserDao {
    private static Logger logger = Logger.getLogger(UserDaoImpl.class);

    private static final String namespace = "user.";

    @Resource
    private SqlSession sqlSession;

    @Override
    public UserModel get(final long userId){
        return (UserModel) sqlSession.selectOne(namespace + "get", userId);
    }
}
