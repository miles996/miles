package com.lsf.elasticsearch.web;

import com.lsf.elasticsearch.model.User;
import com.lsf.elasticsearch.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("测试swagger")
@RequestMapping("test")
public class testController{

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("测试接口")
    @GetMapping("")
    public User test() {
        //ValueOperations<String,Object> vo = redisTemplate.opsForValue();

        return userService.getById();
    }
}
