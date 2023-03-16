package com.app.MobileAppProject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    @Entity
    @Table(name = "admin")
    public class admin {
        @Id
//        @GeneratedValue(strategy = GenerationType.AUTO)
        private String adminId;
        private String password;

        public String getAdminId() {
            return adminId;
        }

        public void setAdminId(String adminId) {
            this.adminId = adminId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "admin{" +
                    "adminId='" + adminId + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }


