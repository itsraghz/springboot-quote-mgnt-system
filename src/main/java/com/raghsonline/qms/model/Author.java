package com.raghsonline.qms.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    //@JoinColumn(name = "QUOTE_ID", referencedColumnName = "ID")
    private List<Quote> quotes;

    private String firstName;

    private String lastName;
}
