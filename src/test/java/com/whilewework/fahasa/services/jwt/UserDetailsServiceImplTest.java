package com.whilewework.fahasa.services.jwt;

import com.whilewework.fahasa.entity.User;
import com.whilewework.fahasa.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    /*
        Mục đích: Kiểm tra khi tìm thấy người dùng trong repository dựa trên email,
                  UserDetailsServiceImpl có thể trả về thông tin người dùng chính xác từ repository hay không.
        Cài đặt:
            + Chuẩn bị một đối tượng User với email và mật khẩu.
            + Mock phương thức findFirstByEmail của UserRepository để trả về một Optional chứa đối tượng User.
            + Gọi phương thức loadUserByUsername của UserDetailsServiceImpl với email đã cho.
        Kết quả mong đợi:
            + UserDetailsServiceImpl sẽ trả về UserDetails chứa thông tin của người dùng đã được tìm thấy.
            + Các thông tin như username, password và danh sách quyền hạn sẽ được trả về chính xác.
    * */
    @Test
    public void testLoadUserByUsername_UserFound() {
        // Arrange
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);
        user.setPassword("password");

        when(userRepository.findFirstByEmail(email)).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        // Assert
        assertEquals(email, userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals(0, userDetails.getAuthorities().size());

        verify(userRepository, times(1)).findFirstByEmail(email);
    }


    /*
        Mục đích: Kiểm tra khi không tìm thấy người dùng trong repository dựa trên email,
                UserDetailsServiceImpl sẽ ném ra UsernameNotFoundException như mong đợi hay không.
        Cài đặt:
            + Mock phương thức findFirstByEmail của UserRepository để trả về một Optional rỗng khi tìm kiếm người dùng.
            + Gọi phương thức loadUserByUsername của UserDetailsServiceImpl với một email không tồn tại trong repository.
        Kết quả mong đợi:
            + UserDetailsServiceImpl ném ra UsernameNotFoundException với thông báo tương ứng
             khi không tìm thấy người dùng với email đã cho.
    * */
    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Arrange
        String email = "nonexistent@example.com";
        when(userRepository.findFirstByEmail(email)).thenReturn(Optional.empty());

        // Act and Assert
        UsernameNotFoundException exception = org.junit.jupiter.api.Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(email)
        );

        assertEquals("User not found with username: " + email, exception.getMessage());

        verify(userRepository, times(1)).findFirstByEmail(email);
    }
}
