package com.hipocrates.hipocratesapi.repository;

import com.hipocrates.hipocratesapi.model.RegistroSalud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RegistroSaludRepository extends JpaRepository<RegistroSalud, Long> {
    List<RegistroSalud> findByTipoRegistro(String tipoRegistro);
    List<RegistroSalud> findByPacienteNombreContainingIgnoreCase(String pacienteNombre);
}