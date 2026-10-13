package com.scc.ui;

import com.scc.model.users;
import java.util.Scanner;

public class Dashboard{

    // 1. Main Menu Display
    public static String showMainMenu(Scanner sc) {
        System.out.println("\n==================================");
        System.out.println("    RENTAL PROPERTY SYSTEM");
        System.out.println("==================================");
        System.out.println("[1] Login");
        System.out.println("[2] Register");
        System.out.println("[3] Exit");
        System.out.print("Choose an option: ");
        return sc.nextLine();
    }

    // 2. Admin Dashboard Display
    public static String showAdminDashboard(users user, Scanner sc) {
        System.out.println("\n==================================");
        System.out.println("       ADMIN DASHBOARD       ");
        System.out.println("==================================");
        System.out.println("Hello, " + user.full_name() + " (Admin)");
        System.out.println("[1] Approve Pending Users");
        System.out.println("[2] View All Users & Delete Accounts");
        System.out.println("[3] View System Activity Logs");
        System.out.println("[4] Logout");
        System.out.print("Choose an option: ");
        return sc.nextLine();
    }

    // 3. Owner Dashboard Display
    public static String showOwnerDashboard(users user, Scanner sc) {
        System.out.println("\n==================================");
        System.out.println("       OWNER DASHBOARD     ");
        System.out.println("==================================");
        System.out.println("Hello, " + user.full_name() + " (Owner)");
        System.out.println("[1] Add New Rental Property Unit (CREATE)");
        System.out.println("[2] View & Search Properties by Status (Occupied/Vacant)");
        System.out.println("[3] Update Property Details or Rent Prices (UPDATE)");
        System.out.println("[4] Delete/Obsolete Property Unit (DELETE)");
        System.out.println("[5] View Monthly Rental Revenue Report");
        System.out.println("[6] Logout");
        System.out.print("Choose an option: ");
        return sc.nextLine();
    }

    // 4. Tenant Dashboard Display
    public static String showTenantDashboard(users user, Scanner sc) {
        System.out.println("\n==================================");
        System.out.println("      TENANT DASHBOARD     ");
        System.out.println("==================================");
        System.out.println("Hello, " + user.full_name() + " (Tenant)");
        System.out.println("[1] Browse Vacant Property Units");
        System.out.println("[2] Record New Lease / Monthly Rent Payment");
        System.out.println("[3] View My Payment Histories");
        System.out.println("[4] Cancel Draft Lease Agreement");
        System.out.println("[5] Logout");
        System.out.print("Choose an option: ");
        return sc.nextLine();
    }
}