-- WARNING
--
-- This file was created by an automatic converter from *SQL to PostgreSQL.
-- Instructions in comments below may not apply, because they refer to a
-- different flavour of SQL.

SET search_path TO public;

-- --------------------------------------------------------
-- Installation der BIKE-Datenbank fuer Oracle-SQL 
--
--                      zum Starten unter SQL*PLUS ab Oracle V9
--                              und ab Access 2002     
--                         E. Schicker, August 2008
-- --------------------------------------------------------

-- TERMOUT ON
-- Installation der BIKE-Datenbank. Bitte etwas Geduld
-- TERMOUT OFF
-- FEEDBACK OFF
--

-- Es werden eventuell noch existierende Tabellen geloescht
DROP TABLE IF EXISTS teilereservierung;
DROP TABLE IF EXISTS auftragsposten;
DROP TABLE IF EXISTS auftrag;
DROP TABLE IF EXISTS teilestruktur;
DROP TABLE IF EXISTS lager;
DROP TABLE IF EXISTS lieferung;
DROP TABLE IF EXISTS teilestamm;
DROP TABLE IF EXISTS personal;
DROP TABLE IF EXISTS kunde;
DROP TABLE IF EXISTS lieferant;

-- ----------------------------------
-- Anlegen der Tabellen:
-- ----------------------------------

CREATE TABLE lieferant (
     nr          INTEGER    CONSTRAINT pk_lieferant PRIMARY KEY,
     name        CHAR(30)   NOT NULL,
     strasse     CHAR(30),
     plz         INTEGER,
     ort         CHAR(25),
     sperre      CHAR (1)       
);

CREATE TABLE kunde (
     nr          INTEGER    CONSTRAINT pk_kunde PRIMARY KEY,
     name        CHAR(30)   NOT NULL,
     strasse     CHAR(30),
     plz         INTEGER,
     ort         CHAR(20),
     sperre      CHAR (1)       
);

CREATE TABLE personal (
     persnr      INTEGER    CONSTRAINT pk_personal PRIMARY KEY,
     name        CHAR(30)   NOT NULL,
     strasse     CHAR(30),
     plz         INTEGER,
     ort         CHAR(20),
     gebdatum    DATE       NOT NULL,
     stand       CHAR(6),
     vorgesetzt  INTEGER    CONSTRAINT fk_personal REFERENCES personal ON DELETE SET NULL,
     gehalt      NUMERIC(8,2),
     beurteilung CHAR(1),
     aufgabe     CHAR(18)
);


CREATE TABLE teilestamm (
     teilnr      INTEGER    CONSTRAINT pk_teilestamm PRIMARY KEY,
     bezeichnung CHAR(35)   NOT NULL,
     nettopreis  NUMERIC(7,2),
     steuer      NUMERIC(7,2),
     preis       NUMERIC(7,2),
     farbe       CHAR(10),
     mass        CHAR(15),
     einheit     CHAR(2)    NOT NULL,
     typ         CHAR(1)    NOT NULL
);

CREATE TABLE lager (
     teilnr     INTEGER    CONSTRAINT pk_lager PRIMARY KEY,
     lagerort    CHAR(6),
     bestand     SMALLINT   NOT NULL,
     mindbest    SMALLINT,
     reserviert  SMALLINT,
     bestellt    SMALLINT,
     CONSTRAINT fk_lager_teil FOREIGN KEY (teilnr) REFERENCES teilestamm ON DELETE CASCADE
);

CREATE TABLE lieferung (
     teilnr      INTEGER    CONSTRAINT fk_teilestamm REFERENCES teilestamm ON DELETE CASCADE,
     liefnr      INTEGER    CONSTRAINT fk_lieferant  REFERENCES lieferant  ON DELETE CASCADE,
     lieferzeit  SMALLINT,
     nettopreis  NUMERIC(7,2),
     bestellt    SMALLINT,
     CONSTRAINT pk_lieferung PRIMARY KEY (teilnr, liefnr)
);
     
CREATE TABLE teilestruktur (
     oberteilnr  INTEGER    CONSTRAINT fk_oteil_teil REFERENCES teilestamm ON DELETE CASCADE,
     einzelteilnr INTEGER   CONSTRAINT fk_eteil_teil REFERENCES teilestamm,
     anzahl      SMALLINT,
     einheit     CHAR(2),
     CONSTRAINT pk_teilestruktur PRIMARY KEY (oberteilnr, einzelteilnr)	
);

CREATE TABLE auftrag (
     auftrnr     INTEGER    CONSTRAINT pk_auftrag PRIMARY KEY,
     datum       DATE,
     kundnr      INTEGER    NOT NULL CONSTRAINT fk_auftrag_kunde REFERENCES kunde,
     persnr      INTEGER    CONSTRAINT fk_auftrag_personal REFERENCES personal ON DELETE SET NULL
);

CREATE TABLE auftragsposten (
     posnr       INTEGER    CONSTRAINT pk_auftragsposten PRIMARY KEY,
     auftrnr     INTEGER    NOT NULL CONSTRAINT fk_auftrag_auftrag REFERENCES auftrag ON DELETE CASCADE,
     teilnr     INTEGER    NOT NULL CONSTRAINT fk_auftrag_teil REFERENCES teilestamm,
     anzahl      SMALLINT,
     gesamtpreis NUMERIC(7,2),
     CONSTRAINT ak_auftragsposten UNIQUE (auftrnr, teilnr)
);

CREATE TABLE teilereservierung (
     posnr       INTEGER    CONSTRAINT fk_teil_auftrag REFERENCES auftragsposten,
     teilnr     INTEGER    CONSTRAINT fk_teil_teil REFERENCES teilestamm,
     anzahl      SMALLINT,
     CONSTRAINT pk_teilereservierung PRIMARY KEY (posnr, teilnr)
);


-- Jetzt werden die Tabellen mit Inhalt versorgt

INSERT INTO lieferant
  VALUES ( 1, 'Firma Gerti Schmidtner', 'Dr. Gesslerstr. 59'       ,  93051, 'Regensburg ', '0' );
INSERT INTO lieferant
  VALUES ( 2, 'Rauch GmbH'            , 'Burgallee 23'             ,  90403, 'Nürnberg '  , '0' );
INSERT INTO lieferant
  VALUES ( 3, 'Shimano GmbH'          , 'Rosengasse 122'           ,  51143, 'Köln'       , '0' );
INSERT INTO lieferant
  VALUES ( 4, 'Suntour LTD'           , 'Meltonstreet 65'          ,   NULL, 'London'     , '0' );
INSERT INTO lieferant
  VALUES ( 5, 'MSM GmbH'              , 'St-Rotteneckstr. 13'      ,  93047, 'Regensburg' , '0' );

INSERT INTO kunde
  VALUES ( 1, 'Fahrrad Shop'          , 'Obere Regenstr. 4'        ,  93059, 'Regensburg' , '0' );
INSERT INTO kunde
  VALUES ( 2, 'Zweirad-Center Staller', 'Kirschweg 20'             ,  44267, 'Dortmund'   , '0' );
INSERT INTO kunde
  VALUES ( 3, 'Maier Ingrid'          , 'Universitätsstr. 33'      ,  93055, 'Regensburg' , '1' );
INSERT INTO kunde
  VALUES ( 4, 'Rafa - Seger KG'       , 'Liebigstr. 10'            ,  10247, 'Berlin'     , '0' );
INSERT INTO kunde
  VALUES ( 5, 'Biker Ecke'            , 'Lessingstr. 37'           ,  22087, 'Hamburg'    , '0' );
INSERT INTO kunde
  VALUES ( 6, 'Fahrräder Hammerl'     , 'Schindlerplatz 7'         ,  81739, 'München'    , '0' );

INSERT INTO personal
  VALUES ( 1, 'Maria Forster'   , 'Ebertstr. 28'     , 93051, 'Regensburg', DATE '1979-07-05', 'verh', NULL, 4800.00 , '2' , 'Manager'    );
INSERT INTO personal
  VALUES ( 2, 'Anna Kraus'      , 'Kramgasse 5'      , 93047, 'Regensburg', DATE '1975-07-09', 'led' , 1   , 2300.00 , '3' , 'Vertreter'  );
INSERT INTO personal
  VALUES ( 4, 'Heinz Rolle'     , 'In der Au 5'      , 90455, 'Nürnberg'  , DATE '1957-10-12', 'led' , 1   , 3300.00 , '3' , 'Sekretär'   );
INSERT INTO personal
  VALUES ( 5, 'Johanna Köster'  , 'Wachtelstr. 7'    , 90427, 'Nürnberg'  , DATE '1984-02-07', 'ges' , 1   , 2100.00 , '5' , 'Vertreter'  );
INSERT INTO personal
  VALUES ( 6, 'Marianne Lambert', 'Fraunhofer Str 3' , 92224, 'Landshut'  , DATE '1974-05-22', 'verh', NULL, 4100.00 , '1' , 'Meister'    );
INSERT INTO personal
  VALUES ( 3, 'Ursula Rank'     , 'Dreieichstr. 12'  , 60594, 'Frankfurt' , DATE '1967-09-04', 'verh', 6   , 2700.00 , '1' , 'Facharbeiterin' );
INSERT INTO personal
  VALUES ( 7, 'Thomas Noster'   , 'Mahlergasse 10'   , 93047, 'Regensburg', DATE '1972-09-17', 'verh', 6   , 2500.00 , '5' , 'Arbeiter'   );
INSERT INTO personal
  VALUES ( 8, 'Renate Wolters'  , 'Lessingstr. 9'    , 86159, 'Augsburg'  , DATE '1979-07-14', 'led' , 1   , 3300.00 , '4' , 'Sachbearbeiterin'  );
INSERT INTO personal
  VALUES ( 9, 'Ernst Pach'      , 'Olgastr. 99'      , 70180, 'Stuttgart' , DATE '1992-03-29', 'led' , 6   , 800.00  , NULL, 'Azubi'      );

INSERT INTO teilestamm
  VALUES ( 100001, 'Herren-City-Rad'                , 588.24, 111.76, 700.00, 'blau',    '26 Zoll', 'ST', 'E');
INSERT INTO teilestamm
  VALUES ( 100002, 'Damen-City-Rad'                 , 546.22, 103.78, 650.00, 'rot',     '26 Zoll', 'ST', 'E');
INSERT INTO teilestamm
  VALUES ( 200001, 'Herren-City-Rahmen lackiert'    , 336.13,  63.87, 400.00, 'blau',    NULL     , 'ST', 'Z');
INSERT INTO teilestamm
  VALUES ( 200002, 'Damen-City-Rahmen lackiert'     , 336.13,  63.87, 400.00, 'rot',     NULL     , 'ST', 'Z');    
INSERT INTO teilestamm
  VALUES ( 300001, 'Herren-City-Rahmen geschweisst' , 310.92,  59.08, 370.00, NULL,      NULL     , 'ST', 'Z');
INSERT INTO teilestamm
  VALUES ( 300002, 'Damen-City-Rahmen geschweisst'  , 310.92,  59.08, 370.00, NULL,      NULL     , 'ST', 'Z');
INSERT INTO teilestamm
  VALUES ( 400001, 'Rad'                            ,  58.82,  11.18,  70.00, NULL,      '26 Zoll', 'ST', 'Z');
INSERT INTO teilestamm
  VALUES ( 500001, 'Rohr 25CrMo4 9mm'               ,   6.30,   1.20,   7.50, NULL,      '9mm'    , 'CM', 'F'); 
INSERT INTO teilestamm
  VALUES ( 500002, 'Sattel'                         ,  42.02,   7.98,  50.00, NULL,      NULL     , 'ST', 'F');
INSERT INTO teilestamm
  VALUES ( 500003, 'Gruppe Deore LX'                ,   5.88,   1.12,   7.00, NULL,      'LX'     , 'ST', 'F');
INSERT INTO teilestamm
  VALUES ( 500004, 'Gruppe Deore XT'                ,   5.04,   0.96,   6.00, NULL,      'XT'     , 'ST', 'F');
INSERT INTO teilestamm
  VALUES ( 500005, 'Gruppe XC-LTD'                  ,   6.72,   1.28,   8.00, NULL,      'XC-LTD' , 'ST', 'F');
INSERT INTO teilestamm
  VALUES ( 500006, 'Felgensatz'                     ,  33.61,   6.39,  40.00, NULL,      '26 Zoll', 'ST', 'F');
INSERT INTO teilestamm
  VALUES ( 500007, 'Bereifung Schwalbe'             ,  16.81,   3.19,  20.00, NULL,      '26 Zoll', 'ST', 'F');
INSERT INTO teilestamm
  VALUES ( 500008, 'Lenker + Vorbau'                ,  78.99,  15.01,  94.00, NULL,      NULL     , 'ST', 'F');
INSERT INTO teilestamm
  VALUES ( 500009, 'Sattelstuetze'                  ,   4.62,   0.88,   5.50, NULL,      NULL     , 'ST', 'F');
INSERT INTO teilestamm
  VALUES ( 500010, 'Pedalsatz'                      ,  33.61,   6.39,  40.00, NULL,      NULL     , 'ST', 'F');
INSERT INTO teilestamm
  VALUES ( 500011, 'Rohr 34CrMo4 2.1mm'             ,   3.36,   0.64,   4.00, NULL,      '2,1mm'  , 'CM', 'F');
INSERT INTO teilestamm
  VALUES ( 500012, 'Rohr 34CrMo3 2.4mm'             ,   3.61,   0.69,   4.30, NULL,      '2,4mm'  , 'CM', 'F');
INSERT INTO teilestamm
  VALUES ( 500013, 'Tretlager'                      ,  25.21,   4.79,  30.00, NULL,      NULL     , 'ST', 'F');
INSERT INTO teilestamm
  VALUES ( 500014, 'Gabelsatz'                      ,  10.08,   1.92,  12.00, NULL,      NULL     , 'ST', 'F');
INSERT INTO teilestamm
  VALUES ( 500015, 'Schlauch'                       ,   6.72,   1.28,   8.00, NULL,      '26 Zoll', 'ST', 'F');

INSERT INTO lager                      
  VALUES ( 100001, '001002',    1,    0,    2,    0 );         
INSERT INTO lager
  VALUES ( 100002, '001001',    6,    0,    3,    0 );
INSERT INTO lager
  VALUES ( 200001, NULL    ,    0,    0,    0,    0 );
INSERT INTO lager
  VALUES ( 200002, '004004',    2,    0,    0,    0 );
INSERT INTO lager
  VALUES ( 300001, NULL    ,    0,    0,    0,    0 );
INSERT INTO lager
  VALUES ( 300002, '002001',    7,    0,    2,    0 );
INSERT INTO lager
  VALUES ( 500001, '003005', 8050, 6000,  184,    0 );
INSERT INTO lager
  VALUES ( 500002, '002002',   19,   20,    2,   10 );
INSERT INTO lager
  VALUES ( 500003, '001003',   15,   10,    0,    0 );
INSERT INTO lager
  VALUES ( 500004, '004001',   18,   10,    0,    0 );
INSERT INTO lager
  VALUES ( 500005, '003002',    2,    0,    0,    0 );
INSERT INTO lager
  VALUES ( 400001, '005001',    1,    0,    0,    0 );
INSERT INTO lager
  VALUES ( 500006, '003004',   21,   20,    0,    0 );
INSERT INTO lager
  VALUES ( 500007, '002003',   62,   40,    0,    0 );
INSERT INTO lager
  VALUES ( 500008, '003003',   39,   20,    1,    0 );
INSERT INTO lager
  VALUES ( 500009, '002007',   23,   20,    0,    0 );
INSERT INTO lager
  VALUES ( 500010, '001006',   27,   20,    1,    0 );
INSERT INTO lager
  VALUES ( 500011, '001007', 3250, 3000,  161,    0 );
INSERT INTO lager
  VALUES ( 500012, '004002',  720,  600,   20,    0 );
INSERT INTO lager
  VALUES ( 500013, '005002',   20,   20,    2,    0 );
INSERT INTO lager
  VALUES ( 500014, '005003',   27,   20,    1,    0 );
INSERT INTO lager
  VALUES ( 500015, '002004',   55,   40,    0,    0 );

INSERT INTO teilestruktur
  VALUES ( 100001, 200001,   1 , 'ST' );      
INSERT INTO teilestruktur
  VALUES ( 100001, 500002,   1 , 'ST' );
INSERT INTO teilestruktur
  VALUES ( 100001, 500003,   1 , 'ST' );
INSERT INTO teilestruktur
  VALUES ( 100001, 400001,   1 , 'ST' );
INSERT INTO teilestruktur
  VALUES ( 100001, 500008,   1 , 'ST' );
INSERT INTO teilestruktur
  VALUES ( 100001, 500009,   1 , 'ST' );
INSERT INTO teilestruktur
  VALUES ( 100001, 500010,   1 , 'ST' );
INSERT INTO teilestruktur
  VALUES ( 100002, 200002,   1 , 'ST' );
INSERT INTO teilestruktur
  VALUES ( 100002, 500002,   1 , 'ST' );
INSERT INTO teilestruktur
  VALUES ( 100002, 500004,   1 , 'ST' );
INSERT INTO teilestruktur
  VALUES ( 100002, 400001,   1 , 'ST' );
INSERT INTO teilestruktur
  VALUES ( 100002, 500008,   1 , 'ST' );
INSERT INTO teilestruktur
  VALUES ( 100002, 500009,   1 , 'ST' );
INSERT INTO teilestruktur
  VALUES ( 100002, 500010,   1 , 'ST' );
INSERT INTO teilestruktur
  VALUES ( 200001, 300001,   1 , 'ST' );
INSERT INTO teilestruktur
  VALUES ( 200002, 300002,   1 , 'ST' );
INSERT INTO teilestruktur
  VALUES ( 300001, 500001, 180 , 'CM' );
INSERT INTO teilestruktur
  VALUES ( 300001, 500011, 161 , 'CM' );
INSERT INTO teilestruktur
  VALUES ( 300001, 500012,  20 , 'CM' );
INSERT INTO teilestruktur
  VALUES ( 300001, 500013,   1 , 'ST' );
INSERT INTO teilestruktur
  VALUES ( 300001, 500014,   1 , 'ST' );
INSERT INTO teilestruktur
  VALUES ( 300002, 500001, 360 , 'CM' );
INSERT INTO teilestruktur
  VALUES ( 300002, 500011, 106 , 'CM' );
INSERT INTO teilestruktur
  VALUES ( 300002, 500012,  20 , 'CM' );
INSERT INTO teilestruktur
  VALUES ( 300002, 500013,   1 , 'ST' );
INSERT INTO teilestruktur
  VALUES ( 300002, 500014,   1 , 'ST' );
INSERT INTO teilestruktur
  VALUES ( 400001, 500007,   2 , 'ST' );
INSERT INTO teilestruktur
  VALUES ( 400001, 500006,   1 , 'ST' );
INSERT INTO teilestruktur
  VALUES ( 400001, 500015,   2 , 'ST' );

INSERT INTO lieferung
  VALUES ( 500001, 5, 1,   5.50, 0);
INSERT INTO lieferung
  VALUES ( 500002, 2, 4,  36.60, 10);
INSERT INTO lieferung
  VALUES ( 500002, 1, 5,  35.10, 0);
INSERT INTO lieferung
  VALUES ( 500003, 3, 6,   5.60, 0);
INSERT INTO lieferung
  VALUES ( 500003, 4, 5,   5.45, 0);
INSERT INTO lieferung
  VALUES ( 500004, 3, 2,   4.70, 0);
INSERT INTO lieferung
  VALUES ( 500004, 4, 3,   4.50, 0);
INSERT INTO lieferung
  VALUES ( 500005, 4, 5,   5.70, 0);
INSERT INTO lieferung
  VALUES ( 500006, 1, 1,  26.00, 0);
INSERT INTO lieferung
  VALUES ( 500007, 1, 2,  15.50, 0);
INSERT INTO lieferung
  VALUES ( 500008, 1, 4,  73.00, 0);
INSERT INTO lieferung
  VALUES ( 500009, 1, 2,   4.00, 0);
INSERT INTO lieferung
  VALUES ( 500009, 2, 1,   4.30, 0);
INSERT INTO lieferung
  VALUES ( 500010, 1, 3,  29.90, 0);
INSERT INTO lieferung
  VALUES ( 500011, 5, 1,   2.60, 0);
INSERT INTO lieferung
  VALUES ( 500012, 5, 1,   3.20, 0);
INSERT INTO lieferung
  VALUES ( 500013, 1, 4,  21.00, 0);
INSERT INTO lieferung
  VALUES ( 500014, 1, 5,   9.00, 0);
INSERT INTO lieferung
  VALUES ( 500015, 1, 1,   6.00, 0);

INSERT INTO auftrag
  VALUES ( 1, DATE '2008-08-04', 1, 2);
INSERT INTO auftrag
  VALUES ( 2, DATE '2008-09-06', 3, 5);
INSERT INTO auftrag
  VALUES ( 3, DATE '2008-10-07', 4, 2);
INSERT INTO auftrag
  VALUES ( 4, DATE '2008-10-18', 6, 5);
INSERT INTO auftrag
  VALUES ( 5, DATE '2008-10-22', 4, 2);
INSERT INTO auftrag
  VALUES ( 6, DATE '2008-11-03', 1, 2);

INSERT INTO auftragsposten
  VALUES ( 11, 1, 200002, 2,  800.00);
INSERT INTO auftragsposten
  VALUES ( 21, 2, 100002, 3, 1950.00);
INSERT INTO auftragsposten
  VALUES ( 22, 2, 200001, 1,  400.00);
INSERT INTO auftragsposten
  VALUES ( 31, 3, 100001, 1,  700.00);
INSERT INTO auftragsposten
  VALUES ( 32, 3, 500002, 2,  100.00);
INSERT INTO auftragsposten
  VALUES ( 41, 4, 100001, 1,  700.00);
INSERT INTO auftragsposten
  VALUES ( 42, 4, 500001, 4,   30.00);
INSERT INTO auftragsposten
  VALUES ( 43, 4, 500008, 1,   94.00);
INSERT INTO auftragsposten
  VALUES ( 51, 5, 500006, 20,   800.00);
INSERT INTO auftragsposten
  VALUES ( 52, 5, 500007, 42,   840.00);
INSERT INTO auftragsposten
  VALUES ( 53, 5, 500008, 9,   846.00);
INSERT INTO auftragsposten
  VALUES ( 61, 6, 500008, 22,   2068.00);
INSERT INTO auftragsposten
  VALUES ( 62, 6, 500010, 25,   1000.00);
INSERT INTO auftragsposten
  VALUES ( 63, 6, 500013, 20,   600.00);

INSERT INTO teilereservierung
  VALUES ( 11, 300002,   2 );
INSERT INTO teilereservierung
  VALUES ( 21, 100002,   3 );
INSERT INTO teilereservierung
  VALUES ( 22, 500001, 180 );
INSERT INTO teilereservierung
  VALUES ( 22, 500011, 161 );
INSERT INTO teilereservierung
  VALUES ( 22, 500012,  20 );
INSERT INTO teilereservierung
  VALUES ( 22, 500013,   1 );
INSERT INTO teilereservierung
  VALUES ( 22, 500014,   1 );
INSERT INTO teilereservierung
  VALUES ( 31, 100001,   1 );
INSERT INTO teilereservierung
  VALUES ( 32, 500002,   2 );
INSERT INTO teilereservierung
  VALUES ( 41, 100001,   1 );
INSERT INTO teilereservierung
  VALUES ( 42, 500001,   4 );
INSERT INTO teilereservierung
  VALUES ( 43, 500008,   1 );
INSERT INTO teilereservierung
  VALUES ( 51, 500010,   1 );
INSERT INTO teilereservierung
  VALUES ( 52, 500013,   1 );

COMMIT;

-- TERMOUT ON
-- FEEDBACK ON

-- Installation der Beispieldatenbank BIKE ist abgeschlossen
