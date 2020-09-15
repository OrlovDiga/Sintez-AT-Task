FROM postgres:latest

ENV POSTGRES_PASSWORD password
ENV POSTGRES_DB anonapp_db
ADD init/00_init_user.sql /docker-entrypoint-initdb.d/