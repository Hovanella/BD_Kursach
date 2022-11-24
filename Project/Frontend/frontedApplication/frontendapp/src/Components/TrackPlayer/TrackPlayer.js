import StarRatings from "react-star-ratings/build/star-ratings";
import TrackService from "../../Services/TrackService";
import { useEffect, useState } from "react";
import React from "react";
import "./TrackPlayer.css"
import { useQuery } from "react-query";
import { Comments } from "../Comments/Comments";


export const TrackPlayer = ({item, changeRating}) => {

    const changeRatingHandler = async (rating) => {
        await TrackService.setTrackRating(rating, item.id).then((track) => {
                setRating(track.data.rate);
            }
        );
    }


    const source = `http://localhost:8089/tracks/${item.id}/file`;


    const [rating, setRating] = useState(item.rate === -1 ? 0 : item.rate);

    return (
        <li className="trackItem" key={item.id}>

            <div className="trackHeaders">
                <h2>{item.name}</h2>
                <h3>{item.author_name}</h3>
                <h3>{item.genre_name}</h3>
            </div>


            <StarRatings starDimension="2em" rating={rating} starRatedColor="blue" starHoverColor="blue" changeRating={changeRatingHandler}
                         numberOfStars={10}> </StarRatings>

            {<audio className="audioPlayer" controls>
                <source src={source} type="audio/mpeg"/>
            </audio>}

            <Comments/>
        </li>
    );

}