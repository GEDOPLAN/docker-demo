package de.gedoplan.demo;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/personen")
public class PersonenResource {

    @Inject
    private PersonenService personenService;

	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Person> getAll() {
            return personenService.findAll();
	}
	
	@POST
	@Consumes(MediaType.TEXT_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public Integer create(Person p) {
            return personenService.save(p);
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	@Path("{id}")
	public Person get(@PathParam("id") Integer id) {
            return personenService.find(id);
	}
}
