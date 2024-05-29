/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: Role
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
@Entity(name="Role")
@Table(name="T_01_02", uniqueConstraints = { @UniqueConstraint(name = "T_01_02_UK_01", columnNames = { "F_01" })})
public class Role {
	
	@Id
	@Column(name="F_00")
  	@GeneratedValue(strategy=GenerationType.IDENTITY)
  	private Long id;

	@Column(name="F_01", length=40, nullable=false)
	private String name;
	
	@ManyToMany(mappedBy = "roles")
    private List<User> users;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "R_01_02_01_03", 
			joinColumns = @JoinColumn(name = "F_01", foreignKey=@ForeignKey(name="R_01_02_01_03_FK_01")), 
			inverseJoinColumns = @JoinColumn(name = "F_02", foreignKey=@ForeignKey(name="R_01_02_01_03_FK_02")),
			uniqueConstraints = @UniqueConstraint(name = "R_01_02_01_03_UK_01", columnNames = {"F_01", "F_02"}))
	private List<Authority> authorities;

}