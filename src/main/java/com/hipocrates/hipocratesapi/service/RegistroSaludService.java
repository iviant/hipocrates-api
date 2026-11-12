package com.hipocrates.hipocratesapi.service;

import com.hipocrates.hipocratesapi.dto.RegistroRequestDTO;
import com.hipocrates.hipocratesapi.dto.RegistroResponseDTO;
import com.hipocrates.hipocratesapi.model.RegistroSalud;
import com.hipocrates.hipocratesapi.repository.RegistroSaludRepository;
import com.hipocrates.hipocratesapi.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RegistroSaludService {

    private final RegistroSaludRepository repository;

    public RegistroSaludService(RegistroSaludRepository repository) {
        this.repository = repository;
    }

    public List<RegistroResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public RegistroResponseDTO buscarPorId(Long id) {
        RegistroSalud registro = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro no encontrado con ID: " + id));
        return mapToResponse(registro);
    }

    public RegistroResponseDTO crear(RegistroRequestDTO dto) {
        RegistroSalud registro = new RegistroSalud(
                null,
                dto.pacienteNombre(),
                dto.tipoRegistro().toUpperCase(),
                dto.sintomaOEmocion(),
                dto.intensidad(),
                dto.temperatura(),
                dto.fechaRegistro(),
                dto.etiquetas()
        );
        RegistroSalud guardado = repository.save(registro);
        return mapToResponse(guardado);
    }

    public RegistroResponseDTO actualizar(Long id, RegistroRequestDTO dto) {
        RegistroSalud existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro no encontrado con ID: " + id));

        existente.setPacienteNombre(dto.pacienteNombre());
        existente.setTipoRegistro(dto.tipoRegistro().toUpperCase());
        existente.setSintomaOEmocion(dto.sintomaOEmocion());
        existente.setIntensidad(dto.intensidad());
        existente.setTemperatura(dto.temperatura());
        existente.setFechaRegistro(dto.fechaRegistro());
        existente.setEtiquetas(dto.etiquetas());

        RegistroSalud actualizado = repository.save(existente);
        return mapToResponse(actualizado);
    }

    public RegistroResponseDTO actualizarParcial(Long id, Map<String, Object> updates) {
        RegistroSalud existente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Registro no encontrado con ID: " + id));

        if (updates.containsKey("sintomaOEmocion")) {
            existente.setSintomaOEmocion((String) updates.get("sintomaOEmocion"));
        }
        if (updates.containsKey("intensidad")) {
            existente.setIntensidad((Integer) updates.get("intensidad"));
        }
        if (updates.containsKey("temperatura")) {
            existente.setTemperatura((Double) updates.get("temperatura"));
        }

        RegistroSalud actualizado = repository.save(existente);
        return mapToResponse(actualizado);
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar. Registro no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }

    public RegistroResponseDTO mapToResponse(RegistroSalud registro) {
        return new RegistroResponseDTO(
                registro.getId(),
                registro.getPacienteNombre(),
                registro.getTipoRegistro(),
                registro.getSintomaOEmocion(),
                registro.getIntensidad(),
                registro.getTemperatura(),
                registro.getFechaRegistro(),
                registro.getEtiquetas()
        );
    }
}