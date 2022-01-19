DROP TABLE IF EXISTS location CASCADE;
DROP TABLE IF EXISTS weather CASCADE;

CREATE TABLE location (
    woeid REAL PRIMARY KEY NOT NULL,
    title TEXT NOT NULL,
    location_type TEXT NOT NULL,
	latitude REAL NOT NULL,
	longitude REAL NOT NULL
);

CREATE TABLE weather (
    id REAL PRIMARY KEY NOT NULL,
    weather_state_name TEXT NOT NULL,
    applicable_date DATE NOT NULL,
    min_temp REAL NOT NULL,
    max_temp REAL NOT NULL,
    air_pressure INTEGER NOT NULL,
    humidity INTEGER NOT NULL,
    location_id REAL REFERENCES location (woeid) NOT NULL
);

/*INSERT INTO location(woeid, title, location_type, lattitude, longitude)
VALUES(2459115, 'New York', 'CITY', 40.71455, -74.00712);

INSERT INTO weather(id, weather_state_name, applicable_date, min_temp, max_temp, air_pressure, humidity, location_id)
VALUES(5122920264237056, 'Heavy Cloud', 'Jan 15, 2022', -9.18, -3.695, 1024, 30, 2459115);
*/

