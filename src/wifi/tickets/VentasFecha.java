/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wifi.tickets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER-PC
 */
public class VentasFecha {

    String Fecha1, Fecha2;
    Connection conn = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public VentasFecha(String Fecha1, String Fecha2) {
        this.Fecha1 = Fecha1;
        this.Fecha2 = Fecha2;

        try {
            Connection conn = DbConnect.getConnection();
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement("SELECT sum(Precio) as total FROM tickets WHERE Fecha BETWEEN ? AND ?");
            pst.setString(1, Fecha1);
            pst.setString(2, Fecha2);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString("total"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Se presentó un problema ver el registro de ventas.");
            e.printStackTrace();
        }
    }

}
