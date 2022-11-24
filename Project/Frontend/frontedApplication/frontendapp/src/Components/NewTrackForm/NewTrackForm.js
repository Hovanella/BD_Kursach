import {useEffect, useRef, useState} from "react";
import React from "react";
import "./NewTrackForm.css"
import {AuthorSelect} from "../AuthorSelect/AuthorSelect";
import {GenreSelect} from "./GenreSelect";
import AuthorService from "../../Services/AuthorService";
import GenreService from "../../Services/GenreService";
import TrackService from "../../Services/TrackService";



export const NewTrackForm = () => {

    useEffect(() => {
        AuthorService.getAllAuthors().then(response => {
            setAuthors(response.data);
        });

        GenreService.getAllGenres().then(response => {
            setGenres(response.data);
        });


    }, []);

    const [authors, setAuthors] = useState([]);
    const [genres, setGenres] = useState([]);
    const [fileName, setFileName] = useState('');
    const [newAuthorInputEnabled, setNewAuthorInputEnabled] = useState(false);
    const [newGenreInputEnabled, setNewGenreInputEnabled] = useState(false);

    const file = useRef();
    const newAuthor = useRef();
    const existingAuthor = useRef();
    const newGenre = useRef();
    const existingGenre = useRef();
    const trackName = useRef();


    const fileChangeHandler = async (event) => {

        const pathToFile = file.current.value ;

        const pathToFileSplited = pathToFile.split("\\");

        const fileName = pathToFileSplited[pathToFileSplited.length - 1];



        setFileName(fileName);

    }

    const newAuthorButtonClickHandler = (e) => {
        e.preventDefault();
        if (newAuthorInputEnabled === false) {
            setNewAuthorInputEnabled(true);
        }
    };
    const existingAuthorButtonClickHandler = (e) => {
        e.preventDefault();
        if (newAuthorInputEnabled === true) {
            setNewAuthorInputEnabled(false);
        }
    }

    const newGenreButtonClickHandler = (e) => {
        e.preventDefault();
        if (newGenreInputEnabled === false) {
            setNewGenreInputEnabled(true);
        }
    }
    const existingGenreButtonClickHandler = (e) => {
        e.preventDefault();
        if (newGenreInputEnabled === true) {
            setNewGenreInputEnabled(false);
        }
    }

    const onSubmitHandler = async (e) => {
        e.preventDefault();

        let genreId, authorId, name, path, absolutePath;

        name = trackName.current.value;
        absolutePath = file.current.value;
        path = fileName;


        if (newAuthorInputEnabled) {
            await AuthorService.AddAuthor(newAuthor.current.value).then((response) => {
                    authorId = response.data.id;
                }
            );
        } else {

            authorId = existingAuthor.current.value;

        }


        if (newGenreInputEnabled) {
            await GenreService.AddGenre(newGenre.current.value).then((response) => {
                    genreId = response.data.id;
                }
            );
        } else {
            genreId = existingGenre.current.value;
        }


        await TrackService.postTrack(genreId, authorId, name, path, absolutePath);

        const formData = new FormData();
        formData.append("file", file.current.files[0]);
        await TrackService.CopyTrackInAudio(formData);

    }


    return (
        <form datatype="multipart/form-data" method="post" onSubmit={onSubmitHandler} className="newTrackForm">

            <input ref={file} type="file" id="file" className="fileInput" onChange={fileChangeHandler}
                   accept="audio/mp3"/>
            <label className="fileNameLabel" htmlFor="file">
                <span className="fileNameSpan"> {fileName || "Choose a File"}</span>
            </label>


            <h2>Author</h2>
            <div className="authorSelectButton">
                <button className="selectButton" onClick={newAuthorButtonClickHandler}>New</button>
                <button className="selectButton" onClick={existingAuthorButtonClickHandler}>Existing</button>
            </div>

            <div className="authorSelect">
                <input ref={newAuthor} className={!newAuthorInputEnabled ? "disableAuthor" : "authorInput"} type="text"
                       placeholder="New Author"/>
                <AuthorSelect authors={authors} ref={existingAuthor} disabled={newAuthorInputEnabled}/>
            </div>


            <h2>Genre</h2>
            <div className="genreSelectButton">
                <button className="selectButton" onClick={newGenreButtonClickHandler}> New</button>
                <button className="selectButton" onClick={existingGenreButtonClickHandler}>Existing</button>
            </div>

            <div className="genreSelect">
                <input ref={newGenre} className={!newGenreInputEnabled ? "disableAuthor" : "genreInput"} type="text"
                       placeholder="New Genre"/>
                <GenreSelect genres={genres} ref={existingGenre} disabled={newGenreInputEnabled}/>
            </div>

            <h2>Name</h2>
            <input ref={trackName} type="text" className="nameInput" placeholder="Name"/>


            <div className="submitButton">
                <button className="submitButton" type="submit">Submit</button>
            </div>
        </form>

    );


}

