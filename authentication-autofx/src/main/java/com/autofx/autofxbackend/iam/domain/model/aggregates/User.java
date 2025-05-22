package com.autofx.autofxbackend.iam.domain.model.aggregates;

import com.autofx.autofxbackend.iam.domain.model.entities.Role;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.EmailAddress;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.Name;
import com.autofx.autofxbackend.iam.domain.model.valueobjects.PhoneNumber;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Name name;

    @Embedded
    private PhoneNumber phoneNumber;

    @Embedded
    private EmailAddress email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @ManyToMany(fetch =  FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User(){
        this.roles = new HashSet<>();
    }

    public User(String firstname, String lastName, String password) {
        this();
        this.name = new Name(firstname, lastName);
        this.password = password;
    }

    public User(String firstname, String lastName, String password, List<Role> roles, String countryCode, Integer phoneNumber, String email) {
        this(firstname,lastName, password);
        var validatedRoleSet = Role.validateRoleSet(roles);
        this.roles.addAll(validatedRoleSet);
        this.phoneNumber = new PhoneNumber(countryCode, phoneNumber);
        this.email = new EmailAddress(email);
    }

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Date updateAt;

}
