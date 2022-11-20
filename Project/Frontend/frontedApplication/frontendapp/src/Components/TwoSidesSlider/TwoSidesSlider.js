import Slider from 'rc-slider';

import React from "react";
import "./TwoSidesSlider.css"
import 'rc-slider/assets/index.css';
import {useState} from "react";

export const TwoSidesSlider = ({ratingRangeValue, setRatingRangeValue}) => {


    const marks = {

        0: '-',
        1: '0',
        2: '1',
        3: '2',
        4: '3',
        5: '4',
        6: '5',
        7: '6',
        8: '7',
        9: '8',
        10: '9',
        11: '10',
    }


    const onSliderChange = (value) => {
        setRatingRangeValue(value);
    };


    return (
        <Slider range allowCross={false} dots={true} min={0} max={11} marks={marks} value={ratingRangeValue}
                onChange={onSliderChange}/>
    )
}

