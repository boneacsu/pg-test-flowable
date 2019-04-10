# pg-test-flowable

This is a suite of workflows and tests to validate different scenarios and capabilities of the Flowable process engine


# Database creation

Example for MSSQL :

CREATE DATABASE "flowable"

USE flowable

CREATE LOGIN flowable WITH PASSWORD = 'flowable';

CREATE USER flowable FOR LOGIN flowable

EXEC sp_addrolemember 'db_owner', 'flowable'