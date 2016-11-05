package wifi.tickets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import wifi.tickets.DbConnect;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ACER-PC
 */
public class ValidarLogin {

    String Usuario, Contraseña;

    public boolean ValidarLogin(String Usuario, String Contraseña) {
        this.Usuario = Usuario;
        this.Contraseña = Contraseña;
        try {
            Connection conn = (Connection) DbConnect.getConnection();
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement("Select * from userlogin where usuario=? and contrasena=?");
            pst.setString(1, Usuario);
            pst.setString(2, Contraseña);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }

    }

}
