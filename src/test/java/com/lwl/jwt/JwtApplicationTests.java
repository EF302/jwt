package com.lwl.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class JwtApplicationTests {

    /**
     * 令牌token生成
     */
    @Test
    void contextLoads() {
        Map<String, Object> map = new HashMap<>();
//        map.put("typ", "JWT");
//        map.put("alg", "HS256");

        Calendar instance = Calendar.getInstance();
        //当前时间加上3600s
        instance.add(Calendar.SECOND, 3600);

        String token = JWT.create()
                .withHeader(map) //设置header，默认值就是typ:JWT，alg：HS256
                .withClaim("userId", 21)//payload
                .withClaim("username", "longwanli")//payload
                .withExpiresAt(instance.getTime())//指定令牌token的过期时间
                .sign(Algorithm.HMAC256("!Q@W#E$R")); //签名
        System.out.println(token);
    }



    /**
     * 验证令牌token，并从解析数据
     *      验证内容有：算法是否匹配、令牌是否过期、payload是否有效、签名是否一致等
     *      验证签名大致过程：base64反解码token中的header、payload，从新生成新的签名，
     *                       并与该token中的sign进行对比，看是否相等
     *
     */
    //常见异常：
    //SignatureVerificationException 签名不一致异常
    //TokenExpiredException 令牌过期异常
    //AlgorithmMismatchException 算法不匹配异常
    //InvalidClaimException 失效的payload异常
    @Test
    void test() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTA3OTkwMzgsInVzZXJJZCI6MjEsInVzZXJuYW1lIjoibG9uZ3dhbmxpIn0.yVc2Px_Ib73y_qS_mF2ruCnBCB-eTGwMDYXDiK0hwDc";
        //创建验证对象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("!Q@W#E$R")).build();
        //验证token,得到解析对象
        DecodedJWT decodedJWT = jwtVerifier.verify(token);

        //从解析对象中获取负载的信息
        System.out.println("header: " + decodedJWT.getHeader());//eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9
        System.out.println("payload: " + decodedJWT.getPayload());//eyJleHAiOjE2MTA3OTg4MDIsInVzZXJJZCI6MjEsInVzZXJuYW1lIjoibG9uZ3dhbmxpIn0
        System.out.println("signature: " + decodedJWT.getSignature());//Uk0Ovn3YNTzZ1_R6MmZWtc6zlfZHWABY8-nd9HVJ_1E
        System.out.println("token：" + decodedJWT.getToken());//eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTA3OTg4MDIsInVzZXJJZCI6MjEsInVzZXJuYW1lIjoibG9uZ3dhbmxpIn0.Uk0Ovn3YNTzZ1_R6MmZWtc6zlfZHWABY8-nd9HVJ_1E


        System.out.println("typ: " + decodedJWT.getType());//JWT
        System.out.println("alg: " + decodedJWT.getAlgorithm());//HS256
        System.out.println(decodedJWT.getAudience());//null
        System.out.println(decodedJWT.getContentType());//null
        System.out.println(decodedJWT.toString());//com.auth0.jwt.JWTDecoder@56399b9e

        System.out.println("用户Id：" + decodedJWT.getClaim("userId").asInt());//用户Id：21
        System.out.println("用户id: " + decodedJWT.getClaims().get("userId").asInt());//用户Id：21
        System.out.println("用户名：" + decodedJWT.getClaim("username").asString());//longwanli
        System.out.println("过期时间：" + decodedJWT.getExpiresAt());//Sat Jan 16 20:06:42 CST 2021

        System.out.println("获取payload中声明的key集合：" + decodedJWT.getClaims().keySet());//获取：[exp, userId, username]
        for (String key : decodedJWT.getClaims().keySet()) {
            System.out.println(key + ":" + decodedJWT.getClaims().get(key).toString());
        }
    }
}
