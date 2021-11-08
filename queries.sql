-- 1.1
SELECT * FROM teilestamm;

-- 1.2
SELECT * FROM teilestamm WHERE bezeichnung ILIKE '%city%';

-- 1.3
SELECT posnr, auftrnr, teilnr, anzahl*gesamtpreis AS umsatz FROM Auftragsposten ORDER BY umsatz DESC LIMIT 1;

-- 1.4
SELECT COUNT(kunde) FROM kunde;
SELECT COUNT(personal) FROM personal;
SELECT COUNT(teilestamm) FROM teilestamm;

-- 1.5
SELECT MIN(datum) AS von, MAX(datum) AS bis FROM auftrag;

-- 1.6
SELECT name FROM kunde WHERE nr=(SELECT kundnr FROM auftrag WHERE auftrnr=2);
SELECT name FROM personal WHERE persnr=(SELECT persnr FROM auftrag WHERE auftrnr=2);
SELECT name FROM personal WHERE persnr=(SELECT vorgesetzt FROM personal WHERE persnr=(SELECT persnr FROM auftrag WHERE auftrnr=2));

-- 1.7
SELECT teilnr, bestand FROM lager WHERE bestand>0 ORDER BY bestand ASC;

-- 1.8

SELECT DISTINCT * FROM auftragsposten ORDER BY teilnr;

-- 1.9
SELECT teilnr AS teilenummer, bezeichnung, nettopreis, preis AS bruttopreis FROM teilestamm WHERE preis>30;

-- 1.10
SELECT einzelteilnr AS teilenummer FROM teilestruktur WHERE oberteilnr=300001 AND anzahl>100;

