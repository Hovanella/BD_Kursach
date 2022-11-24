package com.example.backendapp.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@NamedStoredProcedureQueries(@NamedStoredProcedureQuery(name = "GET_RATING_FOR_TRACK_FROM_USER", procedureName = "GET_RATING_FOR_TRACK_FROM_USER", resultClasses = Rating.class, parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "user_id_", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "track_id_", type = Long.class),
        @StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "rating_", type = void.class),
}))
@Entity
@Getter
@Setter
@Table(name = "RATING")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TRACK_ID", nullable = false)
    private Track track;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "RATE", nullable = false)
    private Integer rate;


}