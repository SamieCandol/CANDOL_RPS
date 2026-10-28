package com.scc.ui;

import com.scc.dao.UsersDao;
import com.scc.model.users;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {

    public static void renderAdminMenu(users adminUser, Scanner sc, UsersDao usersDao) {
        boolean active = true;

        while (active) {
            System.out.println("\n==========================================");
            System.out.println("             ADMIN DASHBOARD              ");
            System.out.println("==========================================");
            System.out.println("Hello, " + adminUser.full_name() + " (Admin)");
            System.out.println("[1] View All System Users");
            System.out.println("[2] View Pending Registrations");
            System.out.println("[3] Approve User Account");
            System.out.println("[4] Update User Role and Status");
            System.out.println("[5] Logout");
            System.out.print("Choose an option: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    displayUsersTable("ALL SYSTEM USERS", usersDao.getAllUsers());
                    break;
                case "2":
                    displayUsersTable("PENDING REGISTRATIONS", usersDao.getPendingUsers());
                    break;
                case "3":
                    handleApproveUser(sc, usersDao);
                    break;
                case "4":
                    handleUpdateUser(adminUser, sc, usersDao);
                    break;
                case "5":
                    System.out.println("-> Logging out from Admin Dashboard...");
                    active = false;
                    break;
                default:
                    System.out.println("Invalid option! Please choose between [1] and [5].");
            }
        }
    }

    private static void displayUsersTable(String title, List<users> userList) {
        System.out.println("\n--- " + title + " ---");
        if (userList == null || userList.isEmpty()) {
            System.out.println("No records found.");
            return;
        }

        String border = "-----------------------------------------------------------------------------------";
        System.out.println(border);
        System.out.printf("%-10s | %-15s | %-20s | %-12s | %-10s%n", "USER ID", "USERNAME", "FULL NAME", "ROLE", "STATUS");
        System.out.println(border);

        for (users u : userList) {
            System.out.printf("%-10d | %-15s | %-20s | %-12s | %-10s%n",
                    u.user_id(),
                    u.username(),
                    u.full_name(),
                    u.roles(),
                    u.status());
        }
        System.out.println(border);
    }

    private static void handleApproveUser(Scanner sc, UsersDao usersDao) {
        List<users> pendingUsers = usersDao.getPendingUsers();
        displayUsersTable("PENDING REGISTRATIONS FOR APPROVAL", pendingUsers);

        if (pendingUsers == null || pendingUsers.isEmpty()) {
            return;
        }

        int targetId = readIntInput(sc, "\nEnter User ID to approve (or enter 0 to cancel): ");
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
    }

    private static void handleUpdateUser(users adminUser, Scanner sc, UsersDao usersDao) {
        displayUsersTable("ALL SYSTEM USERS", usersDao.getAllUsers());

        int targetId = readIntInput(sc, "\nEnter User ID to update (or enter 0 to cancel): ");
        if (targetId == 0) {
            System.out.println("-> Update action cancelled.");
            return;
        }

        // Self-Edit Restriction Rule
        if (targetId == adminUser.user_id()) {
            System.out.println("-> WARNING: Modifying the currently active admin account is restricted.");
            return;
        }

        String newRole = readValidRole(sc);
        String newStatus = readValidStatus(sc);

        boolean success = usersDao.updateUserRoleAndStatus(targetId, newRole, newStatus);
        if (success) {
            System.out.println("-> Success: User ID " + targetId + " updated to Role: " + newRole + ", Status: " + newStatus);
        } else {
            System.out.println("-> Error: Failed to update user in database.");
        }
    }

    // --- Input Validation Wrappers ---

    private static int readIntInput(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("-> Error: Invalid input! Please enter a valid number.");
            }
        }
    }

    private static String readValidRole(Scanner sc) {
        while (true) {
            System.out.print("Enter new role (ADMIN, OWNER, TENANT): ");
            String role = sc.nextLine().trim().toUpperCase();
            if (role.equals("ADMIN") || role.equals("OWNER") || role.equals("TENANT")) {
                return role;
            }
            System.out.println("-> Error: Invalid role. Please choose ADMIN, OWNER, or TENANT.");
        }
    }

    private static String readValidStatus(Scanner sc) {
        while (true) {
            System.out.print("Enter new status (APPROVED, PENDING): ");
            String status = sc.nextLine().trim().toUpperCase();
            if (status.equals("APPROVED") || status.equals("PENDING")) {
                return status;
            }
            System.out.println("-> Error: Invalid status. Please choose APPROVED or PENDING.");
        }
    }
}