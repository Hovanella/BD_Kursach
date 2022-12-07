--Пользователи с самым большим кол-вом оценок
create view USERS_WITH_LARGE_NUMBER_OF_RATINGS as
select USERS.ID, LOGIN, count(RATE)
from USERS
         left join
     RATING on USERS.ID = RATING.USER_ID
group by USERS.ID, LOGIN
order by count(RATE) desc
    fetch next 10 rows only;

--Треки с самым большим кол-вом оценок
create view TRACKS_WITH_LARGE_NUMBER_OF_RATINGS as
select t1.ID as TRACK_ID, t1.NAME as TRACK_NAME, t2.NAME as GENRE_NAME, t3.NAME as AUTHOR_NAME, t1.RATE_COUNT
from (select TRACKS.ID, TRACKS.NAME, TRACKS.GENRE_ID, TRACKS.AUTHOR_ID, count(RATE) as RATE_COUNT
      from TRACKS
               left join
           RATING on TRACKS.ID = RATING.TRACK_ID
      group by TRACKS.ID, TRACKS.NAME, TRACKS.GENRE_ID, TRACKS.AUTHOR_ID
      order by count(RATE) desc
          fetch next 10 rows only) t1
         join GENRES t2 on t1.GENRE_ID = t2.ID
         join AUTHORS t3 on t1.AUTHOR_ID = t3.ID;

--Треки с самой большой средней оценкой
create view TRACKS_WITH_LARGE_AVERAGE_RATING as
select t1.ID as TRACK_ID, t1.NAME as TRACK_NAME, t2.NAME as GENRE_NAME, t3.NAME as AUTHOR_NAME, t1.AVERAGE_RATE
from (select TRACKS.ID, NAME, GENRE_ID, AUTHOR_ID, coalesce(avg(RATE), 0) as AVERAGE_RATE
      from TRACKS
               left join
           RATING on TRACKS.ID = RATING.TRACK_ID
      group by TRACKS.ID, NAME, GENRE_ID, AUTHOR_ID
      order by AVERAGE_RATE desc
          fetch next 10 rows only) t1
         join GENRES t2 on t1.GENRE_ID = t2.ID
         join AUTHORS t3 on t1.AUTHOR_ID = t3.ID;

--Жанры с самым большим кол-вом треков
create view GENRES_WITH_LARGE_NUMBER_OF_TRACKS as
select GENRES.ID, GENRES.NAME, count(TRACKS.ID) as TRACKS_COUNT
from GENRES
         left join
     TRACKS on GENRES.ID = TRACKS.GENRE_ID
group by GENRES.ID, GENRES.NAME
order by count(TRACKS.ID) desc
    fetch next 10 rows only;

--Жанры с самым большим кол-вом оценок
create view GENRES_WITH_LARGE_NUMBER_OF_RATINGS as
select GENRES.ID, GENRES.NAME, count(RATING.RATE) as RATING_COUNT
from GENRES
         left join
     TRACKS on GENRES.ID = TRACKS.GENRE_ID
         left join
     RATING on TRACKS.ID = RATING.TRACK_ID
group by GENRES.ID, GENRES.NAME
order by count(RATING.RATE) desc
    fetch next 10 rows only;

--Авторы с самым большим кол-вом треков
create view AUTHORS_WITH_LARGE_NUMBER_OF_TRACKS as
select AUTHORS.ID, AUTHORS.NAME, count(TRACKS.ID) as TRACKS_COUNT
from AUTHORS
         left join
     TRACKS on AUTHORS.ID = TRACKS.AUTHOR_ID
group by AUTHORS.ID, AUTHORS.NAME
order by count(TRACKS.ID) desc
    fetch next 10 rows only;

--Авторы с самым большим кол-вом оценок
create view AUTHORS_WITH_LARGE_NUMBER_OF_RATINGS as
select AUTHORS.ID, AUTHORS.NAME, count(RATING.RATE) as RATING_COUNT
from AUTHORS
         left join
     TRACKS on AUTHORS.ID = TRACKS.AUTHOR_ID
         left join
     RATING on TRACKS.ID = RATING.TRACK_ID
group by AUTHORS.ID, AUTHORS.NAME
order by count(RATING.RATE) desc
    fetch next 10 rows only;






