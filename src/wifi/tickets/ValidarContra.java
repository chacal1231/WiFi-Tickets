package wifi.tickets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ValidarContra {

    String ContraseñaActual, ContraseñaNueva;

    public String ValidarContra(String ContraseñaActual, String ContraseñaNueva) {
        this.ContraseñaActual = ContraseñaActual;
        this.ContraseñaNueva = ContraseñaNueva;
        ValidarLoginx VLX = new ValidarLoginx();
        try {
            Connection conn = (Connection) DbConnect.getConnection();
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement("Select * from userlogin where usuario =?");
            pst.setString(1, VLX.getUsuario());
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                if (rs.getString("contrasena").equals(ContraseñaActual)) {
                    Connection connx = (Connection) DbConnect.getConnection();
                    String query = ("UPDATE userlogin SET contrasena='" + ContraseñaNueva + "' WHERE usuario='" + VLX.getUsuario() + "'");
                    Statement stmt = connx.createStatement();
                    stmt.executeUpdate(query);
                    return "SI";
                } else {
                    System.out.println("NO");
                    return "NO";
                }

            }
            return "MAL";
        } catch (Exception e) {
            e.printStackTrace();
            return "!";

        }
    }

}
