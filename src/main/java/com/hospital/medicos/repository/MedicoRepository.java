package com.hospital.medicos.repository;

import com.hospital.medicos.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, UUID> {
    // Hereda todos los métodos básicos (findAll, save, findById)
}