package com.hipocrates.hipocratesapi.controller;

import com.hipocrates.hipocratesapi.dto.RegistroRequestDTO;
import com.hipocrates.hipocratesapi.dto.RegistroResponseDTO;
import com.hipocrates.hipocratesapi.service.RegistroSaludService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/registros")
public class RegistroSaludController {

    private final RegistroSaludService service;

    public RegistroSaludController(RegistroSaludService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RegistroResponseDTO>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<RegistroResponseDTO> crear(@Valid @RequestBody RegistroRequestDTO dto) {
        RegistroResponseDTO nuevo = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody RegistroRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RegistroResponseDTO> actualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(service.actualizarParcial(id, updates));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}