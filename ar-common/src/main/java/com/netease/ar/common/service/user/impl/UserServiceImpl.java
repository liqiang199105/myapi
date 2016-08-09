package com.netease.ar.common.service.user.impl;

import com.google.common.base.Strings;
import com.netease.ar.common.dao.user.UserDao;
import com.netease.ar.common.http.exception.ApiError;
import com.netease.ar.common.http.exception.ApiException;
import com.netease.ar.common.model.user.UserModel;
import com.netease.ar.common.service.user.UserService;
import com.netease.ar.common.utils.CommonUtil;
import com.netease.ar.common.utils.DateTimeUtil;
import com.netease.ar.common.utils.RedisKeyUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService{
    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    private static final String DEFAULT_PAINTER_AVATAR = "http://bobo-public.nosdn.127.net/bobo_1470727094381_91088576.png";
    private static final String DEFAULT_PAINTER_NICK_PREFIX = "一直小潘塔#";

    @Resource private UserDao userDao;
    @Resource(name = "jedisPool") private JedisPool jedisPool;

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
    @Transactional
    public UserModel register(final String phone, final String verifyCode) throws ApiException{
        Jedis jedis = jedisPool.getResource();
        try {
            String verifyCodeKey =  RedisKeyUtil.getUserVerifyCodeKey(phone);
            if (jedis.exists(verifyCodeKey) && verifyCode.equals(jedis.get(verifyCodeKey))){ // 认证通过
                UserModel userModel = userDao.getUserByPhone(phone);
                if (userModel != null) { // 手机号已经注册
                    return userModel;
                }
                UserModel user = this.userModelBuilder(phone, verifyCode, jedis);
                return userDao.insertUserModel(user);
            } else {
                throw new ApiException(ApiError.SMS_VERIFY_CODE_ILLEGAL);
            }

        } catch (Exception e){
            logger.error(e);
            throw new ApiException(ApiError.SERVER_ERROR);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    @Override
    @Transactional
    public void replaceVerifyCode(final String phone, final String verifyCode) throws ApiException{
        Jedis jedis = jedisPool.getResource();
        try {
            String verifyCodeKey = RedisKeyUtil.getUserVerifyCodeKey(phone);
            jedis.setex(verifyCodeKey, DateTimeUtil.SECOND * 60, verifyCode);
        } catch (Exception e){
            logger.error("Update verify code failed.", e);
            throw new ApiException(ApiError.SERVER_ERROR);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    // =================================================================================================================
    // private functions
    // =================================================================================================================

    private UserModel userModelBuilder(final String phone, final String verifyCode, final Jedis jedis) throws UnsupportedEncodingException {
        UserModel user = new UserModel();
        String userId = phone + System.currentTimeMillis()  + CommonUtil.getRandomString(16, false);
        user.setPhone(phone);
        user.setVerifyCode(verifyCode);
        user.setLastModified(new Date());
        user.setAvatar(DEFAULT_PAINTER_AVATAR); // 默认头像
        user.setUserId(DigestUtils.md5DigestAsHex(userId.getBytes("UTF-8")).toLowerCase());

        String userTokenKey = RedisKeyUtil.getUserToken(user.getUserId());
        if (!jedis.exists(userTokenKey)){
            String token =  userId +  System.currentTimeMillis();
            user.setToken(DigestUtils.md5DigestAsHex(token.getBytes("UTF-8")).toLowerCase()); // MD5生成token
            jedis.setex(user.getUserId(), DateTimeUtil.HOUR * 24, user.getToken());
        } else {
            user.setToken(jedis.get(userTokenKey));
        }

        String nickIndexKey = RedisKeyUtil.getUserNickIndex();
        int index = Integer.valueOf(jedis.exists(nickIndexKey) ? jedis.get(nickIndexKey) : "1000");
        jedis.set(nickIndexKey, String.valueOf(++index));
        user.setNick(DEFAULT_PAINTER_NICK_PREFIX + index); // 默认昵称生成

        return user;

    }
}
