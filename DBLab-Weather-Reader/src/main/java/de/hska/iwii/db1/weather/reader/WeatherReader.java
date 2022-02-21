package de.hska.iwii.db1.weather.reader;

import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.hska.iwii.db1.weather.model.Location;
import de.hska.iwii.db1.weather.model.Weather;
import de.hska.iwii.db1.weather.model.WeatherContainer;

/**
 * Reader zum Lesen und Parsen der Wetterdaten.
 */
public class WeatherReader {

	private static String BASE_URL = "https://www.metaweather.com/api/location";
	private static String SEARCH_PATH = "/search/?query=";
	
	private ObjectMapper objectMapper = new ObjectMapper();
	private HttpClient client;
	
	/**
	 * Erzeugt den Parser fuer die Verwendung ohne Proxy-Server.
	 */
	public WeatherReader() {
		client = HttpClient.newBuilder()
				.version(Version.HTTP_2)
				.followRedirects(Redirect.ALWAYS)
				.build();
	}

	/**
	 * Erzeugt den Parser fuer die Verwendung mit authentifizierendem Proxy-Server.
	 * @param proxyAddress Adresse des Proxy-Servers (proxy.hs-karlsruhe.de im
	 * 			Fall der Hochschule Karlsruhe).
	 * @param proxyPort Port des Proxy-Servers (8888 im
	 * 			Fall der Hochschule Karlsruhe).
	 * @param proxyUser IZ-Nutzername.
	 * @param proxyPassword IZ-Passwort.
	 * 
	 */
	public WeatherReader(String proxyAddress, int proxyPort, String proxyUser, String proxyPassword) {
		System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");
		Authenticator.setDefault(new ProxyAuthenticator(proxyUser, proxyPassword));
		client = HttpClient.newBuilder()
				.version(Version.HTTP_2)
				.followRedirects(Redirect.ALWAYS)
				.proxy(ProxySelector.of(new InetSocketAddress(proxyAddress, proxyPort)))
				.authenticator(new ProxyAuthenticator(proxyUser, proxyPassword))
				.build();
	}

	/**
	 * Liest die JSON-Daten ueber eine URL aus.
	 * @param path Pfad, der an die Basis-URL angehaengt wird.
	 * @return JSON-Daten oder eine leere Zeichenkette im Fehlerfall.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private String readData(String path) throws IOException, InterruptedException {
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(BASE_URL + path))
				.build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		return response.statusCode() == 200 ? response.body() : "";
	}

	/**
	 * Sucht eine Ortsangabe anhand ihres Namens.
	 * @param name Name der Ortsangabe wie z.B. "Stuttgart"
	 * @return Alle Ortsangaben zu diesem Namen. Gibt es keinen
	 * 			Ort mit dem Namen oder tritt ein Fehler auf, dann
	 * 			ist das Array leer.
	 */
	public Location[] findLocation(String name) {
		try {
			String loc = readData(SEARCH_PATH + URLEncoder.encode(name, Charset.defaultCharset()));
			if (loc.length() > 0) {
				return objectMapper.readValue(loc, Location[].class);
			}
		} catch (IOException | InterruptedException e) {
		}
		return new Location[0];
	}

	/**
	 * Liest das aktuelle Wetter an einem Ort aus.
	 * @param locationId ID des Ortes.
	 * @return Aktuelles Wetten an dem Ort oder <code>null</code>.
	 * 			Im Fall eines Fehlers wird ebenfalls <code>null</code>
	 * 			zurueckgegeben.
	 */
	public Weather readCurrentWeather(long locationId) {
		try {
			String weather = readData("/" + locationId);
			if (weather.length() > 0) {
				WeatherContainer weatherInfo = objectMapper.readValue(weather, WeatherContainer.class);
				return weatherInfo != null ? weatherInfo.getWeather()[0] : null;
			}
		} catch (IOException | InterruptedException e) {
		}
		return null;
	}
}
