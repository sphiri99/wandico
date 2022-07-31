package com.pokemon.demo.repo;

import com.pokemon.demo.entity.PokemonDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepo extends JpaRepository<PokemonDetails, Long> {

    PokemonDetails findByName(String name);

}
