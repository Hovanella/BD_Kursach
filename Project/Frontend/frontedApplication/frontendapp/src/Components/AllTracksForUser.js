import React, { useEffect, useState } from "react";
import { TrackPlayer } from "./TrackPlayer/TrackPlayer";
import TrackService from "../Services/TrackService";
import { useQuery } from "react-query";

export const AllTracksForUser = ({content, playlistTracks, addButtonHandler}) => {

    const path = "";


    console.log(playlistTracks);
    return content.map((item) => {

        let isButtonNeeded = false;

        if (playlistTracks && !playlistTracks.find(x => x.id === item.id)) {
            isButtonNeeded = true;
            console.log("Элемент с id: ", item.id, "и именем ", item.name, " не найден в плейлисте");
        }


        return (
            !isButtonNeeded ?
                <TrackPlayer key={`TrackPlayer${item.id}`} path={path} item={item}></TrackPlayer>
                :
                <>
                    <TrackPlayer key={`${item.id} with button`} path={path}  item={item}></TrackPlayer>
                    <button onClick={() => addButtonHandler(item.id)} key={`${item.id} button`}>Add</button>
                </>
        );

    });
}
