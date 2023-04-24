package com.mycompany.tp;

import java.sql.*;
public class conexion {

    Connection conectar = null;
    String usuario = "root";
    String contrasena = "3540";
    String url = "jdbc:mysql://localhost/bd_pronosticos";

    public Connection establecerconexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conectar = DriverManager.getConnection(url, usuario, contrasena);
        } catch (Exception e) {
            
        }
        return conectar;
    }
    
}
