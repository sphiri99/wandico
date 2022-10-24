package com.wandico.wandico.entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity
@OnDelete(action = OnDeleteAction.CASCADE)
@Table(name = "user_details_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

/*    @ManyToMany(mappedBy = "roles")
    private Set<UserDetails> users;*/
}
