create PROCEDURE REGISTER_USER
    (user_login VARCHAR2, user_password VARCHAR2, user_email VARCHAR2,user_role_id Number) is
begin
    INSERT INTO users (login, PASSWORD, email,ROLE_ID) VALUES (user_login, user_password , user_email,user_role_id);
end REGISTER_USER;
/

create procedure Get_Tracks_For_User(user_id_ number, tracks_to_skip_ number, tracks_to_fetch_ number,
                                     search_by_ varchar2, search_value_ varchar2, order_ varchar2, min_rate_ number, max_rate_ number,
                                     tracks_ OUT SYS_REFCURSOR)
    is
begin
    if (order_ = 'descending')
    then
        open tracks_ for select t.TRACK_ID        as id,
                                t.TRACK_NAME      as name,
                                genre_name,
                                author_name,
                                coalesce(rate, 0) as rate
                         from Tracks_with_Genre_And_Author t
                                  left join RATING on t.TRACK_ID = RATING.TRACK_ID and RATING.USER_ID = user_id_
                         where (search_by_ = 'name' and t.TRACK_NAME like '%' || search_value_ || '%' or
                                search_by_ = 'genre' and t.GENRE_NAME like '%' || search_value_ || '%' or
                                search_by_ = 'author' and t.AUTHOR_NAME like '%' || search_value_ || '%')
                         and (min_rate_ <= coalesce(rate, 0) and max_rate_ >= coalesce(rate, 0))

                         order by rate desc, ID asc
                         offset tracks_to_skip_ rows fetch next tracks_to_fetch_ rows only;
    else
        open tracks_ for select t.TRACK_ID        as id,
                                t.TRACK_NAME      as name,
                                genre_name,
                                author_name,
                                coalesce(rate, 0) as rate
                         from Tracks_with_Genre_And_Author t
                                  left join RATING on t.TRACK_ID = RATING.TRACK_ID and RATING.USER_ID = user_id_
                         where (search_by_ = 'name' and t.TRACK_NAME like '%' || search_value_ || '%' or
                                search_by_ = 'genre' and t.GENRE_NAME like '%' || search_value_ || '%' or
                                search_by_ = 'author' and t.AUTHOR_NAME like '%' || search_value_ || '%')
                           and (min_rate_ <= coalesce(rate, 0) and max_rate_ >= coalesce(rate, 0))
                         order by rate asc , ID asc
                         offset tracks_to_skip_ rows fetch next tracks_to_fetch_ rows only;
    end if;

end;
/

create procedure GET_TRACK_COUNT(user_id_ number, search_by_ varchar2, search_value_ varchar2, min_rate_ number,
                                 max_rate_ number, track_count_ OUT number ) as
begin
    select count(*)
    into track_count_
    from Tracks_with_Genre_And_Author t
             left join RATING on t.TRACK_ID = RATING.TRACK_ID and RATING.USER_ID = user_id_
    where (search_by_ = 'name' and t.TRACK_NAME like '%' || search_value_ || '%' or
           search_by_ = 'genre' and t.GENRE_NAME like '%' || search_value_ || '%' or
           search_by_ = 'author' and t.AUTHOR_NAME like '%' || search_value_ || '%')
      and (min_rate_ <= coalesce(rate, 0) and max_rate_ >= coalesce(rate, 0));

end;
/

create procedure GET_TRACK_FILE(track_id_ number, track_file_ OUT BLOB)
is
begin
select TRACK_FILE into track_file_ from tracks join TRACK_FILES on tracks.TRACK_FILE_ID = track_files.ID where tracks.ID = track_id_;
end;
/

create procedure SET_TRACK_RATING(user_id_ number, track_id_ number, rate_ number,
                                  rating_ OUT SYS_REFCURSOR) as
begin

    insert into RATING (USER_ID, TRACK_ID, RATE) values (user_id_, track_id_, rate_);
    commit;
    open rating_ for select t.TRACK_ID        as id,
                            t.TRACK_NAME      as name,
                            genre_name,
                            author_name,
                            coalesce(rate, 0) as rate
                     from Tracks_with_Genre_And_Author t
                              left join RATING on t.TRACK_ID = RATING.TRACK_ID and RATING.USER_ID = user_id_
                     where t.TRACK_ID = track_id_
                       and rate_ = rate
                       and user_id_ = user_id_;

end;
/

create PROCEDURE GET_RATING_FOR_TRACK_FROM_USER(user_id_ number,track_id_ number,rating_ out sys_refcursor) is
begin
open rating_ for
select * from RATING where USER_ID=user_id_ and TRACK_ID=track_id_;
end;
/

create procedure UPDATE_TRACK_RATING( rating_id_ number,rating number, rating_ OUT SYS_REFCURSOR) as
begin

    update RATING set RATE = rating where id = rating_id_;
    commit;
    open rating_ for select t.TRACK_ID        as id,
                            t.TRACK_NAME      as name,
                            genre_name,
                            author_name,
                            coalesce(r.rate, 0) as rate
                     from Tracks_with_Genre_And_Author t
                               join RATING r on t.TRACK_ID = r.TRACK_ID and r.ID=rating_id_;
end;
/

create procedure GET_USER_ID_BY_LOGIN(login_ varchar, user_id_ out number)
    is
begin
    select id into user_id_ from users where login = login_;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        user_id_ := null;
end;
/

create procedure GET_USER_BY_ID(user_id_ in number, user_out out sys_refcursor)
    is
begin
    open user_out for select * from users where id = user_id_;
end;
/

create procedure CREATE_AUTHOR(name_ varchar2,author_ out sys_refcursor) is
begin
    insert into AUTHORS(name) values(name_);
    open author_ for select * from AUTHORS where name=name_;
end;
/

create procedure CREATE_GENRE(name_ varchar,genre_     out sys_refcursor) is
begin
    insert into GENRES(name) values(name_);
    open genre_ for select * from GENRES where name=name_;
end;
/

create procedure CREATE_TRACK_FILE(track_file_ BLOB , track_id out number ) is
begin
insert into TRACK_FILES (track_file) values (track_file_) returning id into track_id;
end;
/

create procedure CREATE_TRACK(track_name_ varchar2, genre_id_ number, author_id_ number,
                                         track_file_id_ number, track_ out sys_refcursor) is
begin
    insert into tracks (NAME, GENRE_ID, AUTHOR_ID, TRACK_FILE_ID)
    values (track_name_, genre_id_, author_id_, track_file_id_);
    open track_ for select * from tracks where id = (select max(id) from tracks);
end;
/

create procedure GET_TRACKS_WITH_LARGEST_NUMBER_OF_RATING(tracks_ out sys_refcursor) as
begin
open tracks_ for
    select * from TRACKS_WITH_LARGE_NUMBER_OF_RATINGS;
end;
/

create procedure GET_TRACKS_WITH_LARGEST_AVERAGE_RATING(tracks_ out sys_refcursor) as
begin
    open tracks_ for
        select * from TRACKS_WITH_LARGE_AVERAGE_RATING;
end;
/

create PROCEDURE GET_GENRES_WITH_LARGEST_NUMBER_OF_TRACKS(genres_ out sys_refcursor)
as
BEGIN
OPEN genres_ FOR
    select * from GENRES_WITH_LARGE_NUMBER_OF_TRACKS;
end;
/

create procedure GET_GENRES_WITH_LARGEST_NUMBER_OF_RATING(genres_ out sys_refcursor)
as
begin
open genres_ for
    select * from GENRES_WITH_LARGE_NUMBER_OF_RATINGS;
end;
/

create procedure GET_AUTHORS_WITH_LARGEST_NUMBER_OF_TRACKS(authors_ out sys_refcursor)
as
begin
    open authors_ for
        select * from AUTHORS_WITH_LARGE_NUMBER_OF_TRACKS;
end;
/

create procedure GET_AUTHORS_WITH_LARGEST_NUMBER_OF_RATING(authors_ out sys_refcursor)
as
begin
    open authors_ for
        select * from AUTHORS_WITH_LARGE_NUMBER_OF_RATINGS;
end;
/

create procedure GET_USERS_WITH_LARGEST_NUMBER_OF_RATING(users_ out sys_refcursor)
as
begin
    open users_ for
        select * from USERS_WITH_LARGE_NUMBER_OF_RATINGS;
end;
/

create PROCEDURE ADD_GENRE(name_ varchar2,genre_ out sys_refcursor)
is
BEGIN
    insert into GENRES(name) values(name_);
    open genre_ for select * from GENRES where name=name_;
end;
/

create PROCEDURE ADD_AUTHOR(name_ varchar2,author_ out sys_refcursor)
is
BEGIN
    insert into AUTHORS(name) values(name_);
    open author_ for select * from AUTHORS where name=name_;
end;
/

create procedure GET_PLAYLISTS (user_id_ number,playlists_ out sys_refcursor)is
begin
open playlists_ for
select * from playlists where PLAYLISTS.USER_ID=user_id_;
end;
/

create procedure GET_PLAYLIST_BY_ID(playlist_id number,playlist_ out sys_refcursor) is
begin
open playlist_ for
select * from PLAYLISTS where PLAYLISTS.ID=playlist_id;
end;
/

create procedure GET_PLAYLIST_TRACKS_FOR_USER(playlist_id_ number, user_id_ number, tracks_ out sys_refcursor) is
begin
    open tracks_ for
        select t.TRACK_ID        as id,
               t.TRACK_NAME      as name,
               genre_name,
               author_name,
               coalesce(rate, 0) as rate
        from Tracks_with_Genre_And_Author t
                 left join RATING on t.TRACK_ID = RATING.TRACK_ID and RATING.USER_ID = user_id_
                 join PLAYLIST_TRACKS
                      on t.TRACK_ID = PLAYLIST_TRACKS.TRACK_ID and PLAYLIST_TRACKS.PLAYLIST_ID = playlist_id_;
end;
/

create procedure DELETE_TRACK_FROM_PLAYLIST(user_id_ number, playlist_id_ number,
                                            track_id_ number, deleted_track_ out sys_refcursor)
    is
begin
    open deleted_track_ for
        select t.TRACK_ID        as id,
               t.TRACK_NAME      as name,
               genre_name,
               author_name,
               coalesce(rate, 0) as rate
        from Tracks_with_Genre_And_Author t
                 left join RATING on t.TRACK_ID = RATING.TRACK_ID and RATING.USER_ID = user_id_
                 join PLAYLIST_TRACKS
                      on t.TRACK_ID = PLAYLIST_TRACKS.TRACK_ID and PLAYLIST_TRACKS.PLAYLIST_ID = playlist_id_ and PLAYLIST_TRACKS.TRACK_ID = track_id_;
    
    delete from PLAYLIST_TRACKS where playlist_id = playlist_id_ and track_id = track_id_;
end;
/

create procedure ADD_TRACK_TO_PLAYLIST(user_id_ number, playlist_id_ number,
                                       track_id_ number, added_track_ out sys_refcursor)
    is
begin
    insert into PLAYLIST_TRACKS (playlist_id, track_id) values (playlist_id_, track_id_);

    open added_track_ for
        select t.TRACK_ID        as id,
               t.TRACK_NAME      as name,
               genre_name,
               author_name,
               coalesce(rate, 0) as rate
        from Tracks_with_Genre_And_Author t
                 left join RATING on t.TRACK_ID = RATING.TRACK_ID and RATING.USER_ID = user_id_
                 join PLAYLIST_TRACKS
                      on t.TRACK_ID = PLAYLIST_TRACKS.TRACK_ID and PLAYLIST_TRACKS.PLAYLIST_ID = playlist_id_ and
                         PLAYLIST_TRACKS.TRACK_ID = track_id_;
end;
/

create procedure GET_USER_BY_LOGIN(login_ in varchar, user_out out sys_refcursor)
    is
begin
    open user_out for select * from users where LOGIN = login_;
end;
/

create procedure CREATE_PLAYLIST(name_ varchar,user_id_ number,playlist_ out sys_refcursor)
is
begin
    insert into PLAYLISTS(NAME,USER_ID) values(name_,user_id_);
    open playlist_ for select * from PLAYLISTS where name=name_ and user_id=user_id_;
end;
/

create procedure GET_TRACK_BY_ID(user_id_ number, track out sys_refcursor) is
begin
    open track for
        select * from tracks where ID = user_id_;
end;
/

create procedure GET_GENRES(playlists_ out sys_refcursor) is
begin
    open playlists_ for
        select * from GENRES;
end;
/

create procedure GET_AUTHORS(playlists_ out sys_refcursor) is
begin
    open playlists_ for
        select * from AUTHORS;
end;
/

create procedure TRACKS_TO_JSON( user_id_ number,tracks_ out sys_refcursor) is
begin
    open tracks_ for
        select json_object('id' value t.TRACK_ID,
                           'trackName' value t.TRACK_NAME,
                           'genreName' value genre_name,
                           'author_name' value author_name,
                           'rating' value coalesce(rate, 0)
                   ) as track

        from Tracks_with_Genre_And_Author t
                 left join RATING on t.TRACK_ID = RATING.TRACK_ID and RATING.USER_ID = user_id_;
end;
/

create procedure JSON_TO_TRACKS(json_element JSON_ELEMENT_T)
    is
begin
    DBMS_OUTPUT.PUT_LINE(json_element.TO_STRING());
end;
/


