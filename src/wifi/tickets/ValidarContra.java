package wifi.tickets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ValidarContra {
    
    String ContraseñaActual;
    
    public boolean ValidarContra(String ContraseñaActual){
        this.ContraseñaActual=ContraseñaActual;
        try {
            Connection conn = (Connection) DbConnect.getConnection();
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement("Select * from userlogin where usuario =? and contrasena=?");
            pst.setString(2, ContraseñaActual);
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
