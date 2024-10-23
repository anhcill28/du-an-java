public final class RegularUser extends User {
    public RegularUser(String username, String email, String password) {
        super(username, email, password);
    }

    @Override
    public String getUserRole() {
        return "Regular User: View content only";
    }

    @Override
    public String getUserType() {
        return "Regular User";
    }
}
