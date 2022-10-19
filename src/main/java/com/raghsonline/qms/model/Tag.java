package com.raghsonline.qms.model;

import lombok.Data;
import org.springframework.data.annotation.Reference;

import javax.persistence.*;

@Data
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //@ManyToOne
    //@Column()
    private long quoteId;

    private String name;
}
