package com.elm.task.privilege;

import com.elm.task.user.User;
import lombok.Data;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "privileges")
public class Privilege implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "privilege")
    private String privilege;




    @Override

    public String getAuthority() {
        return privilege;
    }
}
