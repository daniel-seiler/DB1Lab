package de.hska.iwii.db1.weather;

import de.hska.iwii.db1.weather.model.Location;
import de.hska.iwii.db1.weather.model.Weather;
import de.hska.iwii.db1.weather.reader.WeatherReader;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

public class Main {
    
    public static void main(String [] args) throws SQLException {
        String url = "jdbc:postgresql://datenbanken1.ddns.net:3690/g20";
        String username = "g20";
        String password = "XfgZfBTstd";
        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);
        Connection connection = DriverManager.getConnection(url, props);
        
        WeatherReader reader = new WeatherReader();
        ArrayList<Location> locations = new ArrayList<>();
        Collections.addAll(locations, reader.findLocation("Berlin"));
        Collections.addAll(locations, reader.findLocation("Bremen"));
        Collections.addAll(locations, reader.findLocation("Cologne"));
        Collections.addAll(locations, reader.findLocation("Dortmund"));
        Collections.addAll(locations, reader.findLocation("Dresden"));
        Collections.addAll(locations, reader.findLocation("Düsseldorf"));
        Collections.addAll(locations, reader.findLocation("Essen"));
        Collections.addAll(locations, reader.findLocation("Frankfurt"));
        Collections.addAll(locations, reader.findLocation("Hamburg"));
        Collections.addAll(locations, reader.findLocation("Stuttgart"));
        
        String insertLocation = "INSERT INTO location(woeid, " +
                                        "title, " +
                                        "location_type, " +
                                        "latitude, " +
                                        "longitude)" +
                                        "VALUES(?, ?, ?, ?, ?)";

        for (Location location: locations) {
            System.out.println(location);
            PreparedStatement statementLocation = connection.prepareStatement(insertLocation);
            statementLocation.setLong(1, location.getId());
            statementLocation.setString(2, location.getName());
            statementLocation.setString(3, location.getLocationType().toString());
            statementLocation.setFloat(4, location.getLattitude());
            statementLocation.setFloat(5, location.getLongitude());
            statementLocation.execute();
        }
    
        PreparedStatement statementLocation = connection.prepareStatement("SELECT * FROM location");
        ResultSet rsLocations = statementLocation.executeQuery();
        locations.clear();
        while(rsLocations.next()) {
            Location tmpL = new Location();
            tmpL.setId((long) rsLocations.getFloat("woeid"));
            tmpL.setName(rsLocations.getString("title"));
            tmpL.setLocationType(rsLocations.getString("location_type"));;
            tmpL.setLattitudeLongitude(rsLocations.getFloat("latitude") + "," + rsLocations.getFloat("longitude"));
            locations.add(tmpL);
        }
        
        String insertWeather = "INSERT INTO weather(" +
                                       "id, " +
                                       "weather_state_name, " +
                                       "applicable_date, " +
                                       "min_temp, " +
                                       "max_temp, " +
                                       "air_pressure, " +
                                       "humidity, " +
                                       "location_id) " +
                                       "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    
        PreparedStatement statementWeather = connection.prepareStatement(insertWeather);
        for (Location location: locations) {
            Weather weather = reader.readCurrentWeather(location.getId());
            if (weather != null) {
                if (weather != null) {
                    System.out.println(weather);
                    statementWeather.setFloat(1, weather.getId());
                    statementWeather.setString(2, weather.getWeatherStateName());
                    statementWeather.setDate(3,  new java.sql.Date(((java.util.Date) weather.getApplicableDate()).getTime()));
                    statementWeather.setFloat(4, weather.getMinTemp());
                    statementWeather.setFloat(5, weather.getMaxTemp());
                    statementWeather.setInt(6, weather.getAirPressure());
                    statementWeather.setInt(7, weather.getHumidity());
                    statementWeather.setFloat(8, location.getId());
                    statementWeather.addBatch();
                }
            }
        }
        statementWeather.executeBatch();
        
        int minHumidity = 80;
        int maxHumidity = 90;
        float minTemp = 1;
        String queryLocation = "SELECT * FROM location WHERE woeid IN " +
                                       "(SELECT location_id FROM weather WHERE " +
                                       "humidity > ? AND " +
                                       "humidity < ? AND " +
                                       "min_temp > ?)";
        PreparedStatement specificLocation = connection.prepareStatement(queryLocation);
        specificLocation.setInt(1, minHumidity);
        specificLocation.setInt(2, maxHumidity);
        specificLocation.setFloat(3, minTemp);
        ResultSet rsSpecificLocations = specificLocation.executeQuery();
        while(rsSpecificLocations.next()) {
            Location tmpL = new Location();
            tmpL.setId((long) rsSpecificLocations.getFloat("woeid"));
            tmpL.setName(rsSpecificLocations.getString("title"));
            tmpL.setLocationType(rsSpecificLocations.getString("location_type"));;
            tmpL.setLattitudeLongitude(rsSpecificLocations.getFloat("latitude") + "," + rsSpecificLocations.getFloat("longitude"));
            System.out.println(tmpL);
        }
        
        connection.close();
    }
}
