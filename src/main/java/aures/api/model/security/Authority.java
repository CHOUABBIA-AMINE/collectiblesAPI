/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: Authority
 *	@CreatedOn	: 05-27-2023
 *
 *	@Type		: Class
 *	@Layaer		: Model
 *	@Domain		: Security
 *
 **/

package aures.api.model.security;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity(name="Authority")
@Table(name="T_01_03", uniqueConstraints = { @UniqueConstraint(name = "T_01_03_UK_01", columnNames = { "F_01" })})
public class Authority {
	
	@Id
	@Column(name="F_00")
  	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	private Long id;

	@Column(name="F_01", length=20, nullable=false)
	private String name;
	
	@ManyToMany(mappedBy = "authorities")
    private List<Role> roles;
	
	//@ManyToMany(mappedBy = "authorities")
    //private List<User> users;

}