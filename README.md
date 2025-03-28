1. ### Keycloak Setup
   Разворачиваем keycloak, должен появиться контейнер с названием keycloak-integration:
   ```bash
   docker-compose -f keycloak-integration/docker-compose.yml up -d
   ```
Перейти в [Keycloak Admin UI Console](http://localhost:9090 "Keycloak Admin UI Console").

   
2. Билдим:
   ```bash
   mvn -f gateway/pom.xml clean install
   mvn -f keycloak-integration/pom.xml clean install
   mvn -f wardrobe-service/pom.xml clean install
   ```
   
3. Запускаем:
   ```bash
   mvn -f gateway/pom.xml spring-boot:run
   mvn -f keycloak-integration/pom.xml spring-boot:run
   mvn -f wardrobe-service/pom.xml spring-boot:run
   ```