package com.quaza.solutions.qpalx.elearning.domain.tutoriallevel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * @author manyce400
 */
@Entity
@Table(name="TutorialLevelCalendar")
public class TutorialLevelCalendar {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;


    @Column(name="CalendarItemName", nullable=false, length=50)
    private String calendarItemName;

    @Column(name="CalendarItemDescripiton", nullable=false, length=150)
    private String calendarItemDescripiton;

    @Column(name="CalendarItemStartMonth", nullable=true)
    private String calendarItemStartMonth;

    @Column(name="CalendarItemEndMonth", nullable=true)
    private String calendarItemEndMonth;

    @Column(name="CalendarItemOrder", nullable=false)
    private Integer calendarItemOrder;

    // Fetch this eager as we want to be able to actively look this up always on demand
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "StudentTutorialLevelID", nullable = false)
    private StudentTutorialLevel studentTutorialLevel;


    public TutorialLevelCalendar() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCalendarItemName() {
        return calendarItemName;
    }

    public void setCalendarItemName(String calendarItemName) {
        this.calendarItemName = calendarItemName;
    }

    public String getCalendarItemDescripiton() {
        return calendarItemDescripiton;
    }

    public void setCalendarItemDescripiton(String calendarItemDescripiton) {
        this.calendarItemDescripiton = calendarItemDescripiton;
    }

    public String getCalendarItemStartMonth() {
        return calendarItemStartMonth;
    }

    public void setCalendarItemStartMonth(String calendarItemStartMonth) {
        this.calendarItemStartMonth = calendarItemStartMonth;
    }

    public String getCalendarItemEndMonth() {
        return calendarItemEndMonth;
    }

    public void setCalendarItemEndMonth(String calendarItemEndMonth) {
        this.calendarItemEndMonth = calendarItemEndMonth;
    }

    public Integer getCalendarItemOrder() {
        return calendarItemOrder;
    }

    public void setCalendarItemOrder(Integer calendarItemOrder) {
        this.calendarItemOrder = calendarItemOrder;
    }

    public StudentTutorialLevel getStudentTutorialLevel() {
        return studentTutorialLevel;
    }

    public void setStudentTutorialLevel(StudentTutorialLevel studentTutorialLevel) {
        this.studentTutorialLevel = studentTutorialLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TutorialLevelCalendar that = (TutorialLevelCalendar) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(calendarItemName, that.calendarItemName)
                .append(calendarItemDescripiton, that.calendarItemDescripiton)
                .append(calendarItemStartMonth, that.calendarItemStartMonth)
                .append(calendarItemEndMonth, that.calendarItemEndMonth)
                .append(calendarItemOrder, that.calendarItemOrder)
                .append(studentTutorialLevel, that.studentTutorialLevel)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(calendarItemName)
                .append(calendarItemDescripiton)
                .append(calendarItemStartMonth)
                .append(calendarItemEndMonth)
                .append(calendarItemOrder)
                .append(studentTutorialLevel)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("calendarItemName", calendarItemName)
                .append("calendarItemDescripiton", calendarItemDescripiton)
                .append("calendarItemStartMonth", calendarItemStartMonth)
                .append("calendarItemEndMonth", calendarItemEndMonth)
                .append("calendarItemOrder", calendarItemOrder)
                .append("studentTutorialLevel", studentTutorialLevel)
                .toString();
    }
}
