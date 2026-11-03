# citas-medicas

Proyecto académico de reservas de citas médicas con Java 25, Spring Boot 4.0.0, Maven, JPA/H2, pruebas automatizadas, JaCoCo y GitHub Actions.

## Ejecutar
Requisitos: JDK 25 y Maven (o el wrapper de Maven si se agrega posteriormente).

```powershell
mvn clean test
mvn verify
mvn spring-boot:run
```

Abrir `http://localhost:8080/citas.html`.

Las pruebas Selenium se etiquetan `ui` y se ejecutan individualmente desde VS Code o con una configuración específica de Maven.
