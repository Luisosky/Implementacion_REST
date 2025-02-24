# Implementación de Servicios REST

Este proyecto está diseñado para demostrar cómo implementar una API REST que soporte operaciones CRUD y paginación utilizando Spring Boot. Se utiliza una base de datos en memoria mediante H2 para facilitar las pruebas y el desarrollo sin la necesidad de configurar un servidor de bases de datos externo.

## Características del Proyecto

- **Operaciones CRUD:** Implementación de endpoints para crear, leer, actualizar y eliminar usuarios.
- **Paginación:** Soporte para paginar los resultados, facilitando el manejo de grandes volúmenes de datos.
- **Validación de Datos:** Se utilizan las anotaciones de Jakarta Validation para asegurar la integridad de los datos.
- **Base de Datos en Memoria:** Configurada con H2, permitiendo pruebas rápidas sin requerir un entorno de base de datos adicional.
- **Pruebas con MockMvc:** Se incluyen pruebas de integración utilizando MockMvc para verificar el correcto funcionamiento de la API.

## Tecnologías Utilizadas

- Java 21
- Spring Boot 3.x (Web, Data JPA)
- H2 Database (base de datos en memoria)
- JUnit y MockMvc para pruebas de integración

## Cómo Ejecutar la Aplicación

1. Clonar el Repositorio:
 ```
  git clone https://github.com/<tu_usuario>/<nombre_del_repositorio>.git
```
2. Importar en el IDE 
3. Compilar y Ejecutar:
   
     Maven:
   
     ```
    mvn spring-boot:run
    ```
     Gradle:
   
   ```
    ./gradlew bootRun
    ```

## Configuración del Proyecto

El archivo `application.properties` está configurado para utilizar H2 como base de datos en memoria, lo que permite ejecutar y probar la aplicación sin requerir una base de datos externa. El archivo `application.properties` debe tener la siguiente config:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true

# Configuración JPA para crear y eliminar el esquema automáticamente
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true



1
