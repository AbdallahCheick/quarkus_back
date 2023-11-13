package org.playground;

import java.util.ArrayList;
import java.util.Date;
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

@Path("plaques")
@ApplicationScoped
public class PlaqueService {

	/**
	 * Le gestionnaire d'entité nous permet d'interagir avec la base de données. C'est à dire faire les opérations de création, lecture, moditification et suppression.
	 */
	@Inject
	EntityManager entityManager;
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	@Transactional
	public Response create(@FormParam("valeur") String value) {
		/*
		 * On instantie un objet plaque
		 */
		Date date = new Date();
		Plaque plaque = new Plaque();
		/*
		 * On initialise les propriétés
		 */
		plaque.numero = value;
		plaque.identifiant = UUID.randomUUID().toString();
		plaque.date = date ;
		/*
		 * On persiste l'objet dans la base de données
		 */
		entityManager.persist(plaque);
		/*
		 * On retourne un message de succès.
		 */
		return Response.ok(String.format("La plaque %s a été enregistrée.", value)).build();
	}
	
    @PersistenceContext
    EntityManager entityManager1;
    
    @GET
    @Path("/liste")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getAllPlaques() {
        // Utilisez EntityManager pour exécuter la requête pour récupérer toutes les plaques
    	List<Plaque> plaques = entityManager1.createQuery("SELECT p FROM Plaque p ORDER BY p.date DESC", Plaque.class)
                .getResultList();
        List<PlaqueDto> dtos = new ArrayList<PlaqueDto>();
        for(Plaque plaque : plaques) {
        	PlaqueDto dto = new PlaqueDto();
        	dto.setId(plaque.identifiant);
        	dto.setValue(plaque.numero);
        	dto.setDate(plaque.date.toString());
        	dtos.add(dto);
        }
        ResponseBuilder responseBuilder = Response.status(200);
        responseBuilder.entity(dtos);
        Response response = responseBuilder.build();
        return response;
    }
	
}
