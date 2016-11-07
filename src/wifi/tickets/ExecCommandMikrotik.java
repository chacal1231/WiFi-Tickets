/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wifi.tickets;

import me.legrange.mikrotik.ApiConnection;
import me.legrange.mikrotik.MikrotikApiException;

/**
 *
 * @author ACER-PC
 */
public class ExecCommandMikrotik {

    String UsuarioGen, ContraseñaGen, Plan, Tiempo;

    public ExecCommandMikrotik(String UsuarioGen, String ContraseñaGen, String Plan, String Tiempo) throws MikrotikApiException {
        this.UsuarioGen = UsuarioGen;
        this.ContraseñaGen = ContraseñaGen;
        this.Plan = Plan;
        this.Tiempo = Tiempo;
        int TiempoMinutos = 0;
        //RouterBoard
        ApiConnection con = ApiConnection.connect("192.168.10.1"); // Conexión al RouterBoard
        con.login("admin", "jesus00**"); // Acceder al RouterBoard
        //EjecutarComando
        switch (Tiempo) {
            case "1 Hora": {
                TiempoMinutos = (60*60);
                break;
            }
            case "1 Dia": {
                TiempoMinutos = (1440*60);
                break;
            }
            case "7 Dias": {
                TiempoMinutos = (10080*60);
                break;
            }
            case "15 Dias": {
                TiempoMinutos = (21600*60);
                break;
            }
            case "1 Mes": {
                TiempoMinutos = (43200*60);
                break;
            }
        }
        //Ejecutar comando al RouterBoard
        con.execute("/ip/hotspot/user/add name='" + UsuarioGen + "' limit-uptime='" + TiempoMinutos + "' profile='" + Plan + "' password='" + ContraseñaGen + "'"); // execute a command
    }

}
