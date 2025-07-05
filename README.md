 (cd "$(git rev-parse --show-toplevel)" && git apply --3way <<'EOF' 
diff --git a/README.md b/README.md
index 5e8b86a7938b4a3a0b02e601b5f1cf865b53641d..e39800698a6481c1bed8e5423ae4c60ec2b76657 100644
--- a/README.md
+++ b/README.md
@@ -1,2 +1,40 @@
 # gestionCliente
-Mircroservicio de gestion de cliente de diseño CRUD.
+
+Microservicio de gestion de clientes basado en Spring Boot y Docker.
+
+## Requisitos
+- Java 17
+- Maven
+- Docker (opcional para ejecutar con `docker-compose`)
+
+## Configuracion inicial
+1. Dirigete a la carpeta `gestiondecliente/` y crea un archivo `.env` con las variables de entorno necesarias:
+   ```bash
+   MYSQL_ROOT_PASSWORD=<tu_clave_root>
+   MYSQL_DATABASE=<nombre_base>
+   SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/<nombre_base>
+   SPRING_DATASOURCE_USERNAME=root
+   SPRING_DATASOURCE_PASSWORD=<tu_clave_root>
+   ```
+2. Si deseas cambiar el puerto, edita `src/main/resources/application.properties`.
+
+## Ejecucion con Docker
+```bash
+cd gestiondecliente
+docker-compose up --build
+```
+Esto levantara un contenedor MySQL y la aplicacion en `http://localhost:8080`.
+
+## Ejecucion local sin Docker
+```bash
+cd gestiondecliente
+./mvnw spring-boot:run
+```
+
+## Pruebas
+```bash
+cd gestiondecliente
+./mvnw test
+```
+
+La documentacion OpenAPI estara disponible en `http://localhost:8080/swagger-ui.html` cuando la aplicacion este en ejecucion.
 
EOF
)
