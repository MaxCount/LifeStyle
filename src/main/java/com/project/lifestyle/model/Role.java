package com.project.lifestyle.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles", schema = "lifestyle")
public class Role {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long role_id;
    @NotEmpty
    private String name;

    public Role(String name) {
        this.name = name;
    }

    public Role(Long role_id) {
        this.role_id = role_id;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
