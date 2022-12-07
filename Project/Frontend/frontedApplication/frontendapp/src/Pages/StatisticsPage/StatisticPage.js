import React from "react";
import { useQuery } from "react-query";
import StatisticsService from "../../Services/StatisticsService";
import "./StatisticPage.css";
import styles from "./StatisticPage.module.css";


export const StatisticsPage = () => {

    const {data: tracksWithLargestAverageRating} = useQuery(["tracksWithLargestAverageRating"], async () =>
        await StatisticsService.getTracksWithLargestAverageRating());

    const {data: tracksWithLargestNumberOfRatings} = useQuery(["tracksWithLargestNumberOfRatings"], async () =>
        await StatisticsService.getTracksWithLargestNumberOfRatings());

    const {data: genresWithLargestNumberOfTracks} = useQuery(["genresWithLargestNumberOfTracks"], async () =>
        await StatisticsService.getGenresWithLargestNumberOfTracks());

    const {data: genresWithLargestNumberOfRatings} = useQuery(["genresWithLargestNumberOfRatings"], async () =>
        await StatisticsService.getGenresWithLargestNumberOfRatings());

    const {data: authorsWithLargestNumberOfTracks} = useQuery(["authorsWithLargestNumberOfTracks"], async () =>
        await StatisticsService.getAuthorsWithLargestNumberOfTracks());

    const {data: authorsWithLargestNumberOfRatings} = useQuery(["authorsWithLargestNumberOfRatings"], async () =>
        await StatisticsService.getAuthorsWithLargestNumberOfRatings());

    const {data: usersWithLargestNumberOfRatings} = useQuery(["usersWithLargestNumberOfRatings"], async () =>
        await StatisticsService.getUsersWithLargestNumberOfRatings());


    return (
        <div className={styles.wrapper}>
            <nav>
                <div className={styles.linkWrapper}><a className={styles.link} href="/admin">Post Track</a></div>

               <div className={styles.linkWrapper}><a className={`${styles.link} ${styles.disabledLink}`}>View Statistic</a></div>
            </nav>

            <h2 className={styles.statisticHeader}>Tracks With The Largest Average Rating </h2>
            <table>
                <thead>
                <tr>
                    <th>Track Name</th>
                    <th>Author Name</th>
                    <th>Genre Name</th>
                    <th>Average Rating</th>
                </tr>
                </thead>
                <tbody>
                {tracksWithLargestAverageRating?.data.map(track => (
                    <tr key={track.trackId}>
                        <td>{track.trackName}</td>
                        <td>{track.authorName}</td>
                        <td>{track.genreName}</td>
                        <td>{track.averageRate}</td>
                    </tr>
                ))}
                </tbody>
            </table>


            <h2 className={styles.statisticHeader}>Tracks With The Largest Number Of Ratings </h2>
            <table>
                <thead>
                <tr>
                    <th>Track Name</th>
                    <th>Author Name</th>
                    <th>Genre Name</th>
                    <th>Number of Ratings</th>
                </tr>
                </thead>
                <tbody>
                {tracksWithLargestNumberOfRatings?.data.map(track => (
                    <tr key={track.trackId}>
                        <td>{track.trackName}</td>
                        <td>{track.authorName}</td>
                        <td>{track.genreName}</td>
                        <td>{track.rateCount}</td>
                    </tr>
                ))}
                </tbody>
            </table>

            <h2 className={styles.statisticHeader}>Genres With The Largest Number Of Tracks </h2>
            <table>
                <thead>
                <tr>
                    <th>Genre Name</th>
                    <th>Number of Tracks</th>
                </tr>
                </thead>
                <tbody>
                {genresWithLargestNumberOfTracks?.data.map(genre => (
                    <tr key={genre.id}>
                        <td>{genre.name}</td>
                        <td>{genre.tracksCount}</td>
                    </tr>
                ))}
                </tbody>
            </table>

            <h2 className={styles.statisticHeader}>Genres With The Largest Number Of Ratings </h2>
            <table>
                <thead>
                <tr>
                    <th>Genre Name</th>
                    <th>Number of Ratings</th>
                </tr>
                </thead>
                <tbody>
                {genresWithLargestNumberOfRatings?.data.map(genre => (
                    <tr key={genre.id}>
                        <td>{genre.name}</td>
                        <td>{genre.ratingCount}</td>
                    </tr>
                ))}
                </tbody>
            </table>

            <h2 className={styles.statisticHeader}>Authors With The Largest Number Of Tracks </h2>
            <table>
                <thead>
                <tr>
                    <th>Author Name</th>
                    <th>Number of Tracks</th>
                </tr>
                </thead>
                <tbody>
                {authorsWithLargestNumberOfTracks?.data.map(author => (
                    <tr key={author.id}>
                        <td>{author.name}</td>
                        <td>{author.tracksCount}</td>
                    </tr>
                ))}
                </tbody>
            </table>

            <h2 className={styles.statisticHeader}>Authors With The Largest Number Of Ratings </h2>
            <table>
                <thead>
                <tr>
                    <th>Author Name</th>
                    <th>Number of Ratings</th>
                </tr>
                </thead>
                <tbody>
                {authorsWithLargestNumberOfRatings?.data.map(author => (
                    <tr key={author.id}>
                        <td>{author.name}</td>
                        <td>{author.ratingCount}</td>
                    </tr>
                ))}
                </tbody>
            </table>

            <h2 className={styles.statisticHeader}>Users With The Largest Number Of Ratings </h2>
            <table>
                <thead>
                <tr>
                    <th>User Name</th>
                    <th>Number of Ratings</th>
                </tr>
                </thead>
                <tbody>
                {usersWithLargestNumberOfRatings?.data.map(user => (
                    <tr key={user.id}>
                        <td>{user.login}</td>
                        <td>{user.countRate}</td>
                    </tr>
                ))}
                </tbody>
            </table>


        </div>
    );

}