-- 1.1
SELECT * FROM teilestamm;

-- 1.2
SELECT * FROM teilestamm WHERE bezeichnung LIKE '%City%';

-- 1.3 - overcomplicated
SELECT posnr, auftrnr, teilnr, anzahl*gesamtpreis as umsatz FROM Auftragsposten ORDER BY umsatz DESC LIMIT 1;

-- 1.4
SELECT COUNT(kunde) FROM kunde;
SELECT COUNT(personal) FROM personal;
SELECT COUNT(teilestamm) FROM teilestamm;

-- 1.5
SELECT MIN(datum) as von, MAX(datum) as bis FROM auftrag;

-- 1.6
SELECT name FROM kunde WHERE nr=(SELECT kundnr FROM auftrag WHERE auftrnr=2);
SELECT name FROM personal WHERE persnr=(SELECT persnr FROM auftrag WHERE auftrnr=2);
SELECT name FROM personal WHERE persnr=(SELECT vorgesetzt FROM personal WHERE persnr=(SELECT persnr FROM au
ftrag WHERE auftrnr=2));

-- 1.7
SELECT teilnr, bestand FROM lager WHERE bestand>0 ORDER BY bestand ASC;

