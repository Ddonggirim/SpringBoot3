package org.example.springbootdev;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity()
public class Member{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Member_SQ")

    @Column(name = "id", updatable = false)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;

    public void changeName(String name){
        this.name = name;
    }
}
