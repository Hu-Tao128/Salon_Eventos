create database salent;

create table disponibilidad(
    numero boolean PRIMARY KEY,
    estado varchar(15) not null
);

create table evento(
    numero int primary key auto_increment,
    nombre varchar(40)
);

create table montaje(
    numero int primary key auto_increment,
    nombreMontaje varchar(30) not null,
    descripcion varchar(50) not null
);

create table equipamiento(
    numero int primary key auto_increment,
    descripcion varchar(35) not null,
    precio float not null,
    stock int not null
);

create table cliente(
    numero int primary key auto_increment,
    nombreFiscal varchar(50),
    nomContacto varchar(40) not null,
    primerApellido varchar(30) not null,
    segundoApellido varchar(30),
    numTel varchar(15) not null unique,
    email varchar(30) not null unique
);

create table salon(
    numero int  primary key auto_increment,
    nombre varchar(20) not null,
    dirCalle varchar(40) not null,
    dirColonia varchar(30) not null,
    dirNumero varchar(10) not null,
    capacidad int not null,
    tamanio float not null,
    precio float not null
);

create table servicios(
    numero int primary key auto_increment,
    nombreServicio varchar(40) not null,
    descripcion varchar (50) not null,
    precio float not null,
    disponibilidad boolean not null,
    foreign key(disponibilidad) references disponibilidad(numero)
);

create table renta(
    numero int primary key auto_increment,
    fechaReservacion date not null,
    fechaInicio date not null,
    fechaFinal date not null,
    horaInicio time not null,
    horaFinal time not null,
    IVA float not null,
    subtotal float not null,
    total float not null,
    montaje int not null,
    salon int not null,
    cliente int not null,
    evento int not null,
    foreign key(montaje) references montaje(numero),
    foreign key(salon) references salon(numero),
    foreign key(cliente) references cliente(numero),
    foreign key(evento) references evento(numero)
);

create table servicios_renta(
    servicios int not null,
    renta int not null,
    primary key(servicios, renta),
    foreign key(servicios) references servicios(numero),
    foreign key(renta) references renta(numero)
);

create table equipos_renta(
    equipamiento int not null,
    renta int not null,
    cantidad float not null,
    importe float not null,
    primary key(equipamiento, renta),
    foreign key(equipamiento) references equipamiento(numero),
    foreign key(renta) references renta(numero)
);

create table equipos_evento(
    evento int not null,
    equipamiento int not null,
    primary key(evento, equipamiento),
    foreign key(evento) references evento(numero),
    foreign key(equipamiento) references equipamiento(numero)
);

create table montaje_evento(
    evento int not null,
    montaje int not null,
    primary key(evento, montaje),
    foreign key(evento) references evento(numero),
    foreign key(montaje) references montaje(numero)
);


insert into disponibilidad(numero, estado) values
(0, "No Disponible"),
(1, "Disponible");

insert into evento(numero, nombre) values
(null,"Bodas"),
(null,"Conferencias"),
(null,"Seminarios"),
(null,"Fiestas de cumpleaños"),
(null,"Reuniones corporativas"),
(null,"Exposiciones y ferias"),
(null,"Eventos musicales"),
(null,"Fiestas de aniversario"),
(null,"Talleres y capacitaciones"),
(null,"Eventos de caridad");

insert into montaje(numero, nombreMontaje, descripcion) values
(null, "Conferencia", "Filas de sillas frente a un escenario."),
(null, "Teatro", "Sillas en filas mirando al escenario."),
(null, "Recepción", "Espacio abierto con mesas para socializar."),
(null, "Banquete", "Mesas redondas con sillas alrededor."),
(null, "Salón", "Espacio flexible sin disposición fija."),
(null, "Disposición en forma de U", "Mesas en forma de U para interacción."),
(null, "Conferencia Circular", "Sillas en círculo para conferencias."),
(null, "Aula", "Mesas y sillas en filas para escribir."),
(null, "Cabaret", "Mesas redondas con sillas abiertas al frente."),
(null, "Lounge", "Sofás y sillones para ambiente relajado.");

insert into servicios(numero, nombreServicio, descripcion, precio, disponibilidad) values
(null, "Guardias de seguridad", "Protección y vigilancia del evento", 1500.00, 1),
(null, "Acceso a internet", "Conexión Wi-Fi para los asistentes", 800.0, 1),
(null, "Fotógrafos", "Captura de momentos del evento", 3000.00, 1),
(null, "Vídeo", "Grabación y edición de videos del evento", 3500.00, 1),
(null, "DJ o música en vivo", " Entretenimiento musical para el evento", 4000.00, 1),
(null, "Transporte y valet parking", "Traslados y estacionamiento seguro", 2500.00, 1),
(null, "Iluminación profesional", "Luces especializadas para ambientación", 1200.00, 1),
(null, "Servicio de montaje y desmontaje", "Instalación y retiro de equipos", 2000.00, 1),
(null, "Servicio de bar", "Bebidas y cócteles para los invitados", 3000.00, 1),
(null, "Servicio de limpieza", "Limpieza antes, durante y después del evento", 1000.00, 1);

insert into equipamiento(numero, descripcion, precio, stock) values 
(null, "Micrófonos inalámbricos", 600.00, 12),
(null, "Micrófonos", 300.00, 15),
(null, "Proyectores de pantalla", 1500.00, 8),
(null, "Pantallas", 1200.00, 10),
(null, "Equipo de sonido", 3000.00, 6),
(null, "Cámaras de video", 4000.00, 6),
(null, "Luces LED", 500.00, 18),
(null, "Pantallas LED", 5000.00, 6),
(null, "Pizarras digitales interactivas", 6000.00, 6),
(null, "Telones de fondo", 1000.00, 8);

insert into cliente(numero, nombreFiscal, nomContacto, primerApellido, segundoApellido, numTel, email) values
(null,"Vibe Venues", "Maria Perez", "García", "López", "664-123-4567", "García.López@gmail.com"),
(null,"Epic Event Spaces", "Luis Hernández", "Rodríguez", "Martínez", "664-987-6543", "Rodríguez.Martínez@gmail.com"),
(null,"Lux Lounge Rentals", "Sandra González", "Sánchez", "Pérez", "664-555-7890", "Sánchez.Pérez@gmail.com"),
(null,"Majestic Moments Halls", "Roberto Ramírez", "Fernández", "Gómez", "664-321-4568", "Fernández.Gómez@gmail.com"),
(null,"Chic Celebrations", "Beatriz Morales", "Ramírez", "Torres", "664-654-3219", "Ramírez.Torres@gmail.com"),
(null,"Serene Spaces", "Miguel Ortega", "Ruiz", "Rivas", "664-678-1234", "Ruiz.Rivas@gmail.com"),
(null,"Harmony Halls", "Carla Mendoza", "Hernández", "Castro", "664-799-2345", "Hernández.Castro@gmail.com"),
(null,"Glamour Gatherings", "Andrés Vargas", "Morales", "Aguilar", "664-891-3456", "Morales.Aguilar@gmail.com"),
(null,"Elite Event Lounges", "Silvia Núñez", "López", "Espinoza", "664-901-4567", "López.Espinoza@gmail.com"),
(null,"Trendy Event Studios", "Enrique Fuentes", "Torres", "Delgado", "664-012-5678", "Torres.Delgado@gmail.com"),
(null, "", "Teresa Ramos", "Romero", "Ortiz", "664-123-6789", "Romero.Ortiz@gmail.com"),
(null, "", "Jaime Cruz", "García", "Jiménez", "664-234-7890", "García.Jiménez@gmail.com"),
(null, "", "Lucia Gómez", "Sánchez", "Herrera", "664-345-8901", "Sánchez.Herrera@gmail.com"),
(null, "", "Juan Torres", "Hernández", "López", "664-456-9012", "Hernández.López@gmail.com"),
(null, "", "Fernanda Diaz", "Rodríguez", "Soto", "664-567-0123", "Rodríguez.Soto@gmail.com"),
(null, "", "Gustavo Torres", "Morales", "Peña", "664-668-1234", "Morales.Peña@gmail.com"),
(null, "", "Paula Rivera", "López", "Gutiérrez", "664-789-2345", "López.Gutiérrez@gmail.com"),
(null, "", "Carlos Flores", "Torres", "Rojas", "664-890-3456", "Torres.Rojas@gmail.com"),
(null, "", "Gabriela Vega", "Hernández", "Ruiz", "664-911-4567", "Hernández.Ruiz@gmail.com"),
(null, "", "Alberto Muñoz", "Rodríguez", "Sánchez", "664-112-5678", "Rodríguez.Sánchez@gmail.com");

insert into salon(numero, nombre, dirCalle, dirColonia, dirNumero, capacidad, tamanio, precio) values
(null, "Gran Salón Imperial", "Calle 4ta", "Centro", "8050", 300, 450, 15000.00),
(null, "Salón Bella Vista", "Oriente", "Santa Fe", "7002 int 8", 250, 375, 12500.00),
(null, "Salón Cristal", "Blvd. López Mateos", "Industrial", "218", 200, 300, 10000.00),
(null, "Salón Esmeralda", "Tijuana-Rosarito", "Parcelas", "300", 150, 225, 7500.00),
(null, "Salón Paraíso", "Blvd. Real de Baja California", "San Francisco", "23911", 350, 525, 17500.00),
(null, "Salón Real", "Av. Reforma", "Reforma", "124 int 4", 400, 600, 20000.00),
(null, "Salón Elegancia", "Carr. Transpeninsular", "Santa Lucía", "135", 180, 270, 9000.00),
(null, "Salón Dorado", "Av. Ruíz", "Centro", "328", 220, 330, 11000.00),
(null, "Salón Majestuoso", "Av. López Mateos", "Zona Centro", "154 int 12", 500, 750, 25000.00),
(null, "Salón Diamante", "Blvd. Anahuac", "Fracc. Villa Residencial", "1301", 275, 412, 13750.00);

insert into renta(numero, fechaReservacion, fechaInicio,fechaFinal, horaInicio, horaFinal, IVA, subtotal, total, montaje, salon, cliente, evento) values 
(null, "24-01-12", "24-01-15", "24-01-16", "10:30:00", "2:00:00", 3376.00, 21100.00, 24476.00, 1, 5, 1, 2),
(null, "24-02-03", "24-02-06", "24-02-07", "11:00:00", "21:00:00", 4320.00, 27000.00, 31320.00, 2, 9, 2, 6),
(null, "24-03-22", "24-04-01", "24-04-02", "7:00:00", "15:00:00", 1392.00, 8700.00, 10092.00, 3, 4, 3, 1),
(null, "24-04-15", "24-04-20", "24-04-21", "8:00:00", "14:00:00", 1600.00, 10000.00, 11600.00, 4, 3, 4, 1),
(null, "24-05-05", "24-05-10", "24-05-11", "10:00:00", "22:00:00", 3280.00, 20500.00, 23780.00, 5, 5, 5, 7),
(null, "24-06-25", "24-07-01", "24-07-02", "12:20:00", "0:00:00", 2880.00, 18000.00, 20880.00, 6, 1, 6, 4),
(null, "24-07-12", "24-07-15", "24-07-15", "8:30:00", "20:30:00", 2560.00, 16000.00, 18560.00, 7, 2, 7, 10),
(null, "24-08-03", "24-08-06", "24-08-07", "8:30:00", "16:00:00", 1952.00, 12200.00, 14152.00, 8, 7, 8, 9),
(null, "24-09-22", "24-10-01", "24-10-02", "10:00:00", "10:00:00", 4480.00, 28000.00, 32480.00, 9, 9, 9, 7),
(null, "24-10-15", "24-10-20", "24-10-21", "11:00:00", "11:00:00", 1760.00, 11000.00, 12760.00, 10, 8, 10, 8),
(null, "24-11-05", "24-11-10", "24-11-10", "8:30:00", "20:30:00", 1824.00, 11400.00, 13224.00, 5, 4, 11, 3),
(null, "24-12-25", "25-01-01", "25-01-02", "8:00:00", "13:00:00", 5504.00, 34400.00, 39904.00, 4, 6, 12, 1),
(null, "24-01-03", "24-01-10", "24-01-10", "8:30:00", "20:30:00", 1760.00, 11000.00, 12760.00, 8, 8, 13, 5),
(null, "24-02-05", "24-02-10", "24-02-11", "12:00:00", "10:00:00", 1632.00, 10200.00, 11832.00, 7, 7, 14, 7),
(null, "24-03-03", "24-03-10", "24-03-11", "12:00:00", "1:00:00", 1760.00, 11000.00, 12760.00, 6, 4, 15, 4),
(null, "24-04-05", "24-04-10", "24-04-11", "8:00:00", "2:00:00", 1200.00, 7500.00, 8700.00, 3, 4, 16, 1),
(null, "24-05-15", "24-05-20", "24-05-21", "1:00:00", "21:00:00", 4160.00, 26000.00, 30160.00, 2, 9, 17, 7),
(null, "24-06-25", "24-07-01", "24-07-02", "1:00:00", "18:00:00", 3328.00, 20800.00, 24128.00, 5, 6, 18, 2),
(null, "24-07-12", "24-07-15", "24-07-16", "9:30:00", "2:00:00", 2600.00, 16250.00, 18850.00, 4, 10, 19, 1),
(null, "24-08-03", "24-08-06", "24-08-07", "11:00:00", "21:00:00", 3440.00, 21500.00, 24940.00, 2, 6, 20, 6),
(null, "24-09-22", "24-10-01", "24-10-02", "11:00:00", "21:00:00", 1600.00, 10000.00, 11600.00, 9, 3, 1, 7),
(null, "24-10-15", "24-10-20", "24-10-20", "8:30:00", "20:30:00", 2328.00, 14550.00, 16878.00, 1, 10, 1, 10),
(null, "24-11-05", "24-11-10", "24-11-11", "11:00:00", "23:40:00", 2064.00, 12900.00, 14964.00, 7, 7, 5, 2),
(null, "24-12-25", "25-01-01", "25-01-02", "8:50:00", "17:00:00", 1760.00, 11000.00, 12760.00, 5, 8, 5, 9),
(null, "24-01-03", "24-01-10", "24-01-11", "11:00:00", "19:00:00", 4400.00, 27500.00, 31900.00, 8, 5, 5, 6),
(null, "24-02-05", "24-02-10", "24-02-11", "9:00:00", "1:00:00", 1760.00, 11000.00, 12760.00, 4, 8, 5, 1),
(null, "24-03-03", "24-03-10", "24-03-11", "10:00:00", "12:00:00", 2800.00, 17500.00, 20300.00, 4, 5, 5, 1),
(null, "24-04-05", "24-04-10", "24-04-11", "12:00:00", "22:00:00", 2560.00, 16000.00, 18560.00, 9, 2, 6, 7),
(null, "24-05-15", "24-05-20", "24-05-20", "8:30:00", "20:30:00", 560.00, 3500.00, 4060.00, 6, 1, 8, 5),
(null, "24-06-25", "24-07-01", "24-07-02", "10:50:00", "1:30:00", 3760.00, 23500.00, 27260.00, 9, 5, 10, 4);


insert into servicios_renta(servicios, renta) values 
(1, 8),
(8, 29),
(4, 15),
(8, 2),
(5, 25),
(4, 7),
(9, 9),
(10, 17),
(2, 18),
(7, 3),
(1, 20),
(7, 14),
(3, 11),
(6, 19),
(2, 22);

insert into equipos_renta(equipamiento, renta, cantidad, importe) values 
(1, 1, 5, 3000.00),
(2, 1, 2, 600.00),
(5, 5, 1, 3000.00),
(5, 6, 1, 3000.00),
(1, 8, 2, 1200.00),
(2, 11, 3, 900.00),
(1, 12, 3, 1800.00),
(4, 12, 3, 3600.00),
(6, 12, 2, 8000.00),
(7, 12, 2, 1000.00),
(2, 23, 4, 2400.00),
(3, 23, 1, 1500.00),
(9, 25, 1, 6000.00),
(4, 28, 2, 4800.00),
(5, 30, 2, 6000.00);

insert into equipos_evento(evento, equipamiento)values 
(1, 1),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 10),
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8),
(2, 9),
(3, 1),
(3, 2),
(3, 3),
(3, 4),
(3, 5),
(3, 6),
(3, 7),
(3, 8),
(3, 9),
(4, 1),
(4, 3),
(4, 4),
(4, 5),
(4, 6),
(4, 7),
(4, 8),
(4, 10),
(5, 1),
(5, 2),
(5, 3),
(5, 4),
(5, 5),
(5, 6),
(5, 7),
(5, 8),
(5, 9),
(6, 1),
(6, 3),
(6, 4),
(6, 5),
(6, 6),
(6, 7),
(6, 8),
(6, 10),
(7, 1),
(7, 2),
(7, 3),
(7, 4),
(7, 5),
(7, 6),
(7, 7),
(7, 8),
(8, 1),
(8, 3),
(8, 4),
(8, 5),
(8, 6),
(8, 7),
(8, 8),
(8, 10),
(9, 1),
(9, 2),
(9, 3),
(9, 4),
(9, 5),
(9, 6),
(9, 7),
(9, 8),
(9, 9),
(10, 1),
(10, 3),
(10, 4),
(10, 5),
(10, 6),
(10, 7),
(10, 8),
(10, 10);

insert into montaje_evento(evento, montaje) values 
(1, 3),
(1, 4),
(1, 5),
(1, 10),
(2, 1),
(2, 2),
(2, 6),
(2, 7),
(2, 8),
(3, 1),
(3, 2),
(3, 6),
(3, 7),
(3, 8),
(4, 3),
(4, 4),
(4, 5),
(4, 9),
(4, 10),
(5, 1),
(5, 2),
(5, 5),
(5, 6),
(5, 7),
(5, 8),
(6, 2),
(6, 3),
(6, 5),
(6, 8),
(7, 2),
(7, 3),
(7, 5),
(7, 9),
(7, 10),
(8, 3),
(8, 4),
(8, 5),
(8, 9),
(8, 10),
(9, 1),
(9, 2),
(9, 6),
(9, 7),
(9, 8),
(10, 3),
(10, 4),
(10, 5),
(10, 9),
(10, 10);

--consultas

--consultas Profe
/*1. Información de una reservación
    a. Fecha de la reservación
    b. Hora de la reservación
    c. Nombre del cliente
    d. Tipo de evento
    e. Nombre del salón
    f. Dirección del salón
    g. Tipo de montaje
    h. Cantidad de invitados
    i. Costo total del evento
*/
SELECT
    DATE_FORMAT(r.fechaReservacion, "%y-%m-%d") AS FechaReservacion,
    DATE_FORMAT(r.horaInicio, '%h:%i') AS HoraReservacion,
    CONCAT(c.nomContacto, ' ', c.primerApellido, ' ', IFNULL(c.segundoApellido, '')) AS NombreCliente,
    e.nombre AS TipoEvento,
    s.nombre AS NombreSalon,
    CONCAT(s.dirCalle, ' ', s.dirNumero, ', ', s.dirColonia) AS DireccionSalon,
    m.nombreMontaje AS TipoMontaje,
    s.capacidad AS CantidadInvitados,
    r.total AS CostoTotalEvento
FROM renta AS r
INNER JOIN cliente AS c ON r.cliente = c.numero
INNER JOIN evento AS e ON r.evento = e.numero
INNER JOIN salon AS s ON r.salon = s.numero
INNER JOIN montaje AS m ON r.montaje = m.numero
WHERE c.numero = 10;


/*2. Equipamiento requerido en una reservación
    a. Fecha de la reservación
    b. Nombre del cliente
    c. Nombre del salón
    d. Descripción de cada quipo
    e. Costo del equipo
*/
SELECT
    DATE_FORMAT(r.fechaReservacion, "%y-%m-%d") AS FechaReservacion,
    CONCAT(c.nomContacto, ' ', c.primerApellido, ' ', IFNULL(c.segundoApellido, '')) AS NombreCliente,
    s.nombre AS NombreSalon,
    eq.descripcion AS DescripcionEquipo,
    eq.precio AS CostoEquipo
FROM renta AS r
INNER JOIN cliente AS c ON r.cliente = c.numero
INNER JOIN salon AS s ON r.salon = s.numero
INNER JOIN equipos_renta AS er ON r.numero = er.renta
INNER JOIN equipamiento AS eq ON er.equipamiento = eq.numero
WHERE c.numero = 5;


/*3. Servicios requeridos en una reservación
    a. Fecha de reservación
    b. Nombre del cliente
    c. Nombre del salón
    d. Descripción de cada servicio
    e. Tipo de servicio
    f. Costo del servicio
*/
SELECT
    DATE_FORMAT(r.fechaReservacion, "%y-%m-%d") AS FechaReservacion,
    CONCAT(c.nomContacto, ' ', c.primerApellido, ' ', IFNULL(c.segundoApellido, '')) AS NombreCliente,
    s.nombre AS NombreSalon,
    se.nombreServicio AS DescripcionServicio,
    se.precio AS CostoServicio
FROM renta AS r
INNER JOIN cliente AS c ON r.cliente = c.numero
INNER JOIN salon AS s ON r.salon = s.numero
INNER JOIN servicios_renta AS sr ON r.numero = sr.renta
INNER JOIN servicios AS se ON sr.servicios = se.numero
WHERE c.numero = 5;


/*4. Reservaciones para el mismo salón
    a. Nombre del salón
    b. Fecha de la reservación
    c. Nombre del cliente
    d. Tipo de evento
*/
SELECT
    s.nombre AS NombreSalon,
    DATE_FORMAT(r.fechaReservacion, "%y-%m-%d") AS FechaReservacion,
    CONCAT(c.nomContacto, ' ', c.primerApellido, ' ', IFNULL(c.segundoApellido, '')) AS NombreCliente,
    e.nombre AS TipoEvento
FROM renta AS r
INNER JOIN cliente AS c ON r.cliente = c.numero
INNER JOIN salon AS s ON r.salon = s.numero
INNER JOIN evento AS e ON r.evento = e.numero
WHERE s.numero = 2;


/*5. Servicios del mismo tipo
    a. Descripción del tipo de servicio
    b. Descripción del servicio
    c. Precio
*/
SELECT
	s.nombreServicio AS DescripcionTipoServicio,
    s.descripcion AS DescripcionServicio,
    s.precio AS Precio
FROM evento AS e
INNER JOIN renta AS r ON e.numero = r.evento
INNER JOIN servicios_renta AS sr ON r.numero = sr.renta
INNER JOIN servicios AS s ON sr.servicios = s.numero
WHERE e.numero = 1;


/*  6. Reservaciones que ha realizado el mismo cliente
        -- Nombre del cliente
        -- Fecha de reservacion
        -- Salon de la reservacion
        -- Tipo de evento
        -- Cantidad de invitados
*/
select concat(c.nomContacto,' ',c.primerApellido,' ',
        ifnull(concat (c.segundoApellido,' '),' ')) as Cliente,
DATE_FORMAT(r.fechaReservacion, "%d-%m-%y") as "Fecha de reservacion",
s.nombre as Salon,
e.nombre as "Tipo de evento",
s.capacidad as "Cantidad de invitados"
from salon as s
inner join renta as r on s.numero = r.salon
inner join cliente as c on c.numero = r.cliente
inner join evento as e on e.numero = r.evento
where c.numero = 1


/*  7. Reservaciones donde se ha solicitado un mismo servicio
        -- Numero de la reservacion 
        -- Fecha de la reservacion 
        -- Nombre del salon
        -- Nombre del cliente
        -- Tipo de evento
        -- Cantidad de invitados
        -- Descripcion del servicio
        -- Costo del servicio
        */
select r.numero as Reservacion,
DATE_FORMAT(r.fechaReservacion, "%d-%m-%y") as "Fecha de reservacion",
sa.nombre as Salon,
concat(c.nomContacto,' ',c.primerApellido,' ',
        ifnull(concat (c.segundoApellido,' '),' ')) as Cliente,
e.nombre as "Tipo de evento",
sa.capacidad as "Cantidad de invitados",
s.descripcion as "Descripcion del servicio",
s.precio as "Costo del servicio"
from servicios as s
inner join servicios_renta as sr on s.numero = sr.servicios
inner join renta as r on sr.renta = r.numero
inner join salon as sa on sa.numero = r.salon
inner join cliente as c on c.numero = r.cliente
inner join evento as e on e.numero = r.evento
where s.numero = 1;


/*  8. Reservaciones donde se ha solicitado el mismo equipo
        -- Numero de la reservacion
        -- Fecha de la reservacion
        -- Nombre del salon
        -- Nombre del cliente
        -- Tipo de evento
        -- Cantidad de invitados
        -- Descripcion del equipo
        -- Costo del equipo*/
select r.numero as Reservacion,
DATE_FORMAT(r.fechaReservacion, "%d-%m-%y") as "Fecha de reservacion",
sa.nombre as Salon,
concat(c.nomContacto,' ',c.primerApellido,' ',
        ifnull(concat (c.segundoApellido,' '),' ')) as Cliente,
e.nombre as "Tipo de evento",
sa.capacidad as "Cantidad de invitados",
eq.descripcion as "Descripcion del equipo",
eq.precio as "Costo del equipo"
from equipamiento as eq
inner join equipos_renta as er on eq.numero = er.equipamiento
inner join renta as r on er.renta = r.numero
inner join salon as sa on sa.numero = r.salon
inner join cliente as c on c.numero = r.cliente
inner join evento as e on e.numero = r.evento
where eq.numero = 1;


/*  9. Reservaciones donde se ha solicitado el mismo montaje
        -- Numero de la reservacion
        -- Fecha de la reservacion
        -- Nombre del salon
        -- Nombre del cliente
        -- Tipo de evento
        -- Cantidad de invitados
        -- Descripcion del montaje*/
select r.numero as Reservacion,
DATE_FORMAT(r.fechaReservacion, "%d-%m-%y") as "Fecha de reservacion",
sa.nombre as Salon,
concat(c.nomContacto,' ',c.primerApellido,' ',
        ifnull(concat (c.segundoApellido,' '),' ')) as Cliente,
e.nombre as "Tipo de evento",
sa.capacidad as "Cantidad de invitados",
m.descripcion as "Descripcion del montaje"
from montaje as m
inner join renta as r on r.montaje = m.numero
inner join salon as sa on sa.numero = r.salon
inner join cliente as c on c.numero = r.cliente
inner join evento as e on e.numero = r.evento
where m.numero = 1;


/*  10. Reservaciones realizadas para el mismo mes 
        -- Fecha
        -- Nombre del salon
        -- Nombre del cliente
        -- Tipo de evento
        -- Cantidad de invitados
        */
select DATE_FORMAT(r.fechaInicio, "%d-%m-%y") as "Fecha de inicio del evento",
s.nombre as Salon,
concat(c.nomContacto,' ',c.primerApellido,' ',
        ifnull(concat (c.segundoApellido,' '),' ')) as Cliente,
e.nombre as "Tipo de evento",
s.capacidad as "Cantidad de invitados"
from renta as r
inner join salon as s on r.salon = s.numero
inner join cliente as c on r.cliente = c.numero
inner join evento as e on r.evento = e.numero
where month(r.fechaInicio) = 1;


--Consutas individuales
/*	0. Consulta para listar todos los eventos junto con los servicios que están disponibles para cada evento*/ 
SELECT 
    e.nombre AS EventoNombre, 
    GROUP_CONCAT(DISTINCT se.nombreServicio ORDER BY se.nombreServicio SEPARATOR ', ') AS Servicios,
    COUNT(DISTINCT se.numero) AS CantidadServicios
FROM evento AS e
INNER JOIN renta AS r ON e.numero = r.evento
INNER JOIN servicios_renta AS sr ON r.numero = sr.renta
INNER JOIN servicios AS se ON sr.servicios = se.numero
GROUP BY e.numero, e.nombre
ORDER BY CantidadServicios DESC;



/*    1.	Consulta para encontrar los salones con la mayor cantidad de eventos reservados:*/
SELECT s.nombre AS Salon, COUNT(r.numero) AS "cantidad de eventos"
FROM salon AS s
INNER JOIN renta as r ON s.numero = r.salon
GROUP BY s.numero
ORDER BY cantidad_eventos DESC;


/*    2.	Consulta para identificar los clientes que han reservado los servicios más costosos:*/
select concat(c.nomContacto,' ',c.primerApellido,' ',
        ifnull(concat (c.segundoApellido,' '),' ')) as Cliente,
s.nombreServicio as Servicio,
s.precio as Precio
from cliente as c
inner join renta as r on c.numero = r.cliente
inner join servicios_renta as sr on sr.renta = r.numero
inner join servicios as s on sr.servicios = s.numero
where s.precio >= 3000;


/*    3.	Consulta para encontrar el equipamiento más frecuentemente alquilado y su frecuencia de uso:*/
SELECT e.descripcion, COUNT(er.renta) AS frecuencia
FROM equipamiento AS e
INNER JOIN equipos_renta AS er ON e.numero = er.equipamiento
GROUP BY e.numero
HAVING frecuencia > 1
ORDER BY frecuencia DESC;


/*    4.	Consulta para obtener un informe detallado de eventos con la diferencia entre las fechas de inicio y final:*/
SELECT e.numero, e.nombre, r.fechaInicio, r.fechaFinal, DATEDIFF(r.fechaFinal, r.fechaInicio) AS duracion_dias
FROM evento e
JOIN renta r ON e.numero = r.evento;


/*    5.	Consulta para listar todos los eventos junto con los montajes que están disponibles para cada evento:*/
SELECT 
    e.nombre AS EventoNombre, 
    GROUP_CONCAT(DISTINCT m.nombreMontaje ORDER BY m.nombreMontaje SEPARATOR ', ') AS MontajesDisponibles,
    COUNT(DISTINCT m.numero) AS CantidadMontajes
FROM evento AS e
INNER JOIN renta AS r ON e.numero = r.evento
INNER JOIN montaje_evento AS me ON e.numero = me.evento
INNER JOIN montaje AS m ON me.montaje = m.numero
GROUP BY e.numero, e.nombre
ORDER BY CantidadMontajes DESC;



/*    6.	Consulta para identificar eventos que se solapan en fechas y la cantidad de solapamientos:*/


/*    7.	Consulta para encontrar los salones que han sido reservados con mayor frecuencia en los últimos seis meses:*/
SELECT s.nombre, COUNT(r.numero) AS cantidad_reservas
FROM salon AS s
INNER JOIN renta AS r ON s.numero = r.salon
WHERE r.fechaReservacion >= CURDATE() - INTERVAL 6 MONTH
GROUP BY s.numero
ORDER BY cantidad_reservas DESC;


/*    8.	Consulta para listar los clientes con el mayor número de reservas y el total gastado en cada cliente:*/
SELECT c.nomContacto, COUNT(r.numero) AS cantidad_reservas, SUM(r.total) AS total_gastado
FROM cliente AS c
INNER JOIN renta AS r ON c.numero = r.cliente
GROUP BY c.numero
HAVING cantidad_reservas > 1
ORDER BY cantidad_reservas DESC, total_gastado DESC;


/*    9.	Consulta para obtener los eventos que tienen el mayor número de servicios asociados:*/
SELECT e.nombre, COUNT(sr.servicios) AS cantidad_servicios
FROM evento AS e
INNER JOIN renta AS r ON e.numero = r.evento
INNER JOIN servicios_renta AS sr ON r.numero = sr.renta
GROUP BY e.numero
HAVING COUNT(sr.servicios) > 1
ORDER BY cantidad_servicios DESC;


/*    10.	Consulta para ver la duración promedio de los eventos por salón:*/
SELECT s.nombre AS salon_nombre, TRUNCATE(AVG(DATEDIFF(r.fechaFinal, r.fechaInicio)), 2) AS duracion_promedio_dias
FROM salon AS s
INNER JOIN renta AS r ON s.numero = r.salon
GROUP BY s.numero;


/*    11.	Consulta para obtener el promedio de ingresos por tipo de servicio:*/


/*    12.	Consulta para listar los clientes que han reservado más de un tipo de servicio:*/
SELECT c.numero, c.nomContacto, COUNT(DISTINCT sr.servicios) AS cantidad_servicios
FROM cliente AS c
INNER JOIN renta AS r ON c.numero = r.cliente
INNER JOIN servicios_renta AS sr ON r.numero = sr.renta
GROUP BY c.numero
HAVING cantidad_servicios > 1;


/*    13.	Consulta para encontrar los equipos con mayor tasa de rotación (alquiler frecuente) en los últimos tres meses:  */
SELECT e.descripcion, COUNT(er.renta) AS frecuencia
FROM equipamiento AS e
INNER JOIN equipos_renta AS er ON e.numero = er.equipamiento
INNER JOIN renta AS r ON er.renta = r.numero
WHERE r.fechaReservacion >= CURDATE() - INTERVAL 3 MONTH
GROUP BY e.numero
HAVING frecuencia > 1
ORDER BY frecuencia DESC;

