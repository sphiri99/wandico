package com.pokemon.demo.service;

import com.pokemon.demo.entity.PokemonDetails;
import com.pokemon.demo.repo.PokemonRepo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PokemonService {
    private PokemonRepo pokemonRepo;

    public PokemonService(PokemonRepo pokemonRepo) {
        this.pokemonRepo = pokemonRepo;
    }

    public PokemonDetails getPokemonDetails(String name) {
        return pokemonRepo.findByName(name);

    }

    public PokemonDetails savePokemonDetails(PokemonDetails pokemonDetails) {
        return pokemonRepo.save(pokemonDetails);

    }

    public List<PokemonDetails> getPokemonList() {
        List<PokemonDetails> all = pokemonRepo.findAll();
        List<PokemonDetails> first100 = new ArrayList<>();
        if(all != null && all.size() > 0) {
            if(all.size() > 100) {
                for(int i =0; i < 100; i++) {
                    first100.add(all.get(i));
                }
                return first100;

            }

        }
        return all;

    }
}
