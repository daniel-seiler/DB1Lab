package de.hska.iwii.db1.weather.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wetterangabe fuer einen Tag
 */
@JsonIgnoreProperties(value = {"created", "visibility", "predictability", "the_temp",
								"weather_state_abbr", "wind_direction", "wind_direction_compass",
								"wind_speed"})
public class Weather {

	// ID der Wetterangabe.
	private long id;

	// Englischsprachige Beschreibung des Wetters. 
	@JsonProperty("weather_state_name")
	private String weatherStateName;
	
	// Datum, fuer das die Vorhersage gueltig ist.
	@JsonProperty("applicable_date")
	private Date applicableDate;
	
	// Tagestiefstemperatur.
	@JsonProperty("min_temp")
	private float minTemp;

	// Tageshoechstemperatur.
	@JsonProperty("max_temp")
	private float maxTemp;
	
	// Luftdruck in mbar 
	@JsonProperty("air_pressure")
	private int airPressure;
	
	// Relative Luftfeuchtigkeit
	private int humidity;
	
	/**
	 * Liest die ID der Wetterangabe aus.
	 * @return ID der Wetterangabe.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Traegt die ID der Wetterangabe ein.
	 * @param id ID der Wetterangabe.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Liest die englischsprachige, textuelle Beschreibung des Wetters aus.
	 * @return Englischsprachige, textuelle Beschreibung des Wetters.
	 */
	public String getWeatherStateName() {
		return weatherStateName;
	}

	/**
	 * Traegt die englischsprachige, textuelle Beschreibung des Wetters ein.
	 * @param weatherStateName Englischsprachige, textuelle Beschreibung des Wetters.
	 */
	public void setWeatherStateName(String weatherStateName) {
		this.weatherStateName = weatherStateName;
	}
	
	/**
	 * Liest das Datum aus, fuer das diese Wetterangabe gueltig ist.
	 * @return Datum, fuer das diese Wetterangabe gueltig ist.
	 */
	public Date getApplicableDate() {
		return applicableDate;
	}

	/**
	 * Traegt das Datum ein, fuer das diese Wetterangabe gueltig ist.
	 * @param applicableDate Datum, fuer das diese Wetterangabe gueltig ist.
	 */
	public void setApplicableDate(Date applicableDate) {
		this.applicableDate = applicableDate;
	}
	
	/**
	 * Liest die Tagestieftstemperatur aus.
	 * @return Tagestieftstemperatur in Grad Celsius.
	 */
	public float getMinTemp() {
		return minTemp;
	}

	/**
	 * Traegt die Tagestieftstemperatur ein.
	 * @param minTemp Tagestieftstemperatur in Grad Celsius.
	 */
	public void setMinTemp(float minTemp) {
		this.minTemp = minTemp;
	}

	/**
	 * Liest die Tageshoechstemperatur aus.
	 * @return Tageshoechstemperatur in Grad Celsius.
	 */
	public float getMaxTemp() {
		return maxTemp;
	}

	/**
	 * Traegt die Tageshoechstemperatur ein.
	 * @param maxTemp Tageshoechstemperatur in Grad Celsius.
	 */
	public void setMaxTemp(float maxTemp) {
		this.maxTemp = maxTemp;
	}
	
	/**
	 * Liest den Luftdruck aus.
	 * @return Luftdruck in mbar.
	 */
	public int getAirPressure() {
		return airPressure;
	}

	/**
	 * Traegt den Luftdruck ein.
	 * @param airPressure Luftdruck in mbar.
	 */
	public void setAirPressure(int airPressure) {
		this.airPressure = airPressure;
	}
	
	/**
	 * Liest die relative Luftfeuchtigkeit aus.
	 * @return Relative Luftfeuchtigkeit.
	 */
	public int getHumidity() {
		return humidity;
	}

	/**
	 * Traegt die relative Luftfeuchtigkeit ein.
	 * @param humidity Relative Luftfeuchtigkeit.
	 */
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	@Override
	public String toString() {
		return "Weather [id=" + id + ", weatherStateName=" + weatherStateName
				+ ", applicableDate=" + SimpleDateFormat.getDateInstance().format(applicableDate)
				+ ", minTemp=" + minTemp + ", maxTemp=" + maxTemp
				+ ", airPressure=" + airPressure + ", humidity=" + humidity + "]";
	}
}
