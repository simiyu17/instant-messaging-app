FROM amazoncorretto:11-alpine-jdk
COPY . /home/instantChat
WORKDIR /home/instantChat
RUN ./gradlew clean -x test bootJar
RUN mv /home/instantChat/build/libs/instantChat-1.0.jar /home/instantChat-1.0.jar && rm -rf /home/instantChat
ENTRYPOINT ["java"]
CMD ["-jar", "/home/instantChat-1.0.jar"]
EXPOSE 8080
