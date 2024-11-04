
# API RESTful - Decrypto

Este proyecto es una API RESTful serverless desarrollada en Java con Spring Boot para AWS Lambda, siguiendo las indicaciones del desafío técnico. La API permite gestionar recursos de **Comitente**, **Mercado** y **País**, así como consultar estadísticas de distribución de comitentes por país y mercado. La aplicación está documentada con Swagger y cuenta con endpoints CRUD básicos.

### URL de la API

La API está desplegada en AWS y puede ser accedida mediante los siguientes endpoints:
- [Swagger UI](https://mmszt7n0bl.execute-api.sa-east-1.amazonaws.com/dev/swagger-ui.html) - Documentación de la API
- **Países**: [GET /api/paises](https://mmszt7n0bl.execute-api.sa-east-1.amazonaws.com/dev/api/paises)
- **Mercados**: [GET /api/mercados](https://mmszt7n0bl.execute-api.sa-east-1.amazonaws.com/dev/api/mercados)
- **Comitentes**: [GET /api/comitentes](https://mmszt7n0bl.execute-api.sa-east-1.amazonaws.com/dev/api/comitentes)
- **Estadísticas**: [GET /stats](https://mmszt7n0bl.execute-api.sa-east-1.amazonaws.com/dev/stats)

### Características

- **CRUD** para gestionar **Comitente**, **Mercado** y **País**.
- Relación muchos a muchos entre comitentes y mercados.
- **Documentación OpenAPI/Swagger** para los endpoints.
- Endpoint `/stats` que proporciona estadísticas de comitentes por país y mercado.
- Desplegado en **AWS Lambda** utilizando **API Gateway**.

### Requisitos

- **Java 17**
- **Maven**
- **AWS CLI** (opcional para despliegue en AWS)
- **MySQL** (si se utiliza en entorno local)

### Configuración

#### 1. Base de Datos
La API utiliza MySQL como base de datos. Configure los detalles de conexión en el archivo `application.properties`.

```properties
spring.datasource.url=jdbc:mysql://<host>:<puerto>/<nombre_bd>
spring.datasource.username=<usuario>
spring.datasource.password=<contraseña>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

#### 2. Swagger
En entorno local, la documentación Swagger estará disponible en `/swagger-ui.html`. Para deshabilitar Swagger en AWS, comente la línea en el archivo `application.properties`:

```properties
# Configuración de Swagger
springdoc.swagger-ui.enabled=true
# Configuración para AWS
#springdoc.swagger-ui.enabled=false
```

### Ejecución

#### 1. Ejecución en Entorno Local

Clona el repositorio, instala las dependencias y ejecuta el proyecto:

```bash
git clone https://github.com/Master-Victor/decrypto
cd aws-lambda
mvn clean install
mvn spring-boot:run
```

Accede a la API en `http://localhost:8080`.

#### 2. Despliegue en AWS Lambda

Para desplegar en AWS:
1. Cambia el perfil de Spring a `aws` y descomenta las anotaciones `@Configuration` y `@Controller` en las clases correspondientes.
2. Compila el proyecto en un JAR sombreado (shaded JAR) y utiliza el AWS CLI o SAM CLI para desplegarlo.

### Endpoints

1. **Países**:
    - `GET /api/paises`: Listar todos los países.
    - `GET /api/paises/{id}`: Obtener un país por su ID.

2. **Mercados**:
    - `GET /api/mercados`: Listar todos los mercados.
    - `GET /api/mercados/{id}`: Obtener un mercado por su ID.
    - `POST /api/mercados`: Crear un nuevo mercado.
    - `PUT /api/mercados/{id}`: Actualizar un mercado existente.
    - `DELETE /api/mercados/{id}`: Eliminar un mercado.

3. **Comitentes**:
    - `GET /api/comitentes`: Listar todos los comitentes.
    - `GET /api/comitentes/{id}`: Obtener un comitente por su ID.
    - `POST /api/comitentes`: Crear un nuevo comitente.
    - `PUT /api/comitentes/{id}`: Actualizar un comitente existente.
    - `DELETE /api/comitentes/{id}`: Eliminar un comitente.

4. **Estadísticas**:
    - `GET /stats`: Proporciona un resumen de la distribución de comitentes por país y mercado.

### Ejemplo de Respuesta `/stats`

```json
[
  {
    "country": "Argentina",
    "market": [
      {
        "MAE": {
          "percentage": "80.75"
        },
        "ROFEX": {
          "percentage": "2.00"
        }
      ]
    ]
  },
  {
    "country": "Uruguay",
    "market": [
      {
        "UFEX": {
          "percentage": "17.25"
        }
      }
    ]
  }
]
```

### Link del despliegue
[API en AWS](https://mmszt7n0bl.execute-api.sa-east-1.amazonaws.com/dev/api)

Puedes acceder a la interfaz de Swagger para probar los endpoints en la siguiente URL:

[Swagger UI](https://mmszt7n0bl.execute-api.sa-east-1.amazonaws.com/dev/swagger-ui.html)

### Endpoints disponibles
- **Países**: [GET /api/paises](https://mmszt7n0bl.execute-api.sa-east-1.amazonaws.com/dev/api/paises)
- **Mercados**: [GET /api/mercados](https://mmszt7n0bl.execute-api.sa-east-1.amazonaws.com/dev/api/mercados)
- **Comitentes**: [GET /api/comitentes](https://mmszt7n0bl.execute-api.sa-east-1.amazonaws.com/dev/api/comitentes)
- **Estadísticas**: [GET /stats](https://mmszt7n0bl.execute-api.sa-east-1.amazonaws.com/dev/stats)
