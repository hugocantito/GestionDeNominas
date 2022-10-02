/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hugocantito.gestiondenominas.Laboral;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author hugocantito
 *
 */

public class EmpleadosDAO {

    public static List<Empleado> getEmpleados() {

        Connection con = ConexionBD.getConnection();
        ArrayList<Empleado> lista_empleados = new ArrayList<Empleado>();
        Empleado e;

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select nombre, dni, sexo, categoria, anyos from empleados");

            while (rs.next()) {
                e = new Empleado(rs.getString(1), rs.getString(2), rs.getString(3).toCharArray()[0], rs.getInt(4), rs.getInt(5));
                lista_empleados.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (DatosNoCorrectosException ex) {
            ex.printStackTrace();
        }

        return lista_empleados;

    }

    public static List<String[]> getNominas() {

        Connection con = ConexionBD.getConnection();
        ArrayList<String[]> lista_nominas = new ArrayList<String[]>();
        String[] n = new String[2];
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select dni, sueldo from nominas");

            while (rs.next()) {
                n[0] = rs.getString(1);
                n[1] = String.valueOf(rs.getInt(2));
                lista_nominas.add(n);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return lista_nominas;
    }

    public static void altaEmpleado(Empleado e) {

        Connection con = ConexionBD.getConnection();

        try {
            Statement st = con.createStatement();

            st.execute("insert into Empleados(nombre,dni,sexo,categoria,anyos) values ('" + e.nombre + "','" + e.dni + "','" + e.sexo + "','" + e.getCategoria() + "','" + e.anyos + "')");

            st.execute("insert into nominas(dni, sueldo) values ('" + e.dni + "','" + Nomina.sueldo(e) + "')");

            backup(e);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void altaEmpleado(String nombre, String dni, char sexo, int categoria, int anyos) {
        try {
            Empleado e = new Empleado(nombre, dni, sexo, categoria, anyos);
            altaEmpleado(e);
        } catch (DatosNoCorrectosException ex) {
            ex.printStackTrace();
        }
    }

    public static void altaEmpleado(String nombreFichero) {
        String line;;
        Empleado e;
        String[] datosEmp;

        try {
            File fentrada = new File(nombreFichero);
            FileReader fr = new FileReader(fentrada);
            BufferedReader br = new BufferedReader(fr);

            while (br.ready()) {

                line = br.readLine();
                datosEmp = line.split(";");
                if (datosEmp.length == 3)
                {
                    e = new Empleado(datosEmp[0], datosEmp[1], datosEmp[2].toCharArray()[0]);
                } else 
                {
                    e = new Empleado(datosEmp[0], datosEmp[1], datosEmp[2].toCharArray()[0], Integer.parseInt(datosEmp[3]), Integer.parseInt(datosEmp[4]));
                }
                altaEmpleado(e);

            }
            br.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (DatosNoCorrectosException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param dni
     * @return
     */
    public static int getSueldo(String dni) {

        Connection con = ConexionBD.getConnection();
        int sueldo = -1;

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select sueldo from nominas where dni='" + dni + "'");

            if (rs != null) {
                while (rs.next()) {
                    sueldo = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return sueldo;
    }

    /**
     * @param dni
     * @return
     */
    public static boolean exists(String dni) {

        Connection con = ConexionBD.getConnection();
        boolean exists = false;

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(*) from empleados where dni='" + dni + "'");

            if (rs != null) {
                exists = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return exists;
    }

    public static Empleado getEmpleado(String dni) {
  
        Connection con = ConexionBD.getConnection();
        Empleado e = null;

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select nombre, dni, sexo, categoria, anyos from empleados");

            while (rs.next()) {
                e = new Empleado(rs.getString(1), rs.getString(2), rs.getString(3).toCharArray()[0], rs.getInt(4), rs.getInt(5));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (DatosNoCorrectosException ex) {
            ex.printStackTrace();
        }

        return e;
    }

    public static void updateEmpleado(Empleado e) {

        Connection con = ConexionBD.getConnection();

        try {
            Statement st = con.createStatement();
            System.out.println("update empleados e set e.nombre='" + e.nombre + "' and e.sexo='" + e.sexo + "' and e.categoria=" + e.getCategoria() + " and e.anyos=" + e.anyos + " where e.dni='" + e.dni + "'");
            
            st.execute("update empleados e set e.nombre='" + e.nombre + "' and e.sexo='" + e.sexo + "' and e.categoria=" + e.getCategoria() + " and e.anyos=" + e.anyos + " where e.dni='" + e.dni + "'");
            
            st.execute("update nominas set sueldo=" + Nomina.sueldo(e) + " where dni='" + e.dni + "'");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void updateSueldo(String dni) {

        Connection con = ConexionBD.getConnection();

        try {
            Statement st = con.createStatement();
            st.execute("update nominas set sueldo=" + Nomina.sueldo(getEmpleado(dni)) + " where dni='" + dni + "'");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void bajaEmpleado(String dni) {
        Connection con = ConexionBD.getConnection();

        try {
            Statement st = con.createStatement();

            st.execute("delete from empleados e where e.dni = '" + dni + "'"); //Debería borrar en cascada	
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param e
     */
    public static void cerrarConexion() {
        ConexionBD.close();
    }

    /**
     * @param e
     */
    public static void backup(Empleado e) {

        File fbackup = new File("backup-empleados.txt");
        try {
            FileWriter fw = new FileWriter(fbackup.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(e.nombre + ";" + e.dni + ";" + e.sexo + ";" + e.getCategoria() + ";" + e.anyos + ";" + Nomina.sueldo(e) + '\n');

            bw.close();
            fw.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
