create table results
(
   id integer identity not null primary key ,
   code varchar(50) not null,
   number integer not null,
   filenames varchar(100),
   error varchar(255)
);