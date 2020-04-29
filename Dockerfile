FROM openjdk:11-jdk-slim

COPY target/task-service-1.0.jar /opt/task-service/

# some tools
RUN apt-get update && apt-get install -y vim tree mc lnav

# setting proper TZ
ENV TZ=Europe/Moscow
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

EXPOSE 8080
VOLUME /opt/task-service/logs

WORKDIR /opt/task-service/

CMD ["java","-jar","task-service-1.0.jar"]