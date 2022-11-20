import React, { useEffect, useRef, useState } from "react";
import { AllTracksForUser } from "../../Components/AllTracksForUser";
import TrackService from "../../Services/TrackService";
import "./HomePage.css"
import { Search } from "../../Components/Search/Search";
import { NavigationArrows } from "../../Components/NavigationArrows/NavigationArrows";
import { useQuery } from "react-query";


const Home = () => {


    const [searchValue, setSearchValue] = useState("");
    const [filterType, setFilterType] = useState("name");
    const [order, setOrder] = useState("ascending");
    const [ratingRangeValue, setRatingRangeValue] = useState([0, 11]);
    const [currentPage, setCurrentPage] = useState(0);
    const [content, setContent] = useState([]);
    const [trackCount, setTrackCount] = useState(0);

    const TracksPerPage = 10;

    const {refetch:refetchTrackCount} = useQuery(["getTracksCount"], () => TrackService.getTrackCount(filterType, searchValue, ratingRangeValue[0], ratingRangeValue[1]));
    const {refetch:refetchTracks} = useQuery(["getTracks"], () => TrackService.getTracksPage(currentPage, TracksPerPage, filterType, searchValue, order, ratingRangeValue[0], ratingRangeValue[1]));





    useEffect(() => {
        refetchTrackCount().then((data) => {
            setTrackCount(data.data);
        });
        refetchTracks().then((data) => {
            console.log("content", data.data);
            setContent(data.data);
        });
    },[currentPage]);

    const changeRating = (id, rating) => {
        const newData = content.map(item => {
            if (item.id === id) {
                item.rating = rating;
            }
            return item;
        });
    }

    const ChangePage = (page) => {
        setCurrentPage(page);
    }

    const DoSearch = () => {
        if(currentPage !== 0){
            setCurrentPage(0);
        }
        else{
            refetchTrackCount().then((data) => {
                setTrackCount(data.data);
            });
            refetchTracks().then(r => {
                console.log("content", r.data);
                setContent(r.data);
            });
        }
    }


    return (<div className="container">

        <div className="searchBlock">
            <h3>Tracks Search</h3>
            <Search DoSearch={DoSearch} setCurrentPage={setCurrentPage} setSearchValue={setSearchValue} setFilterType={setFilterType}
                    setOrder={setOrder} ratingRangeValue={ratingRangeValue}
                    setRatingRangeValue={setRatingRangeValue}></Search>
        </div>


        <NavigationArrows amountOfPages={trackCount / TracksPerPage} currentPage={currentPage} setCurrentPage={setCurrentPage}
                          ChangePage={ChangePage}/>

        <ul className="trackList" id="Tracks">
            {content && <AllTracksForUser changeRating={changeRating} content={content}/>}
        </ul>

        <NavigationArrows amountOfPages={trackCount / TracksPerPage} currentPage={currentPage} setCurrentPage={setCurrentPage}/>

    </div>);
};
export default Home;

