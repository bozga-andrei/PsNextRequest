FROM mysql:8.0

COPY *.sql /docker-entrypoint-initdb.d/
COPY test_psnextrequest_db.sql /tmp/test_psnextrequest_db.sql

ENV MYSQL_ROOT_PASSWORD 1234
ENV MYSQL_USER psnext
ENV MYSQL_PASSWORD 1234
ENV MYSQL_DATABASE psnextrequest_db

RUN /entrypoint.sh mysqld & sleep 30 && killall mysqld