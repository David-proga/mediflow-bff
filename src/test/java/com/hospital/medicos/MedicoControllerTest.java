package com.hospital.pacientes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.pacientes.model.Medico;
import com.hospital.pacientes.repository.MedicoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class MedicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        medicoRepository.deleteAll();
    }

    @Test
    void deberiaListarMedicosExitosamente() throws Exception {
        mockMvc.perform(get("/api/medicos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaCrearUnMedicoCorrectamente() throws Exception {
        Medico medico = new Medico();
        medico.setNombre("Gregory");
        medico.setApellido("House");
        medico.setEspecialidad("Diagnostico");
        medico.setEmail("house@example.com");
        medico.setTelefono("+56922222222");

        mockMvc.perform(post("/api/medicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(medico)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Gregory"))
                .andExpect(jsonPath("$.especialidad").value("Diagnostico"));
    }
}