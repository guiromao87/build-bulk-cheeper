CREATE TABLE cheep (id bigint not null auto_increment primary key, date datetime(6), message varchar(255), autor_id integer);

alter table cheep add constraint FKhthn5gj8fbgw4q15nhq2kh8ng foreign key (autor_id) references user (id);

insert into cheep (date, message, autor_id) values (now(), 'Meu primeiro Cheep', 1);