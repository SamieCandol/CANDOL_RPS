package com.scc.model;

public record users(int user_id, String username, String password, String full_name, String roles, String status) {

    @Override
    public String toString(){
        return String.format("[%d] Username: %s | Name: %s | Role: %s | Status: %s",
                user_id, username, full_name, roles, status);
    }
}