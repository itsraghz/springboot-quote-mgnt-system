package com.raghsonline.simpleqms;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="quote", length = 2000)
    private String quote;
}
