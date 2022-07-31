package com.pokemon.demo.repo;

import com.pokemon.demo.entity.PokemonDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PokemonRepoTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PokemonRepo pokemonRepo;

    private boolean setupComplete;

    @BeforeEach
    public void setUp() throws Exception {

        savePokemon();
        if (!setupComplete) {

            setupComplete = true;
        }
    }

    @Test
    public void testSearchPokemonDetails() {
        PokemonDetails pokemonDetails = pokemonRepo.findByName("someName");
        assertNotNull(pokemonDetails);

       assertEquals("someName", pokemonDetails.getName());
       assertEquals("someDetails", pokemonDetails.getDetails());
       assertEquals("someImage", pokemonDetails.getPhoto());
    }

    @Test
    public void testSearchPokemonDetailsNotvailable() {
        PokemonDetails pokemonDetails = pokemonRepo.findByName("someName1");
        assertNull(pokemonDetails);

    }

    private PokemonDetails savePokemon(){
        PokemonDetails pokemonDetails = new PokemonDetails("someName","someDetails","someImage");
        pokemonDetails.setDetails("SomeDetails");
        return pokemonRepo.save(pokemonDetails);
    }

}