package com.whilewework.fahasa.utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

class JwtUtilTest {

    private JwtUtil jwtUtil = new JwtUtil();

    /*
    *   Mục đích: Kiểm tra xem phương thức generateToken của lớp JwtUtil có thể tạo ra một token hợp lệ không.
    *   Phương thức chính:
    *   Gọi phương thức generateToken của đối tượng jwtUtil với tham số là "testuser".
    *   Kiểm tra xem token được trả về có khác null không (assertNotNull).
    */
    @Test
    void testGenerateToken() {
        String token = jwtUtil.generateToken("testuser");
        assertNotNull(token);
    }

    /*
    *   Mục đích: Kiểm tra xem phương thức extractUsername của lớp JwtUtil có thể lấy ra username từ token hợp lệ không.
    *   Phương thức chính:
    *   - Tạo một token bằng cách gọi phương thức generateToken với tham số "testuser".
    *   - Gọi phương thức extractUsername để trích xuất username từ token đã tạo.
    *   - So sánh username trích xuất được với "testuser" bằng cách sử dụng assertEquals.
     */
    @Test
    void testExtractUsername() {
        String token = jwtUtil.generateToken("testuser");
        String username = jwtUtil.extractUsername(token);
        assertEquals("testuser", username);
    }

    /*
    * Mục đích: Kiểm tra xem phương thức validateToken của lớp JwtUtil có thể xác thực một token JWT hợp lệ cho một UserDetails cụ thể không.
      Phương thức chính:
        - Tạo một đối tượng UserDetails đại diện cho người dùng với username là "testuser", password là "password", và quyền hạn là "ROLE_USER".
        - Tạo một token bằng cách gọi phương thức generateToken với tham số "testuser".
        - Gọi phương thức validateToken để xác thực token với UserDetails đã tạo.
        - Sử dụng assertTrue để đảm bảo rằng kết quả của phương thức validateToken là true.
     */
    @Test
    void testValidateToken() {
        UserDetails userDetails = User.withUsername("testuser").password("password").authorities("ROLE_USER").build();
        String token = jwtUtil.generateToken("testuser");
        assertTrue(jwtUtil.validateToken(token, userDetails));
    }
}
