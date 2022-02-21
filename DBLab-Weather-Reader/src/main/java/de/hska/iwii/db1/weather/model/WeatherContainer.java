package de.hska.iwii.db1.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Basis-Container fuer die Wetter-Information eines Ortes.
 */
@JsonIgnoreProperties(value = {"time", "sun_rise", "sun_set", "timezone_name", "parent", "sources",
								"title", "location_type", "woeid", "latt_long", "timezone"})
public class WeatherContainer {

	@JsonProperty("consolidated_weather")
	private Weather[] weather;

	/**
	 * Liest die Vorhersagedaten aus.
	 * @return Vorhersagedaten.
	 */
	public Weather[] getWeather() {
		return weather;
	}

	/**
	 * Traegt die Vorhersagedaten ein.
	 * @param weather Vorhersagedaten.
	 */
	public void setWeather(Weather[] weather) {
		this.weather = weather;
	}
}
