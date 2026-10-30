# Vikings — Tienda de ropa y calzado deportivo

Aplicación web de comercio electrónico de ropa y calzado deportivo, desarrollada con **Java y Spring Boot**. Incluye una API REST, una base de datos MySQL y un front-end sencillo en HTML, CSS y JavaScript (inicio de sesión, tienda y administración de productos).

Es un proyecto personal y académico, creado mientras aprendo desarrollo de software.

## Características

- CRUD completo de **productos**, con imagen
- CRUD completo de **categorías**
- CRUD de **clientes**
- **Inicio de sesión** con contraseñas cifradas (BCrypt)
- Relación entre productos y categorías (cada producto pertenece a una categoría)
- Validación de datos de entrada (campos obligatorios, precio mayor que 0, correo con formato válido)
- Manejo centralizado de errores con respuestas JSON claras (400, 401, 404, 409)
- Arquitectura en capas: Controlador → Servicio → Repositorio → Base de datos
- Persistencia en MySQL con Spring Data JPA e Hibernate
- Front-end: página de login, tienda con banner y filtros por categoría, y panel para administrar productos

## Tecnologías

| Tecnología | Versión / uso |
|---|---|
| Java | 17 |
| Spring Boot | 4.1.1 |
| Spring Data JPA (Hibernate) | Acceso a la base de datos |
| Spring Validation | Validación de datos de entrada |
| Spring Security Crypto | Cifrado de contraseñas (BCrypt) |
| MySQL | Base de datos |
| Maven | Gestión de dependencias y construcción |
| HTML, CSS y JavaScript | Front-end, servido por la propia aplicación |

## Arquitectura

La aplicación es un **monolito**: el mismo proyecto sirve la API y las páginas del front-end.

```
Navegador (login.html, tienda.html, productos.html) / Postman
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
src/main
├── java/com/diego/vikings_api
│   ├── controlador   → ProductoController, CategoriaController, ClienteController, AuthController
│   ├── servicio      → ProductoService, CategoriaService, ClienteService, AuthService
│   ├── repositorio   → ProductoRepository, CategoriaRepository, ClienteRepository
│   ├── modelo        → Producto, Categoria, Cliente
│   ├── dto           → LoginRequest
│   ├── excepcion     → RecursoNoEncontradoException, SolicitudInvalidaException,
│   │                   CredencialesInvalidasException, ManejadorGlobalExcepciones
│   └── VikingsApiApplication.java
└── resources/static
    ├── login.html, login.css
    ├── tienda.html, tienda.css
    ├── productos.html
    └── img/          → imágenes del banner y de los productos
```

## Requisitos previos

- Java 17 o superior
- MySQL en funcionamiento (puerto 3306 por defecto)
- Git

No necesitas instalar Maven: el proyecto incluye el Maven Wrapper (`mvnw`).

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

La aplicación queda disponible en `http://localhost:8080`.

## Primeros pasos después de arrancar

La base de datos empieza vacía. Para probar la tienda, crea los datos en este orden (por ejemplo con Postman):

1. Una **categoría**: `POST /api/categorias` con `{ "nombre": "Camisetas" }`.
2. Uno o más **productos** que usen esa categoría (ver ejemplo más abajo). Las fotos van en `src/main/resources/static/img/productos/` y en el producto se guarda solo el nombre del archivo.
3. Un **cliente** con correo y contraseña: `POST /api/clientes`.
4. Abrir `http://localhost:8080/login.html` e iniciar sesión con ese cliente.

## Páginas

| Página | Ruta | Descripción |
|---|---|---|
| Inicio de sesión | `/login.html` | Formulario de correo y contraseña |
| Tienda | `/tienda.html` | Banner, filtros por categoría y tarjetas de producto |
| Administración | `/productos.html` | Crear, editar y eliminar productos |

La tienda y la administración redirigen al login si no hay una sesión iniciada.

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
  "producto": "Camiseta Entrenamiento Pro",
  "marca": "Vikings",
  "talla": "M",
  "color": "Negro",
  "precio": 129900,
  "imagen": "camiseta-entrenamiento-pro.png",
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

### Clientes — `/api/clientes`

| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| GET | `/api/clientes` | Lista todos los clientes | 200 |
| GET | `/api/clientes/{id}` | Consulta un cliente por id | 200, 404 |
| POST | `/api/clientes` | Crea un cliente | 201, 400 |
| PUT | `/api/clientes/{id}` | Actualiza los datos de un cliente | 200, 400, 404 |
| DELETE | `/api/clientes/{id}` | Elimina un cliente | 204, 404 |

Ejemplo de cuerpo para crear un cliente:

```json
{
  "nombre": "Cliente Prueba",
  "correo": "prueba@correo.com",
  "telefono": "3001234567",
  "direccion": "Calle 1",
  "contrasena": "1234"
}
```

La contraseña se guarda cifrada con BCrypt y **nunca se devuelve** en las respuestas. El correo debe ser único. La actualización (PUT) no modifica la contraseña.

### Inicio de sesión — `/api/auth`

| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| POST | `/api/auth/login` | Inicia sesión con correo y contraseña | 200, 400, 401 |

Ejemplo de cuerpo:

```json
{
  "correo": "prueba@correo.com",
  "contrasena": "1234"
}
```

## Manejo de errores

Los errores devuelven un JSON con el código de estado y un mensaje.

**400 Bad Request** — datos inválidos:

```json
{
  "estado": 400,
  "mensaje": "Hay datos inválidos",
  "errores": {
    "precio": "El precio es obligatorio"
  }
}
```

**401 Unauthorized** — credenciales incorrectas:

```json
{
  "estado": 401,
  "mensaje": "Correo o contraseña incorrectos"
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

## Limitaciones conocidas

- Las contraseñas se guardan cifradas, pero **la API no exige autenticación**: cualquier persona que conozca las rutas puede llamarla. La protección de las páginas se hace solo en el navegador, con `sessionStorage`.
- No hay roles: cualquier cliente con sesión iniciada puede entrar a la administración de productos.
- Cada producto tiene una sola talla.
- Las fotos incluidas son imágenes de ejemplo con fines académicos.

## Estado del proyecto

En desarrollo. Pendiente:

- Registro de clientes desde la página
- Carrito de compras, pedidos y detalle de pedido
- Seguridad real de la API (Spring Security y tokens)
- Roles de usuario (cliente y administrador)
- Lista desplegable de categorías en el formulario de administración
- Pruebas automáticas

## Autor

Diego Fernando Vera Reyes
