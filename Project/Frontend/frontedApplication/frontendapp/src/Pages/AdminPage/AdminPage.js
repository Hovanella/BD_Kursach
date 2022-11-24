import React from "react";
import AuthenticationService from "../../Services/AuthenticationService";
import {NewTrackForm} from "../../Components/NewTrackForm/NewTrackForm";
import "./AdminPage.css"

export const  AdminPage = () => {
    return (
        <div className="formsContainer">
        <NewTrackForm></NewTrackForm>
        </div>
    );
};
