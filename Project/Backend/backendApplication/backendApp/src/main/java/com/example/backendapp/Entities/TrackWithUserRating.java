package com.example.backendapp.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@NamedStoredProcedureQuery(name = "GET_TRACKS_FOR_USER", procedureName = "GET_TRACKS_FOR_USER", resultClasses = TrackWithUserRating.class, parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "user_id_", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "tracks_to_skip_", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "tracks_to_fetch_", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "search_by_", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "search_value_", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "order_", type = String.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "min_rate_", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "max_rate_", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "tracks_", type = void.class)
})

@NamedStoredProcedureQuery(name = "UPDATE_TRACK_RATING", procedureName = "UPDATE_TRACK_RATING", resultClasses = TrackWithUserRating.class, parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "rating_id_", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "rating", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "rating_", type = void.class)})

@NamedStoredProcedureQuery(name = "SET_TRACK_RATING", procedureName = "SET_TRACK_RATING", resultClasses = TrackWithUserRating.class, parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "user_id_", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "track_id_", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "rate_", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "rating_", type = void.class)})


@Entity
@Setter
@Getter
public class TrackWithUserRating {
    @Id
    private Long id;

    private String name;

    private String genre_name;

    private String author_name;

    private Long rate;

}
