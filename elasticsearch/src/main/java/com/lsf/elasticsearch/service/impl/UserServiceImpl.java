package com.lsf.elasticsearch.service.impl;

import com.lsf.elasticsearch.dao.UserDao;
import com.lsf.elasticsearch.model.User;
import com.lsf.elasticsearch.model.phone;
import com.lsf.elasticsearch.service.UserService;
import com.lsf.elasticsearch.utils.ElasticsearchUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Autowired
    private ElasticsearchUtil elasticsearchUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public User getById() {
        User user = userDao.getById();
//        ValueOperations valueOperations = redisTemplate.opsForValue();
//        valueOperations.set(String.valueOf(user.getId()), user);
//
//        ListOperations listOperations = redisTemplate.opsForList();
//        listOperations.leftPush("aaa", "test");
//        listOperations.leftPush("bbb", "test");
//        listOperations.leftPush("aaa", "test1");
        return user;
    }
}
