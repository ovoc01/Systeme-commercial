


delete from details ;

CREATE VIEW validate_besoin_details AS
SELECT  d.*
FROM besoins b
JOIN details d ON b.idBesoins = d.idBesoins
WHERE b.etat > 0;

