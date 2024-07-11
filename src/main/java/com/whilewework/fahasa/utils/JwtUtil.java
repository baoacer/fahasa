package com.whilewework.fahasa.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtil {

    public static final String SECRET_KEY = "fd6PfrqDedpXnz6pJ/sJSNhfkJBczmI5aRA2oDR8R1l3z2ic236z/XvKu81s6nFc+LUTu0ZXXOuyWYo7hWcl7A==";

    // ================= tạo token ==================

    /*
     * Nhận vào username của người dùng và tạo một token JWT.
     */
    public String generateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(username, claims);
    }

    private String createToken(String username, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 phút
                .signWith(getSignKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    // =============== Giải mã token =================

    /*
     * ValidateToken -> extractUsername lấy username từ token -> so sánh username (token) và username (userDetails)
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /*
     * Method chung để trích xuất bất kỳ thông tin nào từ token
     *  extractUsername -> extractClaim : lay username (getSubject)
     *  extractExpiration -> extractClaim: lay time (getExpiration)
     *
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /*
     * Sử dụng Jwts.parserBuilder() để giải mã token và trả về các thông tin trong Claims.
     */
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            // Xử lý exception
            return null;
        }
    }

    /*
     * Sử dụng extractClaim để trích xuất thời gian hết hạn của token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /*
     * Kiểm tra token đã hết hạn chưa bằng cách so sánh thời gian hết hạn với thời gian hiện tại.
     */
    private Boolean isTokenExpired(String token) {
        Date expiration = extractExpiration(token);
        return expiration == null || expiration.before(new Date());
    }


    /*
     * Kiểm tra tính hợp lệ của token bằng cách xác nhận username,
     * so sánh với UserDetails và kiểm tra token có hết hạn hay không.
     */
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    // ===================== support =================
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}