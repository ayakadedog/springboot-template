package com.restaurant.controller;

import com.restaurant.constant.JwtClaimsConstant;
import com.restaurant.context.BaseContext;
import com.restaurant.dto.UserLoginDTO;
import com.restaurant.entity.User;
import com.restaurant.exception.BaseException;
import com.restaurant.properties.JwtProperties;
import com.restaurant.result.Result;
import com.restaurant.service.UserService;
import com.restaurant.utils.JwtUtil;
import com.restaurant.vo.UserLoginVO;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/dev-api/api/user")
public class UserController {

    @Resource
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("员工登录：{}", userLoginDTO);

//        User user = userService.login(userLoginDTO);

        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setUsername("johndoe");
        user.setPassword("password123");
        user.setPhone("123-456-7890");
        user.setPreferences("1"); // 1表示男性
        user.setNumber("123456789012345678");
        user.setAvatar("https://example.com/avatar.jpg");
        user.setDescription("This is a sample user.");
        user.setStatus(1); // 1表示用户起用
        user.setCreateTime(LocalDateTime.now());

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .name(user.getName())
                .avatar(user.getAvatar())
                .token(token)
                .build();

        return Result.success(userLoginVO);
    }


    /**
     * 获取用户信息
     *
     * @param token
     * @return
     */
    @GetMapping("/info")
    public Result<UserLoginVO> getUserInfo(String token) {
        Long empId;
        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
             empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
            log.info("当前员工id：", empId);
            BaseContext.setCurrentId(empId);
            //3、通过，放行
        } catch (Exception ex) {

            throw new BaseException("身份验证未通过");
        }

        //User user = userService.getById(empId);

        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setUsername("johndoe");
        user.setPassword("password123");
        user.setPhone("123-456-7890");
        user.setPreferences("1"); // 1表示男性
        user.setNumber("123456789012345678");
        user.setAvatar("https://th.bing.com/th?id=OIP.sAiCiGYrRluulMkTFBaFIQHaHU&w=251&h=248&c=8&rs=1&qlt=90&o=6&dpr=1.5&pid=3.1&rm=2");
        user.setDescription("This is a sample user.");
        user.setStatus(1); // 1表示用户起用
        user.setCreateTime(LocalDateTime.now());

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .userName(user.getUsername())
                .name(user.getName())
                .avatar(user.getAvatar())
                .build();

        return Result.success(userLoginVO);

    }


    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }


}
