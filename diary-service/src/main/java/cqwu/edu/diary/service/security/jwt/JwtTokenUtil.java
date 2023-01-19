package cqwu.edu.diary.service.security.jwt;

import cqwu.edu.diary.service.config.JwtConfig;
import cqwu.edu.diary.service.security.entity.CustomerUserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtTokenUtil {
    private JwtTokenUtil() {

    }

    /**
     * 生成token
     *
     * @param customerUserEntity
     * @return {@link String}
     */
    public static String createAccessToken(CustomerUserEntity customerUserEntity) {
        //  登录认证成功生成JWT
        return Jwts.builder()
                //  放入账号和用户ID
                .setId(String.valueOf(customerUserEntity.getId()))
                //  主题
                .setSubject(customerUserEntity.getUsername())
                //  签发时间
                .setIssuedAt(new Date())
                //  签发者
                .setIssuer("jianghaoran")
                //  自定义属性：存入用户拥有的权限
                .claim("authorities", customerUserEntity.getAuthorities())
                //  失效时间
                .setExpiration(null)
                //  签名算法和秘钥
                .signWith(SignatureAlgorithm.HS512, JwtConfig.secret)
                .compact();
    }
}
