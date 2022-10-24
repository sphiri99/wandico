package com.wandico.wandico.entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity
@OnDelete(action = OnDeleteAction.CASCADE)
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_rurn_around_time")
    private double prodTurnAroundTime;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", prodTurnAroundTime=" + prodTurnAroundTime +
                '}';
    }
}

