package com.lsf.elasticsearch.dao;

import com.lsf.elasticsearch.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    User getById();
}
