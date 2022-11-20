import React, { useRef } from "react";
import "./Search.css"
import { TwoSidesSlider } from "../TwoSidesSlider/TwoSidesSlider";

export function Search({
                           DoSearch,
                           setFilterType,
                           setSearchValue,
                           setOrder,
                           ratingRangeValue,
                           setRatingRangeValue
                       }) {

    const searchInput = useRef(null);
    const ChangeSearch = (() => {
        setSearchValue(searchInput.current.value);
    });


    return (<div className="search">
            <input ref={searchInput} className="searchInput" type="search" placeholder="Search..."
                   onChange={ChangeSearch}/>
            <div>

                <div className="searchTypeInputs">
                    <label>
                        <input
                            type="radio"
                            id="TrackName"
                            name="search"
                            onChange={() => setFilterType("name")}
                        />
                        Track name
                    </label>
                    <label>
                        <input
                            type="radio"
                            id="AuthorName"

                            name="search"
                            onChange={() => setFilterType("author")}
                        />
                        Author name
                    </label>
                    <label>
                        <input
                            type="radio"
                            id="GenreName"
                            name="search"
                            onChange={() => setFilterType("genre")}
                        />
                        Genre name
                    </label>
                </div>

                <div className="searchRatingOrderInputs">
                    <label>
                        <input
                            type="radio"
                            id="RatingOrderAscending"
                            name="rating"
                            onChange={() => setOrder("ascending")}
                        />
                        Rating Ascending
                    </label>
                    <label>
                        <input
                            type="radio"
                            id="RatingOrderDescending"
                            name="rating"
                            onChange={() => setOrder("descending")}
                        />
                        Rating Descending
                    </label>
                </div>

                <TwoSidesSlider ratingRangeValue={ratingRangeValue} setRatingRangeValue={setRatingRangeValue}/>

                <input className="searchButton" value="Do search" type="button" onClick={() => DoSearch()}/>
            </div>
        </div>);
}