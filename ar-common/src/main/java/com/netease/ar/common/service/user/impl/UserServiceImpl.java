package com.netease.ar.common.service.user.impl;

import com.netease.ar.common.dao.user.UserDao;
import com.netease.ar.common.http.exception.ApiError;
import com.netease.ar.common.http.exception.ApiException;
import com.netease.ar.common.model.user.UserModel;
import com.netease.ar.common.service.user.UserService;
import com.netease.ar.common.utils.CommonUtil;
import com.netease.ar.common.utils.RedisKeyUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService{
    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    private static final int VERIFY_CODE_EXPIRE_SECONDS = 60 * 5;

    @Resource private UserDao userDao;
    @Resource private JedisPool jedisPool;

    @Override
    public UserModel get(final String userId){
        return userDao.get(userId);
    }

    /**
     * 注册用户userId生成规则 : phone + timestamp + nonceStr
     * @param phone
     * @param verifyCode
     * @return
     */
    @Override
    public UserModel register(final String phone, final String verifyCode) throws ApiException{
        Jedis jedis = jedisPool.getResource();
        try {
            String verifyCodeKey =  RedisKeyUtil.getUserVerifyCodeKey(phone);
            if (jedis.exists(verifyCodeKey) && verifyCode.equals(jedis.get(verifyCodeKey))){ // 认证通过

                // 第一次注册登录，生成token并返回。
                long timestamp = System.currentTimeMillis();
                String userId = phone + timestamp  + CommonUtil.getRandomString(16, false);
                UserModel user = new UserModel();
                user.setUserId(DigestUtils.md5DigestAsHex(userId.getBytes("UTF-8")).toLowerCase());
                userDao.replaceUserVerifyCode(user);
            } else {
                throw new ApiException(ApiError.SMS_VERIFY_CODE_ILLEGAL);
            }

        } catch (Exception e){
            logger.error(e);
            throw new ApiException(ApiError.SERVER_ERROR);
        } finally {
            jedisPool.returnResource(jedis);
        }
        return userDao.get(phone);
    }

    @Override
    @Transactional
    public void replaceVerifyCode(final String phone, final String verifyCode) throws ApiException{
        Jedis jedis = jedisPool.getResource();
        try {
            String verifyCodeKey = RedisKeyUtil.getUserVerifyCodeKey(phone);
            jedis.setex(verifyCodeKey, VERIFY_CODE_EXPIRE_SECONDS, verifyCode);
        } catch (Exception e){
            logger.error("Update verify code failed.", e);
            throw new ApiException(ApiError.SERVER_ERROR);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }
}
