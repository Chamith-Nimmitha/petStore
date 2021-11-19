package com.example.petstore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@Path("/pets")
@Produces("application/json")
public class PetResource {

	private List<Pet> pets = new ArrayList<Pet>();
	private List<PetType> petTypes = new ArrayList<>();
	int petId = 1;
	int petTypeId= 1;

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "All Pets", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))) })
	@GET
	public Response getPets() {
		return Response.ok(pets).build();
	}

	@APIResponses(value = {
			@APIResponse(responseCode = "200", description = "Pet for id", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(ref = "Pet"))),
			@APIResponse(responseCode = "404", description = "No Pet found for the id.") })
	@GET
	@Path("{petId}")
	public Response getPet(@PathParam("petId") int petId) {
		if (petId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}

		List<Pet> givenPet = pets.stream()
				.filter(pet -> petId == pet.getPetId())
				.collect(Collectors.toList());

		if(givenPet.size() == 0){
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(givenPet.get(0)).build();
	}

	@POST
	public Response addPet(@RequestBody Pet pet){
		pet.setPetId(petId);
		pets.add(pet);
		petId++;
		return Response.ok(pet).build();
	}

	@DELETE
	@Path("{petId}")
	public  Response deletePet(@PathParam("petId") int petId){
		if (petId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}

		for (int i=0; i<pets.size(); i++){
			if (petId == pets.get(i).getPetId()){
				pets.remove(i);
				return Response.ok(pets).build();
			}
		}
		return Response.status(Status.NOT_FOUND).build();
	}

	@PATCH
	@Path("{petId}")
	public Response updatePet(@PathParam("petId") int petId,@RequestBody Pet pet){
		if (petId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}
		else{
			for (int i=0; i<pets.size(); i++){
				if (petId == pets.get(i).getPetId()){
					if (pet.getPetAge()!=0){
						pets.get(i).setPetAge(pet.getPetAge());
					}
					if (pet.getPetName()!=null){
						pets.get(i).setPetName(pet.getPetName());
					}
					if (pet.getPetType()!=null){
						pets.get(i).setPetType(pet.getPetType());
					}
					return Response.ok(pets.get(i)).build();
				}
			}
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	 /*
	  ###### for pet types #######
	  */

	@GET
	@Path("types")
	public Response getAllTypes(){
		return Response.ok(petTypes).build();
	}

	@POST
	@Path("types")
	public Response addType (@RequestBody PetType type ){
		type.setTypeId(petTypeId);
		petTypes.add(type);
		petTypeId++;
		return Response.ok(petTypes).build();
	}

	@DELETE
	@Path("types/{typeId}")
	public Response deleteType(@PathParam("typeId") int petType ){
		if (petType < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}
		else{

			for (int x=0; x<petTypes.size(); x++){
				if (petType == petTypes.get(x).getTypeId()){
					String type = petTypes.get(x).getPetType();
					petTypes.remove(x);
					for (int y=0; y< pets.size(); y++){
						if(type.equals(pets.get(y).getPetType())){
							pets.remove(y);
							y--;
						}
					}
					return Response.ok(petTypes).build();
				}
			}
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	@PATCH
	@Path("types/{typeId}")
	public Response updateType(@PathParam("typeId") int typeId, @RequestBody PetType petType){
		if (typeId < 0) {
			return Response.status(Status.NOT_FOUND).build();
		}
		else{
			for (int x=0; x<petTypes.size(); x++){
				if (typeId == petTypes.get(x).getTypeId()){
					String type = petTypes.get(x).getPetType();
					petTypes.get(x).setPetType(petType.getPetType());
					for (int y=0; y< pets.size(); y++){
						if(type.equals(pets.get(y).getPetType())){
							pets.get(y).setPetType(petType.getPetType());
						}
					}
					return Response.ok(petTypes).build();

				}
			}
			return Response.status(Status.NOT_FOUND).build();
		}
	}

	/*
	  ###### for pet Search #######
	 */

	@POST
	@Path("/search")
	public Response petSearch(@RequestBody SearchPet search){

		List<Pet> searchPets = pets.stream()
				.filter(pet -> {
					if (search.getName() != null && pet.getPetName().contains(search.getName())) {
						return true;
					}
					return false;
				})
				.filter(pet -> {
					if (search.getAge() != 0 && pet.getPetAge() == search.getAge()) {
						return true;
					}
					return false;
				})
				.filter(pet -> {
					if (search.getType() != null && pet.getPetType().contains(search.getType())) {
						return true;
					}
					return false;
				})
				.collect(Collectors.toList());

		return Response.ok(searchPets).build();
	}
}
