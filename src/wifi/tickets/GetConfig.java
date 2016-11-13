/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wifi.tickets;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author ACER-PCd
 */
public class GetConfig {

    private static final Properties config;

    static {
        Properties fallback = new Properties();
        fallback.put("key", "default");
        config = new Properties(fallback);
        try {
            InputStream stream = new FileInputStream("config.properties");
            try {
                config.load(stream);
            } finally {
                stream.close();
            }
        } catch (IOException ex) {
            /* Handle exception. */
        }
    }

    public String GetRbUsuario() {
        String username = config.getProperty("RbUsuario");
        return username;
    }

    public String RbContra() {
        String username = config.getProperty("RbContra");
        return username;
    }

    public String RbIP() {
        String username = config.getProperty("RbIP");
        return username;
    }

    public String DbUsuario() {
        String username = config.getProperty("DbUsuario");
        return username;
    }

    public String DbContra() {
        String username = config.getProperty("DbContra");
        return username;
    }

    public String DbIP() {
        String username = config.getProperty("DbIP");
        return username;
    }

}
