--make a loop for 10000 iterations and insert in tracks table

BEGIN
    FOR i IN 1 .. 10000
        LOOP
            insert into tracks (name, genre_id, author_id, track_file_id) VALUES ('Track+ ' || i, 21, 21, 1);
        END LOOP;
END;




