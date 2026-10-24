package com.scc.ui;

import com.scc.model.users;
import java.util.List;
import java.util.Scanner;

public class Dashboard {

    // 1. Main Menu Display
    public static String showMainMenu(Scanner sc) {
        System.out.println("\n==========================================");
        System.out.println("          RENTAL PROPERTY SYSTEM          ");
        System.out.println("==========================================");
        System.out.println("[1] Login");
        System.out.println("[2] Register");
        System.out.println("[3] Exit");
        System.out.print("Choose an option: ");
        return sc.nextLine();
    }

    // 2. Admin Dashboard Display
    public static String showAdminDashboard(users user, Scanner sc) {
        System.out.println("\n==========================================");
        System.out.println("             ADMIN DASHBOARD              ");
        System.out.println("==========================================");
        System.out.println("Hello, " + user.full_name() + " (Admin)");
        System.out.println("[1] View All System Users");
        System.out.println("[2] View Pending Registrations");
        System.out.println("[3] Approve User Account");
        System.out.println("[4] Logout");
        System.out.print("Choose an option: ");
        return sc.nextLine();
    }

    // Formatted Console Output Table for Admin (Deliverable 5 requirement)
    public static void displayUsersTable(String title, List<users> userList) {
        System.out.println("\n--- " + title + " ---");
        if (userList.isEmpty()) {
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

    // 3. Owner Dashboard Display
    public static String showOwnerDashboard(users user, Scanner sc) {
        System.out.println("\n==========================================");
        System.out.println("          PROPERTY OWNER DASHBOARD        ");
        System.out.println("==========================================");
        System.out.println("Hello, " + user.full_name() + " (Owner)");
        System.out.println("[1] Add New Rental Property Unit (CREATE)");
        System.out.println("[2] View & Search Properties by Status");
        System.out.println("[3] Update Property Details / Rent Prices");
        System.out.println("[4] Delete Property Unit");
        System.out.println("[5] View Tenant Payment Histories");
        System.out.println("[6] Logout");
        System.out.print("Choose an option: ");
        return sc.nextLine();
    }

    // 4. Tenant Dashboard Display
    public static String showTenantDashboard(users user, Scanner sc) {
        System.out.println("\n==========================================");
        System.out.println("             TENANT DASHBOARD             ");
        System.out.println("==========================================");
        System.out.println("Hello, " + user.full_name() + " (Tenant)");
        System.out.println("[1] Record Monthly Rent Payment");
        System.out.println("[2] View My Lease & Payment History");
        System.out.println("[3] Update My Payment Status");
        System.out.println("[4] Cancel Draft Lease Agreement");
        System.out.println("[5] Logout");
        System.out.print("Choose an option: ");
        return sc.nextLine();
    }
}