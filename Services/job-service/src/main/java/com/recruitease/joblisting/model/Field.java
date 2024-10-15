package com.recruitease.joblisting.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fields")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Field {
    @Id
    private Integer key;

    private String label;
}
