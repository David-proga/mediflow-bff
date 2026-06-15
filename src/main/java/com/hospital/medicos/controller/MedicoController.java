package com.hospital.medicos.controller;

import com.hospital.medicos.model.Medico;
import com.hospital.medicos.repository.MedicoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/medicos")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Médicos", description = "Endpoints para la gestión del personal médico")
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    // --- CREAR ---
    @PostMapping
    @Operation(summary = "Registrar un nuevo médico")
    public ResponseEntity<Medico> crearMedico(@RequestBody Medico medico) {
        Medico guardado = medicoRepository.save(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // --- LEER TODOS ---
    @GetMapping
    @Operation(summary = "Obtener la lista de todos los médicos")
    public List<Medico> listarTodos() {
        return medicoRepository.findAll();
    }

    // --- MODIFICAR ---
    @PutMapping("/{id}")
    @Operation(summary = "Modificar un médico existente")
    public ResponseEntity<?> actualizarMedico(@PathVariable UUID id, @RequestBody Medico medicoDetalles) {
        Optional<Medico> medicoOpt = medicoRepository.findById(id);
        
        if (medicoOpt.isPresent()) {
            Medico medicoExistente = medicoOpt.get();
            
            // Actualizamos los campos simplificados
            medicoExistente.setNombre(medicoDetalles.getNombre());
            medicoExistente.setApellido(medicoDetalles.getApellido());
            medicoExistente.setEspecialidad(medicoDetalles.getEspecialidad());
            medicoExistente.setEmail(medicoDetalles.getEmail());
            medicoExistente.setTelefono(medicoDetalles.getTelefono());
            
            Medico actualizado = medicoRepository.save(medicoExistente);
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: No se encontró el médico con el ID proporcionado.");
        }
    }

    // --- BORRAR ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un médico por su ID")
    public ResponseEntity<?> eliminarMedico(@PathVariable UUID id) {
        if (!medicoRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: No se encontró el médico con el ID proporcionado.");
        }
        medicoRepository.deleteById(id);
        return ResponseEntity.ok("Médico eliminado correctamente de la base de datos.");
    }
}