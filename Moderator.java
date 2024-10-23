public final class Moderator extends User {
    public Moderator(String username, String email, String password) {
        super(username, email, password);
    }

    @Override
    public String getUserRole() {
        return "Moderator: Manage content only";
    }

    @Override
    public String getUserType() {
        return "Moderator";
    }
}
