-- 2.1
SELECT auftrnr, datum FROM auftrag WHERE kundnr IN (SELECT nr FROM kunde WHERE name = 'Fahrrad Shop');

-- 2.2
SELECT auftrnr, datum FROM auftrag WHERE kundnr =SOME (SELECT nr FROM kunde WHERE name = 'Fahrrad Shop');

-- 2.3
SELECT auftrnr, datum FROM auftrag WHERE EXISTS (SELECT nr FROM kunde WHERE kunde.nr=auftrag.kundnr AND name = 'Fahrrad Shop');

-- 2.4
SELECT kundnr, COUNT(kundnr) AS anzahl, MIN(datum) AS von, MAX(datum) AS bis FROM auftrag GROUP BY kundnr ORDER BY kundnr ASC;

-- 2.5
SELECT kundnr, COUNT(kundnr) AS anzahl, MIN(datum) AS von, MAX(datum) AS bis FROM auftrag GROUP BY kundnr HAVING COUNT(kundnr) <= 1 ORDER BY kundnr ASC;

-- 2.6
SELECT kunde.nr, kunde.name, auftrag.auftrnr FROM kunde INNER JOIN auftrag ON kunde.nr = auftrag.kundnr ORDER BY kunde.nr;

-- 2.7
SELECT kunde.nr, kunde.name, auftrag.auftrnr, personal.name FROM kunde INNER JOIN auftrag ON kunde.nr = auftrag.kundnr INNER JOIN personal ON personal.persnr = auftrag.persnr ORDER BY kunde.nr;

-- 2.8
SELECT kunde.name, COUNT(auftrag.kundnr) AS anzahl FROM kunde INNER JOIN auftrag ON kunde.nr = auftrag.kundnr GROUP BY kunde.name ORDER BY anzahl DESC LIMIT 1;

-- 2.9
WITH bestekunden(name, anzahl) AS (SELECT kunde.name, COUNT(auftrag.kundnr) AS anzahl FROM kunde INNER JOIN auftrag ON kunde.nr = auftrag.kundnr GROUP BY kunde.name ORDER BY anzahl DESC) SELECT * FROM bestekunden LIMIT 1;

-- 2.10
CREATE VIEW KundenUmsatz AS SELECT kunde.name, SUM(auftragsposten.gesamtpreis) FROM auftragsposten INNER JOIN auftrag ON auftrag.auftrnr=auftragsposten.auftrnr INNER JOIN kunde ON kunde.nr = auftrag.kundnr GROUP BY kunde.name;

-- 2.11
DROP VIEW KundenUmsatz;

