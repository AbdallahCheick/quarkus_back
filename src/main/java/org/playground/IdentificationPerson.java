package org.playground;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;

@Path("identite")
@ApplicationScoped
public class IdentificationPerson {
	@Inject
	EntityManager entityManager;
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@Transactional
	public Response create(@FormParam("nom") String nom, @FormParam("prenom") String prenom, @FormParam("age") int age, @FormParam("contact") String contact ) {
		Personne personne = new Personne();
		
		personne.id = UUID.randomUUID().toString();
		personne.nom = nom ; 
		personne.prenom = prenom; 
		personne.age = age; 
		personne.contact = contact;
		entityManager.persist(personne);
		return Response.ok(String.format("%s %s à ete enregistrer avec success", nom , prenom)).build();
	}
	
    @PersistenceContext
    EntityManager entityManager1;

    @GET
    @Path("/liste")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getAllPerson() {
        // Utilisez EntityManager pour exécuter la requête pour récupérer toutes les plaques
        List<Personne> personne = entityManager1.createQuery("SELECT p FROM Personne p", Personne.class)
            .getResultList();
        List<PersonDto> dtos = new ArrayList<PersonDto>();
        for(Personne personne1 : personne) {
        	PersonDto dto = new PersonDto();
        	dto.setId(personne1.id);
        	dto.setNom(personne1.nom);
        	dto.setPrenom(personne1.prenom);
        	dto.setAge(personne1.age);
        	dto.setContact(personne1.contact);
        	dtos.add(dto);
        }
        ResponseBuilder responseBuilder = Response.status(200);
        responseBuilder.entity(dtos);
        Response response = responseBuilder.build();
        return response;
    }

}
