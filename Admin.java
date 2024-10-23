public final class Admin extends User {
    public Admin(String username, String email, String password) {
        super(username, email, password);
    }

    @Override
    public String getUserRole() {
        return "Admin: Full system control";
    }

    @Override
    public String getUserType() {
        return "Admin";
    }
}
