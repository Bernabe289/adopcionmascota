Sistema Backend de Adopción de Mascotas

Proyecto backend desarrollado para la asignatura Full Stack I.
El sistema permite gestionar un proceso de adopción de mascotas mediante una arquitectura basada en microservicios usando Spring Boot.

Tecnologías utilizadas
Java
Spring Boot
Spring Web
Spring Data JPA
Spring Validation
MySQL
XAMPP
Maven
Lombok
Eureka Server
OpenFeign
SLF4J
Postman
IntelliJ IDEA
Arquitectura del sistema

El proyecto está dividido en microservicios independientes.
Cada microservicio tiene una responsabilidad específica y sigue una estructura por capas:

Model
Repository
Service
Controller
Dto
Client

Los microservicios no usan relaciones directas con @ManyToOne ni @JoinColumn entre servicios distintos.
En su lugar, se guardan los IDs externos y se validan mediante OpenFeign.

Ejemplo:

private Integer idUsuario;
private Integer idMascota;
private Integer idSolicitud;

Microservicios del proyecto
Microservicio	Puerto	Descripción
eureka-server	8761	Servidor de descubrimiento de servicios
rolservice	8081	Gestión de roles de usuario
usuarioservice	8082	Gestión de usuarios
especieservice	8083	Gestión de especies
razaservice	8084	Gestión de razas
refugioservice	8085	Gestión de refugios
mascotaservice	8086	Gestión de mascotas
solicitudservice	8087	Gestión de solicitudes de adopción
evaluacionadoptanteservice	8088	Gestión de evaluaciones de adoptantes
historialvetservice	8089	Gestión de historiales veterinarios
vacunaservice	8090	Gestión de vacunas
visitaservice	8091	Gestión de visitas
documentoadopcionservice	8092	Gestión de documentos de adopción
seguimientoservice	8093	Gestión de seguimientos post adopción
reporteservice	8094	Gestión de reportes
notificacionservice	8095	Gestión de notificaciones
Base de datos

Base de datos utilizada:

db_adopcionmascota

Motor utilizado:

MySQL

Configuración general usada en los microservicios:

spring.datasource.url=jdbc:mysql://localhost:3306/db_adopcionmascota
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.flyway.enabled=false

Eureka Server

El sistema utiliza Eureka Server para registrar y descubrir microservicios.

URL de Eureka:

http://localhost:8761

Configuración usada en los microservicios:

eureka.client.service-url.defaultZone=http://localhost:8761/eureka
eureka.instance.prefer-ip-address=true

Comunicación entre microservicios

La comunicación entre microservicios se realiza mediante OpenFeign.

Conexiones principales:

usuarioservice → rolservice
razaservice → especieservice
mascotaservice → razaservice
mascotaservice → refugioservice
solicitudservice → usuarioservice
solicitudservice → mascotaservice
evaluacionadoptanteservice → solicitudservice
documentoadopcionservice → solicitudservice
historialvetservice → mascotaservice
vacunaservice → historialvetservice
visitaservice → solicitudservice
seguimientoservice → solicitudservice
reporteservice → usuarioservice
notificacionservice → usuarioservice

OpenFeign permite que un microservicio consulte a otro para validar si un ID realmente existe antes de guardar o actualizar un registro.

Ejemplo:

mascotaservice guarda idRaza e idRefugio.
Antes de guardar, consulta a razaservice y refugioservice para validar que existan.

Validaciones

El sistema utiliza validaciones con Spring Validation:

@NotBlank
@NotNull
@Email
@Size
@Min

Además, en la capa Service se aplican validaciones de negocio como:

Validar campos obligatorios.
Evitar duplicados.
Normalizar textos con trim().toUpperCase().
Normalizar emails con trim().toLowerCase().
Validar IDs externos usando OpenFeign.
Evitar guardar datos incompletos.
Logs con SLF4J

El proyecto integra logs estructurados con SLF4J en la capa Service.

Los logs permiten tener trazabilidad del sistema, es decir, permiten ver en consola qué operación ocurrió, qué dato falló y en qué parte del flujo ocurrió.

Tipos de logs utilizados:

logger.info() → operaciones exitosas
logger.warn() → validaciones fallidas o datos no encontrados
logger.error() → errores graves del sistema

Ejemplo:

logger.info("Mascota creada correctamente con ID {}", mascotaGuardada.getIdMascota());
logger.warn("No se pudo crear la mascota: idRaza o idRefugio viene null");

Endpoints generales

Cada microservicio expone endpoints REST siguiendo una estructura similar:

GET /api/v1/recurso
POST /api/v1/recurso
GET /api/v1/recurso/{id}
PUT /api/v1/recurso/{id}
DELETE /api/v1/recurso/{id}

Ejemplo para mascotas:

GET http://localhost:8086/api/v1/mascotas
POST http://localhost:8086/api/v1/mascotas
GET http://localhost:8086/api/v1/mascotas/{id}
PUT http://localhost:8086/api/v1/mascotas/{id}
DELETE http://localhost:8086/api/v1/mascotas/{id}

Ejemplo de JSON

Ejemplo para crear una mascota:

{
"nombreMascota": "Firulais",
"edadMascota": 3,
"sexoMascota": "Macho",
"tamanoMascota": "Mediano",
"estadoMascota": "Disponible",
"descripcionMascota": "Perro tranquilo y amigable",
"idRaza": 1,
"idRefugio": 1
}

Orden recomendado para ejecutar el sistema
Iniciar MySQL desde XAMPP.
Crear la base de datos db_adopcionmascota.
Ejecutar eureka-server.
Ejecutar los microservicios base:
rolservice
especieservice
refugioservice
usuarioservice
Ejecutar los demás microservicios.
Verificar que todos aparezcan registrados en Eureka.
Probar los endpoints desde Postman.
Estado del proyecto

El sistema cuenta con:

Arquitectura de microservicios.
Endpoints CRUD.
Validaciones con Spring Validation.
Comunicación entre microservicios con OpenFeign.
Registro en Eureka Server.
Logs estructurados con SLF4J.
Persistencia con MySQL y Spring Data JPA.
Integrantes
Integrante 1: Cristian Flores
Integrante 2: Gabriel Bustamante
