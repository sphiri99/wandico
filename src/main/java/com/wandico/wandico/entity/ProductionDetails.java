package com.wandico.wandico.entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Setter
@Getter
@Entity
@OnDelete(action = OnDeleteAction.CASCADE)
@Table(name = "prod_details")
public class ProductionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_of_employees")
    private int employees;

    @Column(name = "turn_around_time_per_employee")
    private double turnAroundTimePerEmployee;

    public ProductionDetails() {
    }

    public ProductionDetails(int employees, double turnAroundTimePerEmployee) {
        this.employees = employees;
        this.turnAroundTimePerEmployee = turnAroundTimePerEmployee;
    }

    @Override
    public String toString() {
        return "ProductionDetails{" +
                "id=" + id +
                ", employees=" + employees +
                ", turnAroundTimePerEmployee=" + turnAroundTimePerEmployee +
                '}';
    }
}

