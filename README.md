# Microservicio de Usuarios (msvc-usuarios)

## Descripción
Este microservicio es responsable de la gestión de usuarios dentro de un ecosistema de microservicios. Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) de usuarios, además de integrarse de manera síncrona con el microservicio de cursos (`msvc-cursos`) para mantener la integridad de los datos.

Una característica clave es la eliminación en cascada: cuando un usuario es eliminado de este sistema, se notifica automáticamente al microservicio de cursos para que sea desvinculado de cualquier curso en el que estuviera inscrito.

## Tecnologías Utilizadas
- **Java 17** con **Spring Boot 3.x**
- **Spring Data JPA** para la capa de persistencia.
- **PostgreSQL** como base de datos relacional.
- **Spring Cloud OpenFeign** para la comunicación REST entre microservicios.
- **Lombok** para la generación automática de código (getters, setters, etc.).
- **Jakarta Validation** para asegurar la integridad de los datos de entrada.
- **Docker** para la contenedorización (Multi-stage build).
- **Maven** como gestor de dependencias y herramienta de construcción.

## Requisitos
- **Java 17** o superior.
- **Maven 3.6+**.
- **Docker** y Docker Compose (opcional, para despliegue en contenedores).
- Instancia de **PostgreSQL** activa.

## Configuración
El microservicio utiliza las siguientes configuraciones predeterminadas en `src/main/resources/application.properties`:

| Propiedad | Valor Predeterminado | Descripción |
|-----------|----------------------|-------------|
| `server.port` | `8001` | Puerto en el que corre el servicio. |
| `spring.datasource.url` | `jdbc:postgresql://postgres:5432/msvc-usuarios` | URL de conexión a la base de datos PostgreSQL. |
| `spring.datasource.username` | `postgres` | Usuario de la base de datos. |
| `spring.datasource.password` | `postgress` | Contraseña de la base de datos. |
| `msvc.cursos.url` | `msvc-cursos:8002` | URL base del microservicio de cursos para la integración. |

## Endpoints de la API (UserController)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| **GET** | `/usuarios` | Obtiene la lista de todos los usuarios. |
| **GET** | `/usuarios/{id}` | Obtiene el detalle de un usuario por su ID. |
| **POST** | `/usuarios` | Crea un nuevo usuario. |
| **PUT** | `/usuarios/{id}` | Actualiza la información de un usuario existente. |
| **DELETE** | `/usuarios/{id}` | Elimina un usuario y sincroniza la baja con `msvc-cursos`. |
| **GET** | `/usuarios/usuarios-por-curso` | Obtiene usuarios específicos mediante una lista de IDs (`?ids=1,2,3`). |

## Cómo Ejecutar el Proyecto

### Localmente con Maven
1. Asegúrate de tener una base de datos PostgreSQL activa con el nombre `msvc-usuarios`.
2. Clona el repositorio y navega a la carpeta raíz.
3. Ejecuta el comando:
   ```bash
   ./mvnw spring-boot:run
   ```

### Usando Docker
El proyecto incluye un `Dockerfile` optimizado que utiliza una construcción en varias etapas para reducir el tamaño de la imagen final.

1. **Construir la imagen:**
   ```bash
   docker build -t msvc-usuarios .
   ```
2. **Ejecutar el contenedor:**
   ```bash
   docker run -p 8001:8001 --name msvc-usuarios msvc-usuarios
   ```

---
*Desarrollado como parte de un sistema de microservicios con Spring Boot.*
