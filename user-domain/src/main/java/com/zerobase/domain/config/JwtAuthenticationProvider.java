package com.zerobase.domain.config;

import com.zerobase.domain.common.UserType;
import com.zerobase.domain.common.UserVo;
import com.zerobase.domain.util.Aes256Util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Objects;

// 토큰 공급
public class JwtAuthenticationProvider {

    private final String secretKey = "secretKey";

    private  final long tokenValidTime = 1000L * 60 * 60 *24;

    // 토큰 생성
    public String createToken(String userPk, Long id , UserType userType){
        Claims claims = Jwts.claims().setSubject(Aes256Util.encrypt(userPk)).setId(Aes256Util.encrypt(id.toString()));
        claims.put("roles",userType);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+tokenValidTime))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }

    // 토큰 유효확인
    public boolean validateToken(String jwtToken){
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            return false;
        }
    }

    // 유저에게서 가져올수 있는 정보 가져오기
    public UserVo getUserVo(String token){
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return new UserVo(Long.valueOf(Objects.requireNonNull(Aes256Util.decrypt(claims.getId()))),Aes256Util.decrypt(claims.getSubject()));
    }

}
