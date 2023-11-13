package org.playground;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "parking")
public class Plaque {

	@Id // Id est utilisé pour faire la correspondance avec la clé primaire
	@Column(name = "id")
	String identifiant;
	
	@Column(name = "plaque")
	String numero;
	
	@Column(name = "date")
	Date date;
	
}
