# gamehub_store-FS1

server-port de cada microservicio:

- auth: 8080:
- user: 8081
    endpoint: /api/v1/usuarios

        GET /api/v1/usuarios: Lista de todos los usuarios 
        GET /api/v1/usuarios/{id}: muestra el usuario con el id indicado
        POST /api/v1/usuarios: Crea un usuario, se requieres los siguientes parametros:
              - nombre(String)
              - email(String)
              - telefono(String)
              - rol(String)
              - estado(String)
        PUT /api/v1/usuario/{id}: permite cambiar los siguientes parametros del usuario seleccionado por id:
              - nombre(String)
              - email(String)
              - telefono(String)
        DELETE /api/v1/usuarios/{id}: elimina el usuario con el id seleccionado

- product: 8082
- category: 8083
- inventory: 8084
- order: 8085
- payments: 8086
- shipping: 8087
- notifications: 8088
- promotions: 8089

dependencias:

- Lombok
- Spring Boot Devtools
- Spring Web
- Spring Reactive Web
- Spring data JPA
- H2 Database
- OpenFeign
- Validation
- Spring Security
