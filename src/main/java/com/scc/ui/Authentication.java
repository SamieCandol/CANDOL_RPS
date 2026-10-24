package com.scc.ui;

import com.scc.dao.UsersDao;
import com.scc.model.users;
import java.util.List;
import java.util.Scanner;

public class Authentication {

    public static void start(Scanner sc, UsersDao usersDao) {
        boolean running = true;

        while (running) {
            String choice = Dashboard.showMainMenu(sc);

            switch (choice) {
                case "1":
                    handleLogin(sc, usersDao);
                    break;
                case "2":
                    handleRegistration(sc, usersDao);
                    break;
                case "3":
                    System.out.println("Exiting system. Thank you!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option! Please choose [1], [2], or [3].");
            }
        }
    }

    private static void handleLogin(Scanner sc, UsersDao usersDao) {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        users user = usersDao.loginUser(username, password);

        if (user == null) {
            System.out.println("-> Login Failed: Incorrect username or password.");
            return;
        }

        if ("PENDING".equalsIgnoreCase(user.status())) {
            System.out.println("-> Login Denied: Your account is still PENDING admin approval.");
            return;
        }

        System.out.println("-> Login Successful! Welcome, " + user.full_name());
        routeUserDashboard(user, sc, usersDao);
    }

    private static void handleRegistration(Scanner sc, UsersDao usersDao) {
        try {
            System.out.print("Enter User ID (e.g. number like 101): ");
            int userId = Integer.parseInt(sc.nextLine());

            System.out.print("Enter username: ");
            String username = sc.nextLine();

            System.out.print("Enter password: ");
            String password = sc.nextLine();

            System.out.print("Enter full name: ");
            String fullName = sc.nextLine();

            System.out.println("Select Role: [1] ADMIN [2] OWNER [3] TENANT");
            System.out.print("Choose role option: ");
            String roleChoice = sc.nextLine();
            String role = "TENANT";

            if (roleChoice.equals("1")) {
                role = "ADMIN";
            } else if (roleChoice.equals("2")) {
                role = "OWNER";
            } else if (roleChoice.equals("3")) {
                role = "TENANT";
            }

            String status = "PENDING"; // New registrations start as PENDING

            usersDao.registerUser(userId, username, password, fullName, role, status);

        } catch (NumberFormatException e) {
            System.out.println("-> Error: User ID must be a valid integer number.");
        }
    }

    private static void routeUserDashboard(users user, Scanner sc, UsersDao usersDao) {
        String role = user.roles() != null ? user.roles().toUpperCase() : "";

        switch (role) {
            case "ADMIN":
                runAdminDashboard(user, sc, usersDao);
                break;
            case "OWNER":
                runOwnerDashboard(user, sc);
                break;
            case "TENANT":
                runTenantDashboard(user, sc);
                break;
            default:
                System.out.println("-> Error: Unknown user role assigned.");
        }
    }

    private static void runAdminDashboard(users user, Scanner sc, UsersDao usersDao) {
        boolean active = true;
        while (active) {
            String choice = Dashboard.showAdminDashboard(user, sc);

            switch (choice) {
                case "1":
                    Dashboard.displayUsersTable("ALL SYSTEM USERS", usersDao.getAllUsers());
                    break;
                case "2":
                    Dashboard.displayUsersTable("PENDING REGISTRATIONS", usersDao.getPendingUsers());
                    break;
                case "3":
                    handleApproveUser(sc, usersDao);
                    break;
                case "4":
                    System.out.println("-> Logging out from Admin Dashboard...");
                    active = false;
                    break;
                default:
                    System.out.println("Invalid option! Please choose between [1] and [4].");
            }
        }
    }

    private static void handleApproveUser(Scanner sc, UsersDao usersDao) {
        List<users> pendingUsers = usersDao.getPendingUsers();
        Dashboard.displayUsersTable("PENDING REGISTRATIONS FOR APPROVAL", pendingUsers);

        if (pendingUsers.isEmpty()) {
            return;
        }

        try {
            System.out.print("\nEnter User ID to approve (or enter 0 to cancel): ");
            int targetId = Integer.parseInt(sc.nextLine());

            if (targetId == 0) {
                System.out.println("-> Approval action cancelled.");
                return;
            }

            boolean isPending = false;
            for (users u : pendingUsers) {
                if (u.user_id() == targetId) {
                    isPending = true;
                    break;
                }
            }

            if (!isPending) {
                System.out.println("-> Error: User ID " + targetId + " is not in the pending list.");
                return;
            }

            boolean success = usersDao.approveUser(targetId);
            if (success) {
                System.out.println("-> Success: User account ID " + targetId + " status updated to 'APPROVED'.");
            } else {
                System.out.println("-> Error updating user status in database.");
            }

        } catch (NumberFormatException e) {
            System.out.println("-> Error: Invalid input! Please enter a valid numerical User ID.");
        }
    }

    private static void runOwnerDashboard(users user, Scanner sc) {
        boolean active = true;
        while (active) {
            String choice = Dashboard.showOwnerDashboard(user, sc);
            if (choice.equals("6")) {
                System.out.println("-> Logging out from Owner Dashboard...");
                active = false;
            } else {
                System.out.println("-> Feature under development.");
            }
        }
    }

    private static void runTenantDashboard(users user, Scanner sc) {
        boolean active = true;
        while (active) {
            String choice = Dashboard.showTenantDashboard(user, sc);
            if (choice.equals("5")) {
                System.out.println("-> Logging out from Tenant Dashboard...");
                active = false;
            } else {
                System.out.println("-> Feature under development.");
            }
        }
    }
}