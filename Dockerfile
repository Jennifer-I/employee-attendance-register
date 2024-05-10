FROM openjdk:17-oracle
ENV PORT 1980
EXPOSE 1980
EXPOSE 5432
COPY target/employee-attendance-register-0.0.1-SNAPSHOT.jar /app/employee-attendance-register.jar
ENTRYPOINT ["java", "-jar", "app/employee-attendance-register-0.0.1-SNAPSHOT.jar"]

