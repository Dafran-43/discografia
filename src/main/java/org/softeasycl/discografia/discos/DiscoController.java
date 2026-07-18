package org.softeasycl.discografia.discos;

import java.util.List;
import java.util.Optional;

import org.softeasycl.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepo;

    @Autowired
    private IArtistaRepository artistaRepo;

    @PostMapping(
        value = "/disco",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandlePostDiscoRequest(@RequestBody Disco disco) {

        if (!artistaRepo.existsById(disco.idArtista)) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }

        Disco nuevo = discoRepo.insert(disco);
        return new ResponseEntity<>(nuevo, null, HttpStatus.CREATED);
    }

    @GetMapping(
        value = "/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        return new ResponseEntity<>(discoRepo.findAll(), null, HttpStatus.OK);
    }

    @GetMapping(
        value = "/disco/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleGetDiscoRequest(@PathVariable("id") String id) {
        Optional<Disco> disco = discoRepo.findById(id);

        if (disco.isEmpty()) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(disco.get(), null, HttpStatus.OK);
    }

    @GetMapping(
        value = "/artista/{id}/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable("id") String id) {
        return new ResponseEntity<>(discoRepo.findDiscosByIdArtista(id), null, HttpStatus.OK);
    }

    @PutMapping(
        value = "/disco/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleUpdateDiscoRequest(@PathVariable("id") String id, @RequestBody Disco disco) {
        Optional<Disco> discoEncontrado = discoRepo.findById(id);

        if (discoEncontrado.isEmpty()) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }

        if (!artistaRepo.existsById(disco.idArtista)) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }

        Disco discoActualizado = discoEncontrado.get();
        discoActualizado.idArtista = disco.idArtista;
        discoActualizado.nombre = disco.nombre;
        discoActualizado.anioLanzamiento = disco.anioLanzamiento;
        discoActualizado.canciones = disco.canciones;

        Disco guardado = discoRepo.save(discoActualizado);
        return new ResponseEntity<>(guardado, null, HttpStatus.OK);
    }

    @DeleteMapping(
        value = "/disco/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Object> HandleDeleteDiscoRequest(@PathVariable("id") String id) {
        Optional<Disco> disco = discoRepo.findById(id);

        if (disco.isEmpty()) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND);
        }

        discoRepo.deleteById(id);
        return new ResponseEntity<>(disco.get(), null, HttpStatus.OK);
    }
}