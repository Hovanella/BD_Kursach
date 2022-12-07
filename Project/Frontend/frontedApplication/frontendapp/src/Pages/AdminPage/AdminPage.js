import React from "react";
import AuthenticationService from "../../Services/AuthenticationService";
import { NewTrackForm } from "../../Components/NewTrackForm/NewTrackForm";
import "./AdminPage.css"
import styles from "../StatisticsPage/StatisticPage.module.css";

export const AdminPage = () => {
    return (
        <>
            <nav>
                <a className={`${styles.link} ${styles.disabledLink}`}>Post Track</a>
                <a className={styles.link} href="/statistics">View Statistic</a>
            </nav>

            <div className="formsContainer">
                <NewTrackForm></NewTrackForm>
            </div>
        </>
    );
};
