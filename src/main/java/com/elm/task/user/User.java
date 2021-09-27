package com.elm.task.user;

import com.elm.task.article.Article;
import com.elm.task.privilege.Privilege;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
@Entity

@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Table(name = "USERS")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;


    @NotBlank(message = "username must not be empty!")
    @Column(unique = true)
    private String username;

    @NotBlank(message = "phone must not be empty!")
    @Column
    private String phone;

    @JsonIgnore
    @NotBlank(message = "password must not be empty!")
    @Column
    private String password;

    @NotBlank(message = "email must not be empty!")
    @Email
    @Column(unique = true)
    private String email;


  //  @OneToMany(mappedBy = "author")
   // private Collection<Article> articles;

    @ManyToMany
    @JoinTable(
            name = "users_privileges",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "privileges_id")
    )
    private List<Privilege> privileges=new ArrayList<>();

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return getPrivileges();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }



}
