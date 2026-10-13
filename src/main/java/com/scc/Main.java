package com.scc;

import com.scc.dao.UsersDao;
import com.scc.model.users;
import com.scc.ui.Dashboard;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final UsersDao usersDao = new UsersDao();

    public static void main(String[] args) {
        boolean systemRunning = true;

        while (systemRunning) {
            String choice = Dashboard.showMainMenu(sc);

            switch (choice) {
                case "1": // Login
                    System.out.print("Enter username: ");
                    String loginUser = sc.nextLine();
                    System.out.print("Enter password: ");
                    String loginPass = sc.nextLine();

                    users loggedInUser = usersDao.loginUser(loginUser, loginPass);

                    if (loggedInUser != null) {
                        if ("PENDING".equalsIgnoreCase(loggedInUser.status())) {
                            System.out.println("\n-> Your account is still waiting for approval by the Admin.");
                        } else {
                            System.out.println("\n-> Login Successful! Welcome, " + loggedInUser.full_name());

                            String role = loggedInUser.roles() != null ? loggedInUser.roles().toUpperCase() : "";
                            boolean inDashboard = true;

                            while (inDashboard) {
                                String dashChoice = "";

                                switch (role) {
                                    case "ADMIN":
                                        dashChoice = Dashboard.showAdminDashboard(loggedInUser, sc);
                                        break;
                                    case "OWNER":
                                        dashChoice = Dashboard.showOwnerDashboard(loggedInUser, sc);
                                        break;
                                    case "TENANT":
                                        dashChoice = Dashboard.showTenantDashboard(loggedInUser, sc);
                                        break;
                                    default:
                                        inDashboard = false;
                                        break;
                                }

                                // Dashboard Case Conditions
                                switch (dashChoice) {
                                    case "1":
                                        System.out.println("\n-> [Action] Executing Dashboard Option 1...");
                                        break;
                                    case "2":
                                        System.out.println("\n-> [Action] Executing Dashboard Option 2...");
                                        break;
                                    case "3":
                                        System.out.println("\n-> [Action] Executing Dashboard Option 3...");
                                        break;
                                    case "4": // Kung Admin ang naka-login, Option 4 ang Logout
                                        if ("ADMIN".equals(role)) {
                                            System.out.println("\n-> Logging out from Admin Dashboard...");
                                            inDashboard = false; // Mo-gawas sa loop aron ma-logout
                                        } else {
                                            System.out.println("\n-> [Action] Executing Dashboard Option 4...");
                                        }
                                        break;
                                    case "5": // Kung Tenant ang naka-login, Option 5 ang Logout
                                    case "6": // Kung Owner ang naka-login, Option 6 ang Logout
                                        System.out.println("\n-> Logging out from Dashboard...");
                                        inDashboard = false; // Mo-gawas sa loop aron ma-logout
                                        break;
                                    default:
                                        System.out.println("\n-> Invalid choice inside dashboard.");
                                        break;
                                }
                            }
                        }
                    } else {
                        System.out.println("\n-> Invalid username or password!");
                    }
                    break; // Naidagdag ang kinahanglan nga break para sa case "1"

                case "2": // Register
                    System.out.print("Enter full_name: ");
                    String fullname = sc.nextLine();
                    System.out.print("Enter username: ");
                    String usersName = sc.nextLine();
                    System.out.print("Enter password: ");
                    String pass = sc.nextLine();

                    System.out.print("Select Roles [1] ADMIN | [2] OWNER | [3] TENANT: ");
                    String rolesChoice = sc.nextLine();

                    String roles = switch (rolesChoice) {
                        case "1" -> "ADMIN";
                        case "2" -> "OWNER";
                        case "3" -> "TENANT";
                        default -> null;
                    };

                    if (roles != null) {
                        int userId = (int) (System.currentTimeMillis() % 100000);
                        usersDao.registerUser(userId, usersName, pass, fullname, roles, "PENDING");
                    } else {
                        System.out.println("Invalid roles selection!");
                    }
                    break;

                case "3": // Exit
                    System.out.println("\n-> Exiting system. Thank you for using NestSync!");
                    systemRunning = false;
                    break;

                default:
                    System.out.println("\n-> Invalid choice! Palihog pagpili og tarong.");
                    break;
            }
        }
        sc.close();
    }
}