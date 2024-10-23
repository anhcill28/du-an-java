import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoginService loginService = new LoginService();
        UserActionService userActionService = new UserActionService();

        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Đọc dòng còn lại

            if (option == 1) {
                // Đăng nhập
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                try {
                    UserData loggedInUser = loginService.login(username, password);
                    System.out.println("Login successful as " + loggedInUser.username());
                    userActionService.handleUserAccess(loggedInUser);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }

            } else if (option == 2) {
                // Đăng ký
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter email: ");
                String email = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                System.out.println("Select role: ");
                System.out.println("1. Admin");
                System.out.println("2. Moderator");
                System.out.println("3. Regular User");
                int role = scanner.nextInt();

                User userType;
                switch (role) {
                    case 1 -> userType = new Admin(username, email, password);
                    case 2 -> userType = new Moderator(username, email, password);
                    case 3 -> userType = new RegularUser(username, email, password);
                    default -> throw new IllegalArgumentException("Invalid role selection");
                }

                try {
                    loginService.register(username, email, password, userType);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }

            } else if (option == 3) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }
}
