package com.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.restaurant.dto.UserLoginDTO;
import com.restaurant.entity.User;

public interface UserService extends IService<User> {

    /**
     * 微信用户登录
     * @param userLoginDTO
     * @return
     */
    User login(UserLoginDTO userLoginDTO);

}
