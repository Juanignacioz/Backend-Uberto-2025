/*1. Conocer los viajes que reservó un determinado usuario en el corriente año.
2. Llevar un control de las veces que un usuario modifica su saldo, de manera de saber: a) la fecha en la que se modificó, b) el nuevo saldo y el anterior saldo.
3. Saber qué usuarios tienen más de N reservas.
4. Evitar que el precio base de un chofer tome un valor nulo en la base (por fuera de la interfaz de usuario).
5. Listar los choferes que tengan más de 2 viajes realizados.
*/

--1.

CREATE VIEW viajes_usuario_anio_actual AS
SELECT u.id as usuario, count(v.*) as cantidad_de_reservas 
FROM viajes v
INNER JOIN viajeros u ON v.viajero_id = u.id
WHERE EXTRACT(YEAR FROM v.fecha_fin) = EXTRACT(YEAR FROM CURRENT_DATE)
GROUP BY u.id; 

--2. 
CREATE TABLE auditoria_saldos (
    id_auditoria UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    id_viajero VARCHAR NOT NULL,
    fecha_modificacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    saldo_anterior DECIMAL(10,2) NOT NULL,
    saldo_nuevo DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_viajero) REFERENCES viajeros(id)
);

CREATE OR REPLACE FUNCTION registrar_cambio_saldo()
RETURNS TRIGGER AS $$
BEGIN
    IF OLD.saldo IS DISTINCT FROM NEW.saldo THEN
        INSERT INTO historial_saldo (viajero_id, fecha, saldo_anterior, saldo_nuevo)
        VALUES (OLD.id, NOW(), OLD.saldo, NEW.saldo);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trigger_cambio_saldo
AFTER UPDATE ON viajeros
FOR EACH ROW
EXECUTE FUNCTION registrar_cambio_saldo();

--3.

CREATE OR REPLACE PROCEDURE usuarios_con_mas_de_n_reservas(n_reservas INT)
LANGUAGE plpgsql
AS $$
BEGIN
    CREATE TEMP TABLE IF NOT EXISTS temp_usuarios_reservas (
        id_usuario INT,
        nombre_usuario TEXT,
        cantidad_reservas INT
    );

    TRUNCATE temp_usuarios_reservas;

    INSERT INTO temp_usuarios_reservas
    SELECT u.id, u.username, COUNT(v.id)
    FROM viajeros u  
    JOIN viaje v ON u.id = v.viajero_id
   WHERE u.es_chofer = false AND v.fecha_fin > NOW()
    GROUP BY u.id, u.nombre
    HAVING COUNT(v.id) > n_reservas;
END;
$$

--4. 

ALTER TABLE conductores ADD CONSTRAINT ck_precio_base_chofer CHECK (precio_base_del_viaje IS NOT NULL);

--5.

CREATE VIEW choferes_con_mas_de_2_viajes AS
SELECT c.id as id_conductor, c.nombre  ||  ' ' ||   c.apellido as nombre_y_apellido, COUNT(v.*) AS total_viajes
FROM conductores c
INNER JOIN viajes v ON c.id = v.conductor_id
WHERE c.es_chofer=true AND v.fecha_fin < NOW()::TIMESTAMP
GROUP BY c.id
HAVING COUNT(v.*) > 2;



/* PRUEBAS
select * from viajes_usuario_anio_actual

3.
CALL  usuarios_con_mas_de_n_reservas(1);
SELECT * FROM temp_usuarios_reservas;
DROP TABLE temp_usuarios_reservas;



5. select * from choferes_con_mas_de_2_viajes

*/