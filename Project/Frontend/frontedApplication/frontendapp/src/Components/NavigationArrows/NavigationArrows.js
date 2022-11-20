import "./NavigationArrows.css"
import React from "react";

export function NavigationArrows({currentPage, ChangePage, amountOfPages}) {

    const onClickLeft = (e) => {
        e.preventDefault();
        if (currentPage > 0 && currentPage < amountOfPages) {
            ChangePage(currentPage - 1);
        }
    }

    const onClickRight = (e) => {
        e.preventDefault();
        if (currentPage < amountOfPages - 1) {
            ChangePage(currentPage + 1);
        }
    }

    return (
        <div className="arrowsContainer">
            <button  disabled={!(currentPage > 0 && currentPage < amountOfPages)}>
                <img className="leftArrow" onClick={onClickLeft} src="./Icons/left-arrow-svgrepo-com.svg" alt=""/>
            </button>

            <button disabled={!(currentPage < amountOfPages - 1)}>
                <img className="rightArrow" onClick={onClickRight} src="./Icons/right-arrow-svgrepo-com.svg" alt=""/>
            </button>

        </div>
    );
}