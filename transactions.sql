-- 3
BEGIN;
	SELECT COUNT(*) AS "Zahl der Lieferungen" FROM lieferung;
	DELETE FROM lieferant;
	SELECT COUNT(*) AS "Zahl der Lieferungen" FROM lieferung;
ROLLBACK;

SELECT COUNT(*) AS "Zahl der Lieferungen" FROM lieferung;

