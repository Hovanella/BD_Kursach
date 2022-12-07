import styles from "./PlaylistsPage.module.css";
import { Search } from "../../Components/Search/Search";
import PlaylistsService from "../../Services/PlaylistsService";
import { useQuery } from "react-query";
import React, { useEffect, useState } from "react";
import { TrackPlayer } from "../../Components/TrackPlayer/TrackPlayer";
import TrackService from "../../Services/TrackService";
import { NavigationArrows } from "../../Components/NavigationArrows/NavigationArrows";
import { AllTracksForUser } from "../../Components/AllTracksForUser";

export const PlaylistsPage = () => {

    const {refetch: refetchPlaylists} = useQuery("playlists", () => PlaylistsService.getPlaylists());
    const {refetch: refetchPlaylistTracks} = useQuery(["tracks"], () => PlaylistsService.getPlaylistTracks(selectedPlaylist));
    const {refetch: refetchTrackCount} = useQuery(["getTracksCount"], () => TrackService.getTrackCount(filterType, searchValue, ratingRangeValue[0] - 1, ratingRangeValue[1] - 1));
    const {refetch: refetchTracks} = useQuery(["getTracks"], () => TrackService.getTracksPage(currentPage, TracksPerPage, filterType, searchValue, order, ratingRangeValue[0] - 1, ratingRangeValue[1] - 1));

    const [playlists, setPlaylists] = useState([]);
    const [selectedPlaylist, setSelectedPlaylist] = useState(-1);
    const [playlistTracks, setPlaylistTracks] = useState([]);

    const [searchValue, setSearchValue] = useState("");
    const [filterType, setFilterType] = useState("name");
    const [order, setOrder] = useState("ascending");

    const [ratingRangeValue, setRatingRangeValue] = useState([1, 11]);
    const [currentPage, setCurrentPage] = useState(0);
    const [content, setContent] = useState([]);
    const [trackCount, setTrackCount] = useState(0);

    const [isFormClosed, setIsFormClosed] = useState(true);

    const TracksPerPage = 5;

    useEffect(() => {
        refetchTrackCount().then((data) => {
            setTrackCount(data.data);
        });
        refetchTracks().then((data) => {
            console.log("content", data.data);
            setContent(data.data);
        });
    }, [currentPage]);

    const ChangePage = (page) => {
        setCurrentPage(page);
    }



    const DoSearch = () => {
        if (currentPage !== 0) {
            setCurrentPage(0);
        } else {
            refetchTrackCount().then((data) => {
                setTrackCount(data.data);
            });
            refetchTracks().then(r => {
                console.log("content", r.data);
                setContent(r.data);
            });
        }
    }

    useEffect(() => {
        refetchPlaylistTracks().then((response) => {
            setPlaylistTracks(response.data);
            console.log(response.data);
        });

    }, [selectedPlaylist]);

    useEffect(() => {
        refetchPlaylists().then((response) => {
            setPlaylists(response.data);
        });
    }, []);


    const handlePlaylistButtonClick = (id) => {
        if (id === null || id === setSelectedPlaylist) return;
        setSelectedPlaylist(id);

    }

    const deleteButtonHandler = async (trackId) => {
        if (!trackId || !selectedPlaylist) return;

        try {
            await PlaylistsService.deleteTrackFromPlaylist(trackId, selectedPlaylist);
        } catch {
            alert("DELETION FROM PLAYLIST FAILED TRY AGAIN OR NEVER USE ORACLE")
            return;
        }
        refetchPlaylistTracks().then((response) => {
            setPlaylistTracks(response.data);
            console.log(response.data);
        });

    }

    const addButtonHandler = async (trackId) => {
        if (!trackId || !selectedPlaylist) return;


        try {
            await PlaylistsService.addTrackToPlaylist(trackId, selectedPlaylist);
        } catch {
            alert("ADDING TO PLAYLIST FAILED TRY AGAIN OR NEVER USE ORACLE")
            return;
        }
        refetchPlaylistTracks().then((response) => {
            setPlaylistTracks(response.data);
            console.log(response.data);
        });

    }

    const createNewPlaylist =  () => {
    };
    return (
        <>
            <nav>
                <a href="/home" className={styles.link}>Search Track</a>
                <a className={`${styles.link} ${styles.disabledLink}`}>My Playlists</a>
            </nav>
            <div className={styles.container}>
                <div className={styles.playlistsButtonSection} id="PlaylistsButtons">

                    <button className={styles.createNewPlaylist} onClick={() => setIsFormClosed(prevState => !prevState)}>Create New Playlist</button>
                    <form className={isFormClosed ? `${styles.closedForm}` : `${styles.openForm}`}>
                        <input type="button" onClick={()=>setIsFormClosed(true)} value="Close Form"/>
                        <input type="text" placeholder="Enter Playlist name"/>
                        <input type="submit" value="Create"/>
                    </form>

                    <ul className={styles.playlistsList}>
                        {playlists?.map((playlist) => (
                            <li className={styles.playlist} key={`${playlist.id} playlist`}>
                                <button key={`${playlist.id} playlist deletebutton`} onClick={() => handlePlaylistButtonClick(playlist.id)}
                                        className={playlist.id ===selectedPlaylist ?  `${styles.playlistButton} ${styles.selectedPlaylist}` :styles.playlistButton }>
                                    <h3 key={`${playlist.id} playlist header`}>{playlist.name}</h3>
                                </button>
                            </li>
                        ))}

                    </ul>


                </div>
                <div className={styles.playlistSongsSection} id="PlaylistSongs">
                    <ul >
                        {playlistTracks && playlistTracks.map((item) => (
                            <>
                                <TrackPlayer key={`trackPlayer  ${item.id}`} item={item}/>
                                <button key={`trackPlayer  button ${item.id}`} className={styles.deleteButton}
                                        onClick={() => deleteButtonHandler(item.id)}>Delete
                                </button>
                            </>
                        ))}
                    </ul>
                </div>
                <div className={styles.songsSearchSection} id="SearchSongsForPlaylist">
                    <Search DoSearch={DoSearch} setCurrentPage={setCurrentPage} setSearchValue={setSearchValue} setFilterType={setFilterType}
                                    setOrder={setOrder} ratingRangeValue={ratingRangeValue}
                                    setRatingRangeValue={setRatingRangeValue} />

                    <NavigationArrows amountOfPages={trackCount / TracksPerPage} currentPage={currentPage} setCurrentPage={setCurrentPage}
                                      ChangePage={ChangePage}/>

                    <ul className={`trackList ${styles.scrollable}`} id="Tracks">
                        {content && <AllTracksForUser addButtonHandler={addButtonHandler} playlistTracks={playlistTracks}
                                                      content={content}/>}
                    </ul>

                    <NavigationArrows amountOfPages={trackCount / TracksPerPage} currentPage={currentPage} setCurrentPage={setCurrentPage}
                                      ChangePage={ChangePage}/>

                </div>
            </div>
        </>
    );

}