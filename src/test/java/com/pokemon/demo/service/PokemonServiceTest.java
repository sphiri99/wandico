package com.pokemon.demo.service;

import com.pokemon.demo.entity.PokemonDetails;
import com.pokemon.demo.repo.PokemonRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class PokemonServiceTest {

    @Mock
    private PokemonRepo pokemonRepo;

    private PokemonService classUnderTest;

    @BeforeEach
    public void setUp() throws Exception {
      classUnderTest = new PokemonService(pokemonRepo);


    }


    @Test
    public void testSavePokemonDetails() {
       when(pokemonRepo.save(any())).thenReturn(savePokemonInstance());
        PokemonDetails pokemonDetails = classUnderTest.savePokemonDetails(savePokemonInstance());
        assertNotNull(pokemonDetails);


        assertEquals("someName", pokemonDetails.getName());
        assertEquals("someDetails", pokemonDetails.getDetails());
        assertEquals("someImage", pokemonDetails.getPhoto());

    }


    @Test
    public void testSavePokemonDetailsFailure() {
        PokemonDetails pokemonDetails = classUnderTest.savePokemonDetails(savePokemonInstance());
        assertNull(pokemonDetails);

        verify(pokemonRepo,timeout(0)).save(any());

    }


    private PokemonDetails savePokemonInstance(){
        PokemonDetails pokemonDetails = new PokemonDetails("someName","someDetails","someImage");
        return pokemonDetails;
    }

}