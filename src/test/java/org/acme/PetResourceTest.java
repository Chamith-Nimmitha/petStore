package org.acme;

import com.example.petstore.Pet;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class PetResourceTest {


    List<Pet> pets = null;

	@Test
    public void testPetEndpoint() {
        given()
          .when().get("/v1/pets")
          .then()
             .statusCode(200);
//             .body(hasItem(
// 		            allOf(
//    		                hasEntry("pet_id", "1"),
//    		                hasEntry("pet_type", "Dog"),
//    		                hasEntry("pet_name", "Boola"),
//    		                hasEntry("pet_age", "3")
//    		            )
//    		      )
//    		 );
    }

    @BeforeEach
    public void resetTheValues()
    {
        System.out.println( "Reset the Values" );
        pets = new ArrayList<>()
        {{
            add( new Pet( 1, "dog", "shiba", 5 ) );
            add( new Pet( 2, "cat", "boola", 2 ) );
            add( new Pet( 3, "bird", "peththa", 2 ) );
        }};
    }

    @Test
    @Order( 5 )
    public void testPetGetEndPoint()
    {
        given().when().get( "/pets" ).then().statusCode( 200 );
    }


    @Test
    @Order( 5 )
    void testPetGetEndpointSuccessWithValues()
    {
        given()
                .when().get( "/pets" )
                .then()
                .assertThat()
                .statusCode( 200 )
                .body( "petId", notNullValue() )
                .body( "petAge", equalTo( new ArrayList()
                {{
                    add( 5 );
                    add( 2 );
                    add( 2 );
                }} ) )
                .body( "petName", equalTo( new ArrayList()
                {{
                    add( "shiba" );
                    add( "boola" );
                    add( "peththa" );
                }} ) )
                .body( "petType", equalTo( new ArrayList()
                {{
                    add( "dog" );
                    add( "cat" );
                    add( "bird" );
                }} ) );

    }


    @Test
    @Order( 1 )
    public void testedAdding()
    {
        given()
                .header( "Content-Type", "application/json" )
                .body( "{\n" +
                        "\t\"petType\":\"Dog\",\n" +
                        "\t\"petName\":\"Flexy\",\n" +
                        "\t\"petAge\":5\n" +
                        "}" )
                .when().post( "/pets" )
                .then()
                .assertThat()
                .statusCode( 200 )
                .body( "petId", notNullValue() )
                .body( "petAge", equalTo( 5 ) )
                .body( "petName", equalTo( "Flexy" ) )
                .body( "petType", equalTo( "Dog" ) );

    }

    @Test
    @Order( 2 )
    void testUpdatePet()
    {
        given()
                .header( "Content-Type", "application/json" )
                .pathParam( "id", 1 )
                .body( "{\n" +
                        "\t\"petType\":\"Dog\",\n" +
                        "\t\"petName\":\"Flexy\",\n" +
                        "\t\"petAge\":5\n" +
                        "}" )
                .when().put( "/pets/{id}" )
                .then()
                .assertThat()
                .statusCode( 200 )
                .body( "petId", notNullValue() )
                .body( "petAge", equalTo( 5 ) )
                .body( "petName", equalTo( "Flexy" ) )
                .body( "petType", equalTo( "Dog" ) );

    }

    @Test
    @Order( 3 )
    void testUpdatePetNotValidId()
    {
        given()
                .header( "Content-Type", "application/json" )
                .pathParam( "id", 6 )
                .body( "{\n" +
                        "\t\"petType\":\"Dog\",\n" +
                        "\t\"petName\":\"Flexy\",\n" +
                        "\t\"petAge\":5\n" +
                        "}" )
                .when().put( "/pets/{id}" )
                .then()
                .assertThat()
                .statusCode( 204 );

    }


    @Test
    @Order( 4 )
    void testDeletePet()
    {
        given()
                .header( "Content-Type", "application/json" )
                .pathParam( "id", 1 )
                .when().delete( "/pets/{id}" )
                .then()
                .assertThat()
                .statusCode( 200 );

    }

    @Test
    void testSearchPets()
    {
        given().header( "Content-Type", "application/json" )
                .queryParam( "age", "5" )
                .when().get( "/pets/search" )
                .then()
                .assertThat()
                .statusCode( 200 )
                .body( "petId", notNullValue() )
                .body( "petAge", equalTo( new ArrayList()
                {{
                    add( 5 );
                }} ) )
                .body( "petName", equalTo( new ArrayList()
                {{
                    add( "boola" );
                }} ) )
                .body( "petType", equalTo( new ArrayList()
                {{
                    add( "dog" );
                }} ) );
        ;
    }


/////////////////////////////////    Pet Type testCases   ///////////////////////////////////////////


    @Test
    void testGetPetType()
    {
        given()
                .when().get( "/petTypes" )
                .then()
                .assertThat()
                .statusCode( 200 )
                .body( "petType", equalTo( new ArrayList()
                {{
                    add( "dog" );
                    add( "cat" );
                    add( "bird" );
                }} ) );

    }

    @Test
    void testAddPetType()
    {
        given()
                .header( "Content-Type", "application/json" )
                .body( "{\n" +
                        "  \"petId\": 4,\n" +
                        "  \"petType\": \"rat\"\n" +
                        "}" )
                .when().post( "/petTypes" )
                .then()
                .assertThat()
                .statusCode( 200 )
                .body( "petId", notNullValue() )
                .body( "petType", equalTo( "rat" ) );

    }

    @Test
    void testUpdatePetType()
    {
        given()
                .header( "Content-Type", "application/json" )
                .pathParam( "id", 3 )
                .body( "{\n" +
                        "  \"petId\": 0,\n" +
                        "  \"petType\": \"cats\"\n" +
                        "}" )
                .when().put( "/petTypes/{id}" )
                .then()
                .assertThat()
                .statusCode( 200 );

    }

    @Test
    public void testDeletePetType()
    {

        given()
                .header( "Content-Type", "application/json" )
                .pathParam( "id", 1 )
                .when().delete( "/petTypes/{id}" )
                .then()
                .assertThat()
                .statusCode( 200 );


    }

}