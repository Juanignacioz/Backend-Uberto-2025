1. Saber qué usuarios hicieron para un determinado chofer.


MATCH (v:Viajero)-[r:VIAJO_CON]->(c:Chofer {conductorId:"id_del_conductor"})
RETURN DISTINCT v.viajeroId, v.nombre_y_apellido,c.nombre_y_apellido;

2. Saber qué usuarios tienen más de 4 viajes.

MATCH (v:Viajero)-[r:VIAJO_CON]->(c:Chofer)
WITH v, COUNT(r) AS totalViajes
WHERE totalViajes > 4
RETURN v.viajeroId, v.nombre_y_apellido, totalViajes
ORDER BY totalViajes DESC;


3. Saber una sugerencia de choferes que podrían interesar. Son los choferes con los que viajaron amigos del usuario.

MATCH (v:Viajero {viajeroId:"id_del_viajero"})-[amigazo:AMISTAD]->(amigo:Viajero)-[a:VIAJO_CON]->(c:Chofer)
RETURN DISTINCT c


4.Saber qué choferes tienen más de 4 viajes.

MATCH (v:Viajero)-[r:VIAJO_CON]->(c:Chofer)
WITH c, COUNT(r) AS totalViajes
WHERE totalViajes > 4
RETURN c.conductorId, c.nombre_y_apellido, totalViajes
ORDER BY totalViajes DESC;



5. Saber qué usuario tiene un viaje con un chofer que tiene más de 3 viajes pendientes.

MATCH (c:Chofer)<-[r:VIAJO_CON]-(v:Viajero)
WHERE r.fechaDeFinalizacion > localdatetime()
WITH c, COUNT(r) AS viajesPendientes
WHERE viajesPendientes > 3
MATCH (usuario:Viajero)-[r2:VIAJO_CON]->(c)
WHERE r2.fechaDeFinalizacion > localdatetime()
RETURN DISTINCT usuario.viajeroId, usuario.nombre_y_apellido, c.conductorId,c.nombre_y_apellido, viajesPendientes;