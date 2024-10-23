public abstract sealed class User permits Admin, Moderator, RegularUser {
    protected String username;
    protected String email;
    protected String password;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public abstract String getUserRole(); // Phương thức trừu tượng để lấy quyền người dùng

    public abstract String getUserType(); // Phương thức trừu tượng để lấy loại người dùng
}
