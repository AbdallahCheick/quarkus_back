package org.playground;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="identification")
public class Personne {

	@Id
	@Column(name="id")
	String id;
	
	@Column(name="nom")
	String nom;
	
	@Column(name="prenom")
	String prenom;
	
	@Column(name="age")
	int age;
	
	@Column(name="contact")
	String contact;
	
	
}
