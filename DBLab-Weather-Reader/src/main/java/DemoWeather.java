
import java.io.IOException;

import de.hska.iwii.db1.weather.model.Location;
import de.hska.iwii.db1.weather.model.Weather;
import de.hska.iwii.db1.weather.reader.WeatherReader;

/**
 * Demo-Klasse fuer den Zugriff auf das Wetter
 * der Stadt New York. 
 */
public class DemoWeather {

	public static void main(String[] args) throws IOException {
		// 1. Erzeugt ein WeatherReader-Objekt fuer die komplette
		//    Serverkommunikation. Fuer den Zugriff uber den
		//    Proxy der Hochschule muss der zweite Konstruktur mit
		//    den Proxy-Parametern verwendet werden.
		WeatherReader reader = new WeatherReader();
		
		// 2. Auslesen von Informationen ueber einen oder mehrere Orte.
		//    Weitere Beispiele: Munic, Berlin, Frankfurt, Hamburg, Cologne,
		//    Dresden, Stuttgart
		Location[] locations = reader.findLocation("New York");
		for (Location location: locations) {
			System.out.println(location);
			// 3. Auslesen des aktuellen Wetters an dem ersten gefundenen Ort.
			Weather weather = reader.readCurrentWeather(location.getId());
			if (weather != null) {
				System.out.println(weather);
			}
		}
	}
}
