FROM websphere-liberty:javaee7
RUN apt-get update \
  && apt-get install -y language-pack-ro-base \
  && rm -rf /var/lib/apt/lists/*
ENV LANG ro_RO.UTF-8
RUN installUtility install --acceptLicense defaultServer 
COPY server.xml  /config/
COPY jvm.options /config/
COPY target/colegiulcasaverde.ro.war /config/dropins/
EXPOSE 9080
EXPOSE 9443
EXPOSE 9000
 
