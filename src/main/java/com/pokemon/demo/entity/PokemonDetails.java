package com.pokemon.demo.entity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;

@Setter
@Getter
@Entity
@Table(name = "pokemon")
public class PokemonDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "details")
    private String details;

    @Column(name = "picture",nullable = true, length = 64)
    private String photo;


    //image bytes can have large lengths so we specify a value
    //which is more than the default length for picByte column
    @Column(name = "pic_byte", length = 1000)
    private byte[] picByte;

    public PokemonDetails() {
    }

    public PokemonDetails(String name,String details, String photo) {
        this.name = name;
        this.details = details;
        this.photo = photo;
    }

    public PokemonDetails(String name,String details, byte[] picByte) {
        this.name = name;
        this.details = details;
        this.picByte = picByte;
    }


    @Transient
    public String getPhotosImagePath() {
        if (photo == null || id == null) return null;

        return "/pokemon-photos/" + id + "/" + photo;
    }

    @Override
    public String toString() {
        return "PokemonDetails{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", photos='" + photo + '\'' +
                ", picByte=" + Arrays.toString(picByte) +
                '}';
    }
}

