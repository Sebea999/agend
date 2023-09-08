# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
#FROM alpine:latest
#CMD ["/bin/sh"]

#FROM openjdk:11
#COPY ./dist/AgendApp.war
#WORKDIR /usr/src/myapp
#RUN javac Main.java
#CMD ["java", "Main"]


##FROM        java:8-jdk
#FROM        openjdk:8-jdk-alpine
#
#ENV         JAVA_HOME         /usr/lib/jvm/java-8-openjdk-amd64
#ENV         GLASSFISH_HOME    /usr/local/glassfish4
#ENV         PATH              $PATH:$JAVA_HOME/bin:$GLASSFISH_HOME/bin
#
#RUN         apt-get update && \ apt-get install -y curl unzip zip inotify-tools && \ rm -rf /var/lib/apt/lists/*
#
#RUN         curl -L -o /tmp/glassfish-4.1.zip http://download.java.net/glassfish/4.1/release/glassfish-4.1.zip && \ unzip /tmp/glassfish-4.1.zip -d /usr/local && \ rm -f /tmp/glassfish-4.1.zip
#
#EXPOSE      8080 4848 8181
#
#WORKDIR     /usr/local/glassfish4
#
# verbose causes the process to remain in the foreground so that docker can track it
#CMD         asadmin start-domain --verbose



#FROM openjdk:8-jdk-alpine
#COPY "./dist/.jar" "app.jar"
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","app.jar"]

# https://www.youtube.com/watch?v=7O80TOISy7I
#FROM  openjdk:8
#ADD dist/AgendApp.jar app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar"]


# CODIGO HECHO POR OPERA IA [ARIA]
# Utilizamos una imagen base con el [JDK 8] de [Java]
#FROM [openjdk:8-jdk]
#FROM openjdk:16
# Establecemos el directorio de trabajo dentro del contenedor
#WORKDIR /app
# Copiamos el archivo .war de tu proyecto a la imagen
#COPY ./dist/AgendApp.war /app/AgendApp.war
# Exponemos el puerto en el que se ejecuta tu aplicación web (cambia el número de puerto según tu configuración)
#EXPOSE 8080
# Comando para ejecutar tu aplicación cuando se inicie el contenedor
#CMD ["java", "-jar", "AgendApp.war"]

#view the video:        https://www.youtube.com/watch?v=t9L__Y0E5OY


FROM tomcat:8-openjdk-8
COPY [./dist/AgendApp.war] /usr/local/tomcat/webapps/
EXPOSE 9999
CMD ["catalina.sh", "run"]
