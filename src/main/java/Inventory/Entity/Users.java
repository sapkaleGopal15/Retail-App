package Inventory.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email"),
    @UniqueConstraint(columnNames = "shopGSTNumber")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String fName;

    @Column(nullable = false)
    private String lName;

    @Column(nullable = false)
    private String address;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @Getter(value = AccessLevel.NONE)
    private String password;

    @Column(nullable = false)
    private String contact;

    @Column(nullable = false)
    private String shopName;

    @Column(length = 15, unique = true, nullable = false)
    private String shopGSTNumber;

    @Column(nullable = false)
    private String shopAddress;

    @Getter(value = AccessLevel.NONE)
    private boolean enable = false;

    private boolean emailVerified = false;
    private boolean phoneVerified = false;

    private String emailToken;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList = new ArrayList<>();


    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Products> products = new ArrayList<>();



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // list of roles (USER, ADMIN)
        Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role -> new SimpleGrantedAuthority(role))
        .collect(Collectors.toList());
        return roles;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
       return this.enable;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public List<Products> getProducts(){
        return products;
    }

    public void setProducts(List<Products> products){
        this.products = products;
    }

    public Long getId() {
        return userId;
    }
    
}


    