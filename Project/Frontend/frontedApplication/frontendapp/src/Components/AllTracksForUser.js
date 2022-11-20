import React, { useEffect, useState } from "react";
import { TrackPlayer } from "./TrackPlayer/TrackPlayer";
import TrackService from "../Services/TrackService";
import { useQuery } from "react-query";

export const AllTracksForUser = ({content, changeRating}) => {

    const path="";

    return content.map((item, index) => {
        return (
            <TrackPlayer key={item.id} path={path} changeRating={changeRating} item={item}></TrackPlayer>
        );

    });
}
