# Tasks Schema

# --- !Ups

CREATE SEQUENCE task_id_seq;
CREATE TABLE task (
  id integer NOT NULL DEFAULT nextval('task_id_seq'),
  label varchar(255) NOT NULL,
  estimate integer NOT NULL,
  done boolean NOT NULL DEFAULT false
);

# --- !Downs
DROP TABLE task;
DROP SEQUENCE task_id_seq;
