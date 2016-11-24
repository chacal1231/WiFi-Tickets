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
import java.util.Calendar;
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

    public GenerarTicket(String Plan, String Tiempo) {
        final String alphabet = "abcdefghijkmnj";
        final String numbers = "1234567890";
        final int N = alphabet.length();
        final int B = numbers.length();
        String UsuarioGen = "";
        String ContraseñaGen = "";
        String FechaGen;
        ValidarLogin VLX = new ValidarLogin();
        int Precio=0;
        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            UsuarioGen += Character.toString(alphabet.charAt(r.nextInt(N)));
            ContraseñaGen += Character.toString(numbers.charAt(r.nextInt(B)));
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        FechaGen = dateFormat.format(date);
        Calendar cal = Calendar.getInstance();
        String Mes = new SimpleDateFormat("MMMMMMMMMM").format(cal.getTime());
        this.Plan = Plan;
        this.Tiempo = Tiempo;
        this.Usuario = UsuarioGen;
        this.Contraseña = ContraseñaGen;
        this.Fecha = FechaGen;

        try {
            Connection conn = DbConnect.getConnection();
            PreparedStatement pst = (PreparedStatement) conn.prepareStatement("Select * FROM planes WHERE plan=?");
            pst.setString(1, Plan);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                if(Tiempo.equals("1 Hora")){
                    Precio=rs.getInt("1h");
                }else if(Tiempo.equals("1 Dia")){
                    Precio=rs.getInt("1d");
                }else if(Tiempo.equals("7 Dias")){
                    Precio=rs.getInt("7d");
                }else if(Tiempo.equals("15 Dias")){
                    Precio=rs.getInt("15d");
                }else if(Tiempo.equals("1 Mes")){
                    Precio=rs.getInt("1m");
                }
            }
            String query = ("INSERT INTO tickets(Usuario, Contraseña, Tiempo, Plan, Precio, Fecha, UsuGene, Mes) VALUES('" + UsuarioGen + "','" + ContraseñaGen + "','" + Tiempo + "','" + Plan + "', '" + Precio + "', '" + FechaGen + "', '" + VLX.getUsuario() + "' , '" + Mes + "')");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            ExecCommandMikrotik CommandMi = new ExecCommandMikrotik(UsuarioGen, ContraseñaGen, Plan, Tiempo); //Llamar clase para agregar comando al RouterBoard
            JOptionPane.showMessageDialog(null, "¡Usuario generado con éxito!\n\n"
                    + "Nombre de usuario:       " + UsuarioGen + "\n"
                    + "Contraseña:                     " + ContraseñaGen + "\n"
                    + "Plan:                                  " + Plan + "\n"
                    + "Tiempo:                            " + Tiempo + "\n"
            );

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Se presentó un problema al generar el usuario.");
            e.printStackTrace();
        }
    }

}
