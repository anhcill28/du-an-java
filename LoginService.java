// File: LoginService.java
import java.util.HashMap;
import java.util.Map;

public class LoginService {
    private final Map<String, UserData> users = new HashMap<>();  // Lưu trữ thông tin người dùng

    public LoginService() {
        // Thêm một số người dùng mặc định
        users.put("nguyenvananh", new UserData("nguyenvananh", "nguyenvananh@domain.vn", "matkhausuper", new Admin("nguyenvananh", "nguyenvananh@domain.vn", "matkhausuper")));
        users.put("tranthibinh", new UserData("tranthibinh", "tranthibinh@domain.vn", "modkhau123", new Moderator("tranthibinh", "tranthibinh@domain.vn", "modkhau123")));
        users.put("phamvanduc", new UserData("phamvanduc", "phamvanduc@domain.vn", "userkhau456", new RegularUser("phamvanduc", "phamvanduc@domain.vn", "userkhau456")));
    }

    // Đăng nhập dựa trên tên người dùng và mật khẩu
    public UserData login(String username, String password) throws IllegalArgumentException {
        UserData userData = users.get(username);
        if (userData == null || !userData.password().equals(password)) {
            throw new IllegalArgumentException("Tên đăng nhập hoặc mật khẩu không hợp lệ");
        }
        return userData;
    }

    // Đăng ký người dùng mới
    public void register(String username, String email, String password, User userType) {
        if (users.containsKey(username)) {
            throw new IllegalArgumentException("Người dùng đã tồn tại");
        }
        users.put(username, new UserData(username, email, password, userType));
        System.out.println("Người dùng " + username + " đã được đăng ký thành công.");
    }

    // Xóa người dùng
    public void deleteUser(String username) {
        if (!users.containsKey(username)) {
            throw new IllegalArgumentException("Người dùng không tồn tại");
        }
        users.remove(username);
        System.out.println("Người dùng " + username + " đã được xóa thành công.");
    }
}
