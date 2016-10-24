package com.quaza.solutions.qpalx.elearning.domain.lms.adaptivelearning.statistics;

import javax.persistence.*;

@Entity
@Table(name="QPalXELessonStatistic")
public class QPalXELessonStatistic {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID", nullable=false)
    private Long id;



}
