package com.scc;

import com.scc.dao.DatabaseInitializer;
import com.scc.dao.UsersDao;
import com.scc.ui.Authentication;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. Initialize Database & Tables automatically on startup[cite: 2]
        DatabaseInitializer.initializeDatabase();

        // 2. Instantiate shared resources (Scanner and DAO)[cite: 2]
        Scanner sc = new Scanner(System.in);
        UsersDao usersDao = new UsersDao();

        // 3. Delegate execution to Authentication start[cite: 2]
        Authentication.start(sc, usersDao);

        sc.close();
    }
}