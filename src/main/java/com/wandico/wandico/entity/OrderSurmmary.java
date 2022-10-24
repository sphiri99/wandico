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
@Table(name = "order_details")
public class OrderSurmmary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_name")
    private String name;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "quantity")
    private int quantity =0;

    @Column(name = "est_turnaround_time")
    private Date estTurnaroundTime;

    @Column(name = "due_date")
    private Date dueDate;

    //This status will either be pending, in-progress, complete or stuck
    @Column(name = "status")
    private String status;

    public OrderSurmmary() {
    }

    @Override
    public String toString() {
        return "OrderSurmary{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", itemName='" + itemName + '\'' +
                ", createdOn=" + createdOn +
                ", quantity=" + quantity +
                ", estTurnaroundTime=" + estTurnaroundTime +
                ", dueDate=" + dueDate +
                ", status='" + status + '\'' +
                '}';
    }
}

