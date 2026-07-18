package org.softeasycl.discografia.artistas;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ArtistaController {

    @Autowired
    private IArtistaRepository artistaRepo;

    @PostMapping(
        value = "/artista",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        Artista nuevo = artistaRepo.insert(artista);
        return new ResponseEntity<>(nuevo, null, HttpStatus.CREATED);
    }

    @GetMapping(
        value = "/artistas",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Artista>> HandleGetAristasRequest() {
        return new ResponseEntity<>(artistaRepo.findAll(), null, HttpStatus.OK);
    }

    @GetMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetArtistaRequest(@PathVariable("id") String id) {
        Optional<Artista> artista = artistaRepo.findById(id);

        if (artista.isEmpty()) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(artista.get(), null, HttpStatus.OK);
    }

    @PutMapping(
        value = "/artista/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleUpdateArtistaRequest(
            @PathVariable("id") String id,
            @RequestBody Artista artista) {

        if (!artistaRepo.existsById(id)) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }

        artista._id = id;
        Artista actualizado = artistaRepo.save(artista);

        return new ResponseEntity<>(actualizado, null, HttpStatus.OK);
    }

    @DeleteMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleDeleteArtistaRequest(@PathVariable("id") String id) {

        if (!artistaRepo.existsById(id)) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }

        Optional<Artista> artista = artistaRepo.findById(id);
        artistaRepo.deleteById(id);

        return new ResponseEntity<>(artista.get(), null, HttpStatus.OK);
    }
}