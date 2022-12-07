-- Создать базу данных oracle со следующими таблицами:
-- 1. Таблица "Users" (id,login,password,email,is_admin)
-- 2. Таблица "Genres" (id,name)
-- 3. Таблица "Authors" (id,name)
-- 4. Таблица "Tracks" (id,name,genre_id,author_id,file(blob))
-- 5. Таблица "Rating" (id,track_id,user_id,rate)
-- 6. Таблица "Comments" (id,track_id,user_id,text)
-- 7. Таблица "Playlists" (id,name,user_id)
-- 8. Таблица "Playlist_Tracks" (id,playlist_id,track_id)
-- всё ,




alter session set "_ORACLE_SCRIPT"=true;
alter session set container=CDB$ROOT;

create tablespace kursach
datafile 'kursach.dbf'
size 100m
autoextend on
next 100m
maxsize unlimited
extent management local
segment space management auto;

create user kursach_admin identified by qwerty
default tablespace kursach
temporary tablespace temp
quota unlimited on kursach;

grant all privileges to kursach_admin;
grant create session to kursach_admin;
grant create user to kursach_admin;

--unauthorized access to the database
create user unauthorized_user identified by qwerty
default tablespace kursach
temporary tablespace temp
quota unlimited on kursach;

grant connect to unauthorized_user;
grant execute on GET_USER_BY_ID to unauthorized_user;
grant execute on GET_USER_ID_BY_LOGIN to unauthorized_user;
grant execute on REGISTER_USER to unauthorized_user;


--admin
create user admin  identified by admin
default tablespace kursach
temporary tablespace temp
quota unlimited on kursach;

grant connect to admin;
grant execute on CREATE_TRACK_FILE to admin;
grant execute on CREATE_TRACK to admin;
grant execute on ADD_AUTHOR to admin;
grant execute on ADD_GENRE to admin;
grant execute on GET_GENRES to admin;
grant execute on GET_AUTHORS to admin;

--client
create user client identified by client
default tablespace kursach
temporary tablespace temp
quota unlimited on kursach;

grant connect to client;
grant execute on GET_USER_BY_LOGIN to client;
grant execute on GET_PLAYLISTS to client;
grant execute on GET_PLAYLIST_BY_ID to client;
grant execute on GET_PLAYLIST_TRACKS_FOR_USER to client;
grant execute on ADD_TRACK_TO_PLAYLIST to client;
grant execute on DELETE_TRACK_FROM_PLAYLIST to client;
grant execute on CREATE_PLAYLIST to client;
grant execute on GET_TRACKS_FOR_USER to client;
grant execute on SET_TRACK_RATING to client;
grant execute on UPDATE_TRACK_RATING to client;
grant execute on GET_TRACK_FILE to client;
grant execute on GET_TRACK_BY_ID to client;
grant execute on GET_TRACK_COUNT to client;
grant execute on GET_TRACK_FILE to client;
grant execute on GET_RATING_FOR_TRACK_FROM_USER to client;
grant execute on TRACKS_TO_JSON  to client;





--grant procedure GET_USER_ID to unauthorized_user;




--drop all tables
drop table playlist_tracks;
drop table playlists;
drop table comments;
drop table rating;
drop table tracks;
drop table authors;
drop table genres;
drop table users;



create table ROLES
(
    id number generated always as identity primary key ,
    NAME VARCHAR2(20) not null,

    constraint ROLES_NAME_UK check (NAME in ('admin','user'))
);

create table USERS(
    id number generated always as identity primary key ,
    role_id number not null,
    login varchar2(30) not null,
    password varchar2(30) not null,
    email varchar2(50) not null,

    foreign key (role_id) references ROLES(id)

);

--alter users table and set that password length should be 32 symbols (md5 hash)



-- 2. Таблица "Genres" (id,name)
create table GENRES(
    id number generated always as identity primary key,
    name varchar2(30) not null
);
--3 . Таблица "Authors" (id,name)
create table AUTHORS(
    id number generated always as identity primary key,
    name varchar2(30) not null
);
-- 4. Таблица "Tracks" (id,name,genre_id,author_id,file(blob))
create table TRACKS(
    id number generated always as identity primary key,
    name varchar2(30) not null,
    genre_id number not null,
    author_id number not null,
    track_file_id number not null,

    foreign key (genre_id) references GENRES(id),
    foreign key (track_file_id) references Track_Files(id),
    foreign key (author_id) references AUTHORS(id)

);




create table Track_Files(
    id number generated always as identity primary key,
    track_file blob
);






-- 5. Таблица "Rating" (id,track_id,user_id,rate)
create table RATING(
    id number generated always as identity primary key,
    track_id number not null,
    user_id number not null,
    rate number(10) not null check (rate>=0 and rate<=10),

    foreign key (track_id) references TRACKS(id),
    foreign key (user_id) references USERS(id)
);
-- 6. Таблица "Comments" (id,track_id,user_id,text)
create table COMMENTS(
    id number generated always as identity primary key,
    track_id number not null,
    user_id number not null,
    text varchar2(4000) not null,

    foreign key (track_id) references TRACKS(id),
    foreign key (user_id) references USERS(id)
);

-- 7. Таблица "Playlists" (id,name,user_id)
create table PLAYLISTS(
    id number generated always as identity primary key,
    name varchar2(30) not null,
    user_id number not null,

    foreign key (user_id) references USERS(id)
);

-- 8. Таблица "Playlist_Tracks" (id,playlist_id,track_id)
create table PLAYLIST_TRACKS(
    id number generated always as identity primary key,
    playlist_id number not null,
    track_id number not null,

    foreign key (playlist_id) references PLAYLISTS(id),
    foreign key (track_id) references TRACKS(id)
);




