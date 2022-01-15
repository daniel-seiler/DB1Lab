package de.hska.iwii.db1.weather.reader;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Die Klasse enthaelt die Anmeldeinformationen fuer den Proxy-Server.
 */
public class ProxyAuthenticator extends Authenticator {

    private PasswordAuthentication pwdAuth;
    
	/**
	 * Erzeugt den Authentocator fuer den Proxy.
	 * @param proxyUser Name des Proxy-Users.
	 * @param proxyPassword Password des Proxy-Users.
	 */
	public ProxyAuthenticator(String proxyUser, String proxyPassword) {
        pwdAuth = new PasswordAuthentication(proxyUser, proxyPassword.toCharArray());
    }

    /**
     * Liefert die Anmeldeinformationen fuer den Proxy-Server zurueck.
     * @return Anmeldeinformationen fuer den Proxy-Server.
     */
    @Override
	protected PasswordAuthentication getPasswordAuthentication() {
        return pwdAuth;
    }
}
