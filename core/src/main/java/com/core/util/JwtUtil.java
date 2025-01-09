package com.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtil {
    @Value("${token.jwt.secretKey}")
    private  String secretKey;
    public  String getJwtToken(String userId,String version,String name,String role){
        return JWT.create().withClaim("userId",userId).withClaim("timeStamp",System.currentTimeMillis()).withClaim("version",version)
                .withClaim("name",name).withClaim("role",role).sign(Algorithm.HMAC256(secretKey));
    }
    public Map<String,String> parseJwtToken(String token){
        var map = new HashMap<String,String>();
        var result = JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
        var userId = result.getClaim("userId");
        var timeStamp = result.getClaim("timeStamp");
        var version = result.getClaim("version");
        var name = result.getClaim("name");
        var role = result.getClaim("role");
        map.put("userId",userId.asString());
        map.put("timeStamp",timeStamp.asLong().toString());
        map.put("version",version.asString());
        map.put("name",name.asString());
        map.put("role",role.asString());
        return  map;

    }
}
