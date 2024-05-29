/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: User
 *	@CreatedOn	: 05-27-2024
 *
 *	@Type		: Class
 *	@Layaer		: Model
 *	@Domain		: Security
 *
 **/

package aures.api.model.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="User")
@Table(name="T_01_01", uniqueConstraints = { @UniqueConstraint(name = "T_01_01_UK_01", columnNames = { "F_01" })})
public class User implements UserDetails{
	
	private static final long serialVersionUID = 1221548L;

	
	@Id
	@Column(name="F_00")
  	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	private Long id;

	@Column(name="F_01", length=50, nullable=false)
	private String username;

	//@JsonIgnore()
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name="F_02", length=150, nullable=false)
	private String password;
	
	@Column(name="F_03", nullable=false)
	private int enabled;
	
	@Column(name="F_04", nullable=false)
	private int locked;
	
	@Column(name="F_05", nullable=false)
	private Date expirationDate;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "R_01_01_01_02", 
			joinColumns = @JoinColumn(name = "F_01", foreignKey=@ForeignKey(name="R_01_01_01_02_FK_01")), 
			inverseJoinColumns = @JoinColumn(name = "F_02", foreignKey=@ForeignKey(name="R_01_01_01_02_FK_02")),
			uniqueConstraints = @UniqueConstraint(name = "R_01_01_01_02_UK_01", columnNames = {"F_01", "F_02"}))
	private List<Role> roles;

	@Override
    public boolean isAccountNonExpired() {
		return this.expirationDate.getTime() - System.currentTimeMillis()>0?true:false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.locked==0?true:false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		if(this.getRoles() != null)
		this.getRoles().forEach(role->{
			//authorities.add(new SimpleGrantedAuthority(role.getName()));
			role.getAuthorities().forEach(authority->{
				authorities.add(new SimpleGrantedAuthority(authority.getName()));
			});
		});
		return (Collection<? extends GrantedAuthority>) authorities;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled==1?true:false;
	}
	
}