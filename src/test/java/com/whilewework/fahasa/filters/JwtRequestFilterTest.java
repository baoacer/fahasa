package com.whilewework.fahasa.filters;

import com.whilewework.fahasa.services.impl.UserDetailsServiceImpl;
import com.whilewework.fahasa.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class JwtRequestFilterTest {

    @Mock
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /*
     - Mục đích: Kiểm tra xem khi có một token hợp lệ được cung cấp trong header Authorization,
     JwtRequestFilter có thực hiện xác thực người dùng và đặt thông tin xác thực
     vào SecurityContextHolder hay không.
      - Cài đặt:
            + Giả định rằng request chứa một token hợp lệ.
            + Mock các phương thức của JwtUtil để trích xuất username từ token
             và kiểm tra tính hợp lệ của token.
            + Mock phương thức của UserDetailsServiceImpl để trả về thông tin người
             dùng khi tên người dùng được truyền vào.

       - Kết quả mong đợi:
            + JwtRequestFilter sẽ thực hiện xác thực thành công.
            + Sau khi xác thực, thông tin xác thực sẽ được đặt vào SecurityContextHolder.
    * */
    @Test
    public void testDoFilterInternal_validToken() throws ServletException, IOException {
        String token = "valid_token";
        UserDetails userDetails = new User("testuser", "", Collections.emptyList());

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.extractUsername(token)).thenReturn("testuser");
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);
        when(jwtUtil.validateToken(token, userDetails)).thenReturn(true);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(userDetailsService, times(1)).loadUserByUsername("testuser");
        verify(filterChain, times(1)).doFilter(request, response);

        // Assert that authentication was set in SecurityContextHolder
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        assert SecurityContextHolder.getContext().getAuthentication() != null;
    }

    /*
    Mục đích: Kiểm tra xem khi có một token không hợp lệ được cung cấp trong header Authorization,
                JwtRequestFilter có xử lý ngoại lệ và không thực hiện xác thực người dùng.
    Cài đặt:
        + Giả định rằng request chứa một token không hợp lệ.
        + Mock các phương thức của JwtUtil để ném ra ngoại lệ khi trích xuất username từ token.
        + Không mock phương thức của UserDetailsServiceImpl, bởi vì không mong đợi rằng phương thức
        này sẽ được gọi khi có lỗi xảy ra.
    Kết quả mong đợi:
        + JwtRequestFilter không gọi phương thức loadUserByUsername của UserDetailsServiceImpl.
        + Không có thông tin xác thực nào được đặt vào SecurityContextHolder
    * */
    @Test
    public void testDoFilterInternal_invalidToken() throws ServletException, IOException {
        String token = "invalid_token";

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.extractUsername(token)).thenThrow(new RuntimeException("Invalid token"));

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(filterChain, times(1)).doFilter(request, response);

        // Assert that no authentication was set in SecurityContextHolder
        assert SecurityContextHolder.getContext().getAuthentication() == null;
    }

    /*
        Mục đích: Kiểm tra xem khi không có token được cung cấp trong header Authorization,
                    JwtRequestFilter có bỏ qua việc xác thực người dùng.
        Cài đặt:
            + Giả định rằng request không chứa header Authorization.
            + Không mock bất kỳ phương thức nào của JwtUtil hay UserDetailsServiceImpl, bởi vì không mong đợi rằng chúng sẽ được gọi khi không có token.
        Kết quả mong đợi:
            + JwtRequestFilter không gọi phương thức loadUserByUsername của UserDetailsServiceImpl.
            + Không có thông tin xác thực nào được đặt vào SecurityContextHolder
    * */
    @Test
    public void testDoFilterInternal_noToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(filterChain, times(1)).doFilter(request, response);

        // Assert that no authentication was set in SecurityContextHolder
        assert SecurityContextHolder.getContext().getAuthentication() == null;
    }


    /*
        Mục đích: Kiểm tra xem khi có một lỗi xảy ra trong quá trình xử lý xác thực
                    người dùng (ví dụ: lỗi kết nối cơ sở dữ liệu), JwtRequestFilter
                    có xử lý ngoại lệ và không đặt thông tin xác thực vào SecurityContextHolder.
        Cài đặt:
            + Giả định rằng request chứa một token hợp lệ.
            + Mock các phương thức của JwtUtil để trích xuất username từ token và kiểm tra tính hợp lệ của token.
            + Mock phương thức loadUserByUsername của UserDetailsServiceImpl để ném ra ngoại lệ khi xử lý yêu cầu.
        Kết quả mong đợi:
            + JwtRequestFilter gọi phương thức loadUserByUsername của UserDetailsServiceImpl.
            + Do lỗi xảy ra trong phương thức này, không có thông tin xác thực nào được đặt vào SecurityContextHolder.
    * */
    @Test
    public void testDoFilterInternal_exceptionThrown() throws ServletException, IOException {
        String token = "valid_token";
        UserDetails userDetails = new User("testuser", "", Collections.emptyList());

        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        when(jwtUtil.extractUsername(token)).thenReturn("testuser");
        when(userDetailsService.loadUserByUsername("testuser")).thenThrow(new RuntimeException("Database connection failed"));

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(userDetailsService, times(1)).loadUserByUsername("testuser");
        verify(filterChain, times(1)).doFilter(request, response);

        // Assert that no authentication was set in SecurityContextHolder due to exception
        assert SecurityContextHolder.getContext().getAuthentication() == null;
    }
}
