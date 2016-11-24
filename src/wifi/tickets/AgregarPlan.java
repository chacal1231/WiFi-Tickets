/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wifi.tickets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import me.legrange.mikrotik.ApiConnection;

/**
 *
 * @author ACER-PC
 */
public class AgregarPlan {

    String NombrePlan, P1h, P1d, P7d, P15d, P1m;

    public AgregarPlan(String NombrePlan, String P1h, String P1d, String P7d, String P15d, String P1m) {
        this.NombrePlan = NombrePlan;
        this.P1h = P1h;
        this.P1d = P1d;
        this.P7d = P7d;
        this.P15d = P15d;
        this.P1m = P1m;
        GetConfig Gc = new GetConfig();
        try {
            Connection conn = (Connection) DbConnect.getConnection();
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement("INSERT INTO planes(plan,1h,1d,7d,15d,1m) VALUES(?,?,?,?,?,?)");
            pst.setString(1, NombrePlan);
            pst.setString(2, P1h);
            pst.setString(3, P1d);
            pst.setString(4, P7d);
            pst.setString(5, P15d);
            pst.setString(6, P1m);
            pst.executeUpdate();
            ApiConnection con = ApiConnection.connect(Gc.RbIP()); // Conexión al RouterBoard
            con.login(Gc.RbUsuario(), Gc.RbContra()); // Acceder al RouterBoard
            con.execute("/ip/hotspot/user/profile/add name='" + NombrePlan + "' rate-limit='" + "1M/1M" + "' shared-users='" + "1" + "' keepalive-timeout='" + "none" + "'"); // execute a command
            JOptionPane.showMessageDialog(null, "¡Se agregó correctamente el plan");

        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}
