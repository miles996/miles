package com.lsf.elasticsearch.dao.impl;

import com.lsf.elasticsearch.dao.BaseDao;
import com.lsf.elasticsearch.dao.UserDao;
import com.lsf.elasticsearch.model.User;
import org.springframework.stereotype.Repository;

public class UserDaoImpl{
    public User getById() {
        return new User();
    }
}
