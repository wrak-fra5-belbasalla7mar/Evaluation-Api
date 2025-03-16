package org.fawry.evaluationapi.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ratings")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Ratings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "kpi_id")
    private KPI kpi;

    private long submitterId;
    private long ratedPersonId;
    private String feedback;
}
