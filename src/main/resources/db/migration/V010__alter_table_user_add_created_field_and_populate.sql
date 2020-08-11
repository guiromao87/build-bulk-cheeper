ALTER TABLE user ADD COLUMN created date;
ALTER TABLE user ADD COLUMN profile_name varchar(20);

update user set created = now(), profile_name = 'gui' where id = 1;
