/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.erp.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Jerome
 */
public class Tools {

    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isLong(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static java.sql.Date sqlDate() {
        java.util.Date uDate = new java.util.Date();
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }

    public static String currentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();
        return dateFormat.format(date);
    }

    public static String sqlDateFormat(java.util.Date date) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(date);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }
}
