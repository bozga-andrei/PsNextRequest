# Pull base image
# ---------------
FROM jboss/wildfly:10.0.0.Final

# Updates & Installs
# -------------------------------

#Switch to root
USER root

#Set timezone
ENV TZ=Europe/Brussels
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY bashrc /root/.bashrc
#Set locale
RUN localedef -i fr_BE -f UTF-8 fr_BE.UTF-8

# Update RPM Packages
RUN yum -y -q update && yum clean all


# WildFly Configuration
# -------------------------------

#Switch to jboss user
USER jboss

RUN mkdir -p /opt/jboss/logs

#Add a custom configuration
ADD standalone-custom.xml /opt/jboss/wildfly/standalone/configuration/
#add the certificate
ADD wfly.jks /opt/jboss/wildfly/standalone/configuration/

#Create a eclipselink module and add eclipselink.jar 2.6.4
#RUN mkdir -p /opt/jboss/wildfly/modules/org/eclipse/persistence/main
ADD modules/eclipselink/eclipselink.jar /opt/jboss/wildfly/modules/system/layers/base/org/eclipse/persistence/main
ADD modules/eclipselink/module.xml /opt/jboss/wildfly/modules/system/layers/base/org/eclipse/persistence/main


#Deploy mysql connector
ADD mysql-connector-java-5.1.40-bin.jar /opt/jboss/wildfly/standalone/deployments/
#ADD eclipselink.jar /opt/jboss/wildfly/standalone/deployments/


#Create a https-users.properties file and add a new user for ssl; username: admin; password: welcome1
#Create a new mgmt user username:admin password: welcome1
RUN touch /opt/jboss/wildfly/standalone/configuration/https-users.properties && \
    /opt/jboss/wildfly/bin/add-user.sh admin welcome1 -up /opt/jboss/wildfly/standalone/configuration/https-users.properties -r httpsRealm --silent && \
    /opt/jboss/wildfly/bin/add-user.sh admin welcome1 --silent

# Increasing Initial heap size & Maximum heap size
RUN sed -i -- 's/JAVA_OPTS="-Xms64m -Xmx512m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m/JAVA_OPTS="-Xms512m -Xmx3072m -XX:MetaspaceSize=512M -XX:MaxMetaspaceSize=1024m/g' /opt/jboss/wildfly/bin/standalone.conf

RUN echo ${JAVA_OPTS}

#apply the standalone-custom.xml configuration
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-custom.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "--debug"]
