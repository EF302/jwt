package com.lwl.jwt.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.lwl.jwt.entity.User;
import com.lwl.jwt.service.UserService;
import com.lwl.jwt.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @PostMapping("/user/login")
    //@RequestBody：用json参数请求形式；如果不加@RequestBody，就只能在请求路径中添加请求参数
    public Map<String, Object> login(@RequestBody User user) {
        log.info("用户名：{}", user.getName());
        log.info("password: {}", user.getPassword());

        Map<String, Object> map = new HashMap<>();
        try {
            User userDB = userService.login(user);

            Map<String, Object> payload = new HashMap<>();
            payload.put("id", userDB.getId());
            payload.put("name", userDB.getName());
            String token = JWTUtils.getToken(payload);

            map.put("state", true);
            map.put("msg", "登录成功");
            map.put("token", token);
            return map;

        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", false);
            map.put("msg", e.getMessage());
            map.put("token", "");
        }
        return map;
    }

    /**
     * 处理事务的接口
     *
     * @param request
     * @return
     */
    @PostMapping("/user/test")
    public Map<String, Object> test(HttpServletRequest request) {
        //获取请求中的token信息
        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtils.verify(token);
        String id = verify.getClaim("id").asString();
        String name = verify.getClaim("name").asString();
        log.info("用户id：{}", id);
        log.info("用户名: {}", name);

        //TODO 业务逻辑

        Map<String, Object> map = new HashMap<>();
        map.put("state", true);
        map.put("msg", "请求成功");
        return map;
    }

}
