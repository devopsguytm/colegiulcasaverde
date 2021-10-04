FROM websphere-liberty:javaee7
RUN installUtility install --acceptLicense defaultServer 
COPY target/colegiulcasaverde.ro.war /config/dropins/
COPY server.xml  /config/
COPY jvm.options /config/
EXPOSE 9080