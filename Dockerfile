FROM openjdk:8-jre-alpine
 
ENV JAVA_OPTS=${JAVA_OPTS:-'-Xmx512m'}
ENV DEBUG_OPTS=${DEBUG_OPTS}
ENV PORT=${PORT:-2702}
ENV spring.datasource.url=${DATA_SOURCE:-'jdbc:h2:file:./store/db/vecchioDB'}
ENV spring.datasource.username=${DB_USER:-'sa'}
ENV spring.datasource.password=${DB_PASSWORD:-''}
ENV spring.datasource.driver-class-name=${DB_DRIVER:-'org.h2.Driver'}
ENV spring.jpa.properties.hibernate.dialect=${DB_DIALECT:-'org.hibernate.dialect.H2Dialect'}

RUN adduser -D -g '' java

COPY /build/libs/viglet-vecchio.jar /app.jar


RUN sh -c 'touch /app.jar'
RUN sh -c 'mkdir -p /store'
RUN sh -c 'chown -R java /store'

VOLUME /tmp
VOLUME /store

USER java

EXPOSE ${PORT}

CMD java ${JAVA_OPTS} ${DEBUG_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar
