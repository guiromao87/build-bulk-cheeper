create table relationship(follower_id integer not null, followed_id integer not null);

alter table relationship add constraint FKal68f3iqgugyisltovm28vdic foreign key(follower_id) references user(id);

alter table relationship add constraint FKbbsbqqevwr29ktmx5tocb9p2y foreign key(followed_id) references user(id);