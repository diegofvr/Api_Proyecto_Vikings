# Vikingos API

API REST para un e-commerce de ropa y calzado deportivo, desarrollada con Java y Spring Boot. Es un proyecto personal y académico, creado mientras aprendo desarrollo de software.

## Características

- CRUD completo de **productos**
- CRUD completo de **categorías**
- Relación entre productos y categorías (cada producto pertenece a una categoría)
- Validación de datos de entrada (campos obligatorios, precio mayor que 0)
- Manejo centralizado de errores con respuestas JSON claras (400, 404, 409)
- Arquitectura en capas: Controller → Service → Repository → Base de datos
- Persistencia en MySQL con Spring Data JPA e Hibernate

## Tecnologías

| Tecnología | Versión / uso |
|---|---|
| Java | 17 |
| Spring Boot | 4.1.1 |
| Spring Data JPA (Hibernate) | Acceso a la base de datos |
| Spring Validation | Validación de datos de entrada |
| MySQL | Base de datos |
| Maven | Gestión de dependencias y construcción |

## Arquitectura

```
Cliente HTTP (Postman, navegador...)
        │
        ▼
Controller   → recibe la petición HTTP y arma la respuesta
        │
        ▼
Service      → aplica las reglas de negocio
        │
        ▼
Repository   → consulta y guarda datos
        │
        ▼
MySQL
```

## Estructura del proyecto

```
src/main/java/com/diego/vikings_api
├── controlador   → ProductoController, CategoriaController
├── servicio      → ProductoService, CategoriaService
├── repositorio   → ProductoRepository, CategoriaRepository
├── modelo        → Producto, Categoria
├── excepcion     → RecursoNoEncontradoException, SolicitudInvalidaException, ManejadorGlobalExcepciones
└── VikingsApiApplication.java
```

## Requisitos previos

- Java 17 o superior
- MySQL en funcionamiento (puerto 3306 por defecto)
- Git

No necesitas instalar Maven: el proyecto incluye el *Maven Wrapper* (`mvnw`).

## Cómo ejecutar el proyecto

**1. Clonar el repositorio**

```bash
git clone https://github.com/diegofvr/Api_Proyecto_Vikings.git
cd Api_Proyecto_Vikings
```

**2. Crear la base de datos en MySQL**

```sql
CREATE DATABASE vikings_api_db;
```

Las tablas se crean solas al arrancar la aplicación.

**3. Crear el archivo de configuración**

El archivo `application.properties` no se incluye en el repositorio porque contiene credenciales. Crea `src/main/resources/application.properties` con este contenido, cambiando los valores por los tuyos:

```properties
spring.application.name=vikings-api

spring.datasource.url=jdbc:mysql://localhost:3306/vikings_api_db
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

**4. Ejecutar la aplicación**

```bash
# Linux / Mac
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

## Endpoints

### Productos — `/api/productos`

| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| GET | `/api/productos` | Lista todos los productos | 200 |
| GET | `/api/productos/{id}` | Consulta un producto por id | 200, 404 |
| POST | `/api/productos` | Crea un producto | 201, 400, 404 |
| PUT | `/api/productos/{id}` | Actualiza un producto | 200, 400, 404 |
| DELETE | `/api/productos/{id}` | Elimina un producto | 204, 404 |

Ejemplo de cuerpo para crear o actualizar un producto (la categoría debe existir):

```json
{
  "producto": "Camiseta de compresión",
  "marca": "Nike",
  "talla": "M",
  "color": "Negro",
  "precio": 89900,
  "categoria": { "id": 1 }
}
```

### Categorías — `/api/categorias`

| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| GET | `/api/categorias` | Lista todas las categorías | 200 |
| GET | `/api/categorias/{id}` | Consulta una categoría por id | 200, 404 |
| POST | `/api/categorias` | Crea una categoría | 201, 400 |
| PUT | `/api/categorias/{id}` | Actualiza una categoría | 200, 400, 404 |
| DELETE | `/api/categorias/{id}` | Elimina una categoría | 204, 404, 409 |

Ejemplo de cuerpo:

```json
{ "nombre": "Camisetas" }
```

## Manejo de errores

Los errores devuelven un JSON con el código de estado y un mensaje.

**400 Bad Request** — datos inválidos:

```json
{
  "estado": 400,
  "mensaje": "Datos inválidos",
  "errores": {
    "precio": "El precio debe ser mayor que 0"
  }
}
```

**404 Not Found** — el recurso no existe:

```json
{
  "estado": 404,
  "mensaje": "No existe un producto con id 9999"
}
```

**409 Conflict** — la operación choca con datos relacionados, por ejemplo al eliminar una categoría que todavía tiene productos.

## Estado del proyecto

En desarrollo. Pendiente:

- [ ] Clientes
- [ ] Pedidos y detalle de pedido
- [ ] Frontend (HTML, CSS y JavaScript)
- [ ] Pruebas automáticas

## Autor

**Diego Fernando Vera Reyes**
