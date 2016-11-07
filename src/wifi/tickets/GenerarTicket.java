/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wifi.tickets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.swing.JOptionPane;
import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;

/**
 *
 * @author ACER-PC
 */
public class GenerarTicket {

    String Tiempo, Plan, Usuario, Contraseña, Fecha;

    public GenerarTicket(String Plan, String Tiempo) throws MikrotikApiException {
        ApiConnection con = ApiConnection.connect("192.168.10.1"); // connect to router
        con.login("admin", "jesus00**"); // log in to router
        

        final String alphabet = "abcdefghijkmnj";
        final String numbers = "1234567890";
        final int N = alphabet.length();
        final int B = numbers.length();
        String UsuarioGen = "";
        String ContraseñaGen = "";
        String FechaGen;
        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            UsuarioGen += Character.toString(alphabet.charAt(r.nextInt(N)));
            ContraseñaGen += Character.toString(numbers.charAt(r.nextInt(B)));
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        FechaGen = dateFormat.format(date);
        this.Plan = Plan;
        this.Tiempo = Tiempo;
        this.Usuario = UsuarioGen;
        this.Contraseña = ContraseñaGen;
        this.Fecha = FechaGen;

        try {
            Connection conn = DbConnect.getConnection();
            String query = ("INSERT INTO tickets(Usuario, Contraseña, Tiempo, Plan, Fecha) VALUES('" + UsuarioGen + "','" + ContraseñaGen + "','" + Tiempo + "','" + Plan + "','" + FechaGen + "')");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            con.execute("/ip/hotspot/user/add name='" + UsuarioGen + "' limit-uptime=60 profile='" + Plan + "' password='" + ContraseñaGen + "'"); // execute a command
            JOptionPane.showMessageDialog(null, "¡Usuario generado con éxito!\n\n"
                    + "Nombre de usuario:\t " + UsuarioGen + "\n"
                    + "Contraseña:\t " + ContraseñaGen + "\n"
                    + "Plan:\t " + Plan + "\n"
                    + "Tiempo:\t " + Tiempo + "\n"
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Se presentó un problema al generar el usuario.");
            e.printStackTrace();
        }
        //System.out.println(UsuarioGen+" "+ ContraseñaGen+" "+ Tiempo+" " +Plan+ " "+FechaGen);
        con.disconnect(); // disconnect from router
    }

}
