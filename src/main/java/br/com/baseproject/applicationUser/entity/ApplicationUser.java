package br.com.baseproject.applicationUser.entity;

import br.com.baseproject.generic.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUser extends BaseEntity {

    @NotEmpty(message = "The field username cannot be empty")
    @Column(unique = true)
    private String username;

    @NotEmpty(message = "The field password cannot be empty")
    private String password;

    public ApplicationUser(ApplicationUser applicationUser) {
        this.username = applicationUser.username;
        this.password = applicationUser.password;
    }
}
