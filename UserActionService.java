public class UserActionService {

    public void handleUserAccess(UserData userData) {
        switch (userData.userType().getUserType()) {
            case "Admin" -> grantAdminAccess((Admin) userData.userType());
            case "Moderator" -> grantModeratorAccess((Moderator) userData.userType());
            case "Regular User" -> grantRegularUserAccess((RegularUser) userData.userType());
            default -> throw new IllegalStateException("Unexpected value: " + userData.userType());
        }
    }

    private void grantAdminAccess(Admin admin) {
        System.out.println(admin.getUsername() + " has full access to the system.");
        System.out.println("Admin privileges: Create/Delete Users, Manage System Settings.");
    }

    private void grantModeratorAccess(Moderator moderator) {
        System.out.println(moderator.getUsername() + " can manage content.");
        System.out.println("Moderator privileges: Edit/Delete Content.");
    }

    private void grantRegularUserAccess(RegularUser regularUser) {
        System.out.println(regularUser.getUsername() + " can view content.");
        System.out.println("User privileges: View Content, Comment.");
    }
}
