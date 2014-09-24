#!/bin/bash
#
# SCRIPT:  
#     ./travis/before_script_postgres.sh
# PURPOSE: 
#     创建数据库及用户，初始化 schema
# DISCRIPTION: 
#     创建一个测试数据库，并增加一个可以从客户端程序访问数据库的用户目。
#     最后初始化整个 schema。

DBNAME="jibu_db"
DBUSER="jibu_db_user"
DBPASS="000000"

psql -c "CREATE USER $DBUSER WITH PASSWORD '$DBPASS';" -U postgres
psql -c "CREATE DATABASE $DBNAME OWNER $DBUSER ENCODING 'UTF8';" -U postgres

# ./jibu-schema/postgresql/tables/ 目录下的 .sql 文件按文件名顺序执行。
cat $HOME/jibu-schema/postgresql/tables/tables*.sql > $HOME/schema.sql
cat $HOME/jibu-schema/postgresql/tables/comments*.sql >> $HOME/schema.sql
psql -U $USER -d $DBNAME -1 < $HOME/schema.sql


