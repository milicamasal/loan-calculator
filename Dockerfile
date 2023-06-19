FROM openjdk:11-jre-slim

WORKDIR /calculator

COPY target/loan-calculator-*.jar loan-calculator.jar

EXPOSE 8080

CMD ["java", "-jar", "/calculator/loan-calculator.jar"]
