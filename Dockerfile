
# Primeira etapa: gerar o jar com man package dentro do container
# A imagem maven jdk esta deprecated, eclipse temurin apresenta vulnerabilidade. Usar amazon corretto
FROM maven:3.9.9-amazoncorretto-21-alpine AS build
COPY . .
RUN mvn clean package -DskipTests

# RUN ls -la /target/praja.jar # nao funciona no windows
 # Lista o arquivo para verificar se foi criado


# Segunda etapa: acessar o jar gerado na primeira etapa

FROM amazoncorretto:21-alpine

# Para acessar a primeira etapa: --from=build

# O nome Usuario.jar vem da tag finalName do pom.xml que adicionei
COPY --from=build target/*.jar app.jar
EXPOSE 9090
CMD ["java", "-jar", "/app.jar"]