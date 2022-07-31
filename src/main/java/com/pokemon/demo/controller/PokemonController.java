package com.pokemon.demo.controller;

import com.pokemon.demo.entity.PokemonDetails;
import com.pokemon.demo.service.PokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@RequestMapping("pokeapi.co/api")
@CrossOrigin(origins = "http://localhost:4200")
public class PokemonController {

    private PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/v2")
    public ResponseEntity<List<PokemonDetails>> getPokemonList() {
        try {

            List<PokemonDetails> pokemonList = pokemonService.getPokemonList();

            for(PokemonDetails pokemonDetails : pokemonList) {
                if(pokemonDetails.getPicByte() != null) {
                    pokemonDetails.setPicByte(decompressBytes(pokemonDetails.getPicByte()));
                }
            }

            return ResponseEntity.ok(pokemonList);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping("/savePokemon")
    public ResponseEntity<PokemonDetails> savePokemonDetails(@RequestParam String name, @RequestParam String details,@RequestParam("image") MultipartFile multipartFile) throws IOException {
        try {
            PokemonDetails pokemonDetails =  new PokemonDetails(name,details,compressBytes(multipartFile.getBytes()).toString());
            pokemonDetails = pokemonService.savePokemonDetails(pokemonDetails);

            return ResponseEntity.ok(pokemonDetails);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}
