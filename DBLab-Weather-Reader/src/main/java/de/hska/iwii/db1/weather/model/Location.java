package de.hska.iwii.db1.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Beschreibt einen Ort.
 */
public class Location {

	// Name des Ortes, z.B. "Hamburg"
	@JsonProperty("title")
	private String name;
	
	// Art des Ortes, z.B. "city"
	@JsonProperty("location_type")
	private String locationType;
	
	// ID des Ortes
	@JsonProperty("woeid")
	private long id;

	// Laengen- und Breitengrad
	@JsonProperty("latt_long")
	private String lattitudeLongitude;
	
	/**
	 * Liest den Namen des Ortes aus.
	 * @return Name des Ortes.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Traegt den Namen des Ortes ein.
	 * @param name Name des Ortes.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Liest die Art der Ortsangabe aus.
	 * @return Art der Ortsangabe, z.B. "city"
	 */
	public LocationType getLocationType() {
		return LocationType.valueOf(locationType.toUpperCase());
	}

	/**
	 * Traegt die art der Ortsangabe ein.
	 * @param locationType Art der Ortsangabe, z.B. "city"
	 */
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	
	/**
	 * Liest die ID des Ortes aus.
	 * @return ID des Ortes.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Traegt die ID des Ortes ein.
	 * @param id ID des Ortes.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Liest den Breitengrad des Ortes aus. 
	 * @return Breitengrad des Ortes.
	 */
	public float getLattitude() {
		return Float.parseFloat(lattitudeLongitude.split(",")[0]);
	}
	
	/**
	 * Liest den Laengengrad der Ortsangabe aus.
	 * @return Laengengrad der Ortsangabe.
	 */
	public float getLongitude() {
		return Float.parseFloat(lattitudeLongitude.split(",")[1]);
	}
	
	/**
	 * Traegt Laengen- und Breitengrad ein.
	 * @param lattitudeLongitude Laengen- und Breitengrad, durch Komma getrennt.
	 */
	public void setLattitudeLongitude(String lattitudeLongitude) {
		this.lattitudeLongitude = lattitudeLongitude;
	}

	@Override
	public String toString() {
		return "Location [name=" + name + ", locationType=" + getLocationType() + ", id=" + id + ", lattitude="
				+ getLattitude() + ", longitude=" + getLongitude() + "]";
	}
}
