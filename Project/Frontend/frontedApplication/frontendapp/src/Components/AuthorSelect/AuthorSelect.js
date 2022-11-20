import React, {forwardRef} from "react";

export const AuthorSelect = forwardRef((props, ref) => {

    let {authors, disabled} = props;

    if (authors === undefined) {
        authors = [];
    }
    return (
        <select ref={ref} className={disabled ? "disableAuthor" : "select"}>
            {authors && authors.map(author => (
                <option key={author.id} value={author.id}>{author.name}</option>
            ))}
        </select>
    );

});

