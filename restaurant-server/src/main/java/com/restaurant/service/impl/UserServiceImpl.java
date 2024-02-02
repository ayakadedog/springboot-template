package com.restaurant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restaurant.constant.MessageConstant;
import com.restaurant.constant.StatusConstant;
import com.restaurant.dto.UserLoginDTO;
import com.restaurant.entity.User;
import com.restaurant.exception.AccountLockedException;
import com.restaurant.exception.PasswordErrorException;
import com.restaurant.mapper.UserMapper;
import com.restaurant.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {


    @Resource
    private UserMapper userMapper;


        /**
         * 员工登录
         *
         * @param userLoginDTO
         * @return
         */
        @Override
        public User login(UserLoginDTO userLoginDTO) {
            String username = userLoginDTO.getUsername();
            String password = userLoginDTO.getPassword();

            //1、根据用户名查询数据库中的数据
            User user = userMapper.getByUsername(username);

            //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
            if (user == null) {
                //账号不存在
                throw new PasswordErrorException(MessageConstant.ACCOUNT_NOT_FOUND);
            }

            //密码比对
            // TODO 后期需要进行md5加密，然后再进行比对
            if (!password.equals(user.getPassword())) {
                //密码错误
                throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
            }

            if (user.getStatus() == StatusConstant.DISABLE) {
                //账号被锁定
                throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
            }

            //3、返回实体对象

        return user;
    }


}
