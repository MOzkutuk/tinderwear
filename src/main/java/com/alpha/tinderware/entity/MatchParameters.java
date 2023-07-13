package com.alpha.tinderware.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "matchParameters")
@Getter
@Setter
@ToString
public class MatchParameters {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idp;
    private String collorPalette;
    private String bodyShape;
    private String height;

}
