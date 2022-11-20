import React, {forwardRef} from "react";

export const GenreSelect = forwardRef((props, ref) => {

    let {genres, disabled} = props;

    if (genres === undefined) {
        genres = [{id: 0, name: "ads"}, {id: 1, name: "dasds"}, {id: 2, name: "dasds"}];
    }

    return (
        <select ref={ref} className={disabled ? "disableGenre" : "select"}>
            {genres && genres.map(genre => (
                <option key={genre.id} value={genre.id}>{genre.name}</option>
            ))}
        </select>
    );

});


