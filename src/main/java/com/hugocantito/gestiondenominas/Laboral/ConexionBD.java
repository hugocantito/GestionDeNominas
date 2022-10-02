/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hugocantito.gestiondenominas.Laboral;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author hugocantito
 */
public class ConexionBD {

    private static Connection con = null;
    private static final String conUrl = "jdbc:mysql://localhost:3306/nominas?user=root&password=root"
            + "&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static Connection getConnection() {
        try {
            if (con == null) {
                con = DriverManager.getConnection(ConexionBD.conUrl);
                System.out.println("Conexión realizada correctamente");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return con;
    }

    public static void close() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}
