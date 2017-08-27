-- Create table
create table STAR_SM_USER
(
  star_id       NUMBER(19) not null,
  user_name     VARCHAR2(100),
  user_password VARCHAR2(100),
  is_enabled    NUMBER(1),
  status        VARCHAR2(100),
  user_id       NUMBER(19)
);

-- Create table
create table STAR_USER_REGISTRN
(
  star_id     NUMBER(19) not null,
  first_name  VARCHAR2(100),
  last_name   VARCHAR2(100),
  gender      VARCHAR2(100),
  dob         DATE,
  contact_num VARCHAR2(100),
  email       VARCHAR2(100)
);