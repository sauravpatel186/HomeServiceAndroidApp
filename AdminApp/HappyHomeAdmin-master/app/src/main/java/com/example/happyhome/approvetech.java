package com.example.happyhome;

import androidx.appcompat.app.AppCompatActivity;

public class approvetech {
        String category;
        String dob;
        String fullname;

        public approvetech() {

        }

        @Override
        public String toString() {
                return "approvetech{" +
                        "category='" + category + '\'' +
                      ", dob='" + dob + '\'' +
                        ", fullname='" + fullname + '\'' +
                        '}';
        }

        public String getCategory() {
                return category;
        }

        public void setCategory(String category) {
                this.category = category;
        }

       public String getDob() {
                return dob;
       }

       public void setDob(String dob) {
               this.dob = dob;
       }

        public String getFullname() {
                return fullname;
        }

       public void setFullname(String fullname) {
               this.fullname = fullname; }


}
