psql -U postgres -c "CREATE USER @@@APP@@@ WITH PASSWORD '@@@APP@@@';"
psql -U postgres -c "SELECT pg_terminate_backend(pg_stat_activity.pid) FROM pg_stat_activity WHERE pg_stat_activity.datname = '@@@APP@@@' AND pid <> pg_backend_pid();"
psql -U postgres -c "DROP DATABASE IF EXISTS @@@APP@@@;"
psql -U postgres -c "CREATE DATABASE @@@APP@@@ OWNER @@@APP@@@ ENCODING 'UTF8';"
psql -U postgres -c "GRANT ALL PRIVILEGES ON DATABASE @@@APP@@@ TO @@@APP@@@;"
psql -U postgres -d @@@APP@@@ -c "CREATE EXTENSION postgis;"
psql -U postgres -d @@@APP@@@ -c "ALTER USER postgres WITH PASSWORD 'changeme';"

psql -U postgres -c "CREATE USER @@@APP@@@r WITH PASSWORD '@@@APP@@@';"
psql -U postgres -c "GRANT CONNECT ON DATABASE @@@APP@@@ TO @@@APP@@@r;"
psql -U postgres -d @@@APP@@@ -c "GRANT USAGE ON SCHEMA public TO @@@APP@@@r;"
psql -U postgres -d @@@APP@@@ -c "GRANT SELECT ON ALL TABLES IN SCHEMA public TO @@@APP@@@r;"
psql -U postgres -d @@@APP@@@ -c "ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT ON TABLES TO @@@APP@@@r;"

