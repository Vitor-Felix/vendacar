CREATE DATABASE keycloak_db;
CREATE USER keycloak WITH ENCRYPTED PASSWORD 'password_keycloak';
GRANT ALL PRIVILEGES ON DATABASE keycloak_db TO keycloak;
-- Comandos extras para garantir permissões no Postgres 17
\c keycloak_db
GRANT ALL ON SCHEMA public TO keycloak;
