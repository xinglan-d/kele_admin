package com.kele.api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: duzongyue
 * @createDate: 2020/06/03 09:40
 * @version: 1.0
 */
public class JwtUtil {

    private final static String ENCRYPTION_PROTOCOL = "HS256";
    private Algorithm algorithm = Algorithm.HMAC256("wsaw");
    private final static String KEY = "id";

    public Map<String, Object> getJwtHeader() {
        Map<String, Object> map = new HashMap<>();
        map.put("alg", ENCRYPTION_PROTOCOL);
        map.put("typ", "JWT");
        return map;
    }

    public String createToken(String id) {
        return JWT.create().withHeader(getJwtHeader()).withClaim(KEY, id).sign(algorithm);
    }

    public static void main(String[] args) {
        String token = new JwtUtil().createToken("1111");
        System.out.println(token);
        new JwtUtil().verifyToken(token);

    }

    public String verifyToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        Map<String, Claim> claims = jwt.getClaims();
        Claim claim = claims.get(KEY);
        if (claim != null) {
            return claim.asString();
        }
        return null;
    }
}
