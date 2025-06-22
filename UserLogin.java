public class UserLogin {
    public static void main(String[] args) throws Exception {
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        System.out.print("Enter username or phone number: ");
        String userInput = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String username = "admin";
        String phoneNumber = "1234567890";
        String correctPassword = "password";

        if ((username.equals(userInput) || phoneNumber.equals(userInput)) && correctPassword.equals(password)) {
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username/phone or password.");
        }
        scanner.close();
    }
}
