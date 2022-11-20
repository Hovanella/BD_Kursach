import StarRatings from "react-star-ratings/build/star-ratings";
import TrackService from "../../Services/TrackService";
import { useEffect, useState } from "react";
import React from "react";
import "./TrackPlayer.css"
import { useQuery } from "react-query";


export const TrackPlayer = ({item, changeRating,path}) => {
    const wayToAudioFolder = "./Tracks/";

    /*const changeRatingHandler = async (rating) => {
        await TrackService.postOrUpdateRating(rating, item.id).then(
            (response) => {
                setRating(response.data.rate);
                changeRating(item.id, response.data.rate);
            });
    }*/
    const {refetch:refetchTrackFile } = useQuery([`${item.id}`], () => TrackService.CopyTrackInAudio(item.id));

    const source = wayToAudioFolder + item.name + ".mp3";


    const [rating, setRating] = useState(item.rate === -1 ? 0 : item.rate);

    return (
        <li className="trackItem" key={item.id}>

            <a className="downloadTrackLink" href={source} download>
                <img src="./Icons/download-svgrepo-com.svg" className="downloadTrackImage" alt="DownloadTrack" title="Download track"/>
            </a>


            <div className="trackHeaders">
                <h2>{item.name}</h2>
                <h3>{item.author_name}</h3>
                <h3>{item.genre_name}</h3>
            </div>


            <StarRatings starDimension="2em" rating={rating} starRatedColor="blue" starHoverColor="blue" /*changeRating={changeRatingHandler}*/
                         numberOfStars={10}> </StarRatings>

            {<audio className="audioPlayer" controls>
                <source src={source} type="audio/mpeg"/>
            </audio>}

        </li>
    );

}