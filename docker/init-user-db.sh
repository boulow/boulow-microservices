#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
	DROP DATABASE IF EXISTS boulow_dev;
    CREATE DATABASE boulow_user_dev;
	CREATE DATABASE boulow_account_dev;
	GRANT ALL PRIVILEGES ON DATABASE boulow_user_dev TO "$POSTGRES_USER";
	GRANT ALL PRIVILEGES ON DATABASE boulow_account_dev TO "$POSTGRES_USER";
EOSQL