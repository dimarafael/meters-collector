package com.dima.meterscollector.controller;

import com.dima.meterscollector.domain.MeterConfiguration;
import com.dima.meterscollector.repository.MeterConfigRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MeterConfigControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MeterConfigRepo meterConfigRepo;


    @BeforeEach
    void clearDB(){
        meterConfigRepo.deleteAll();
    }

    @Test
    void getAllMeters_authentication() throws Exception {
        mockMvc.perform(get("/api/meter_config"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/meter_config").with(httpBasic("admin","000")))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/meter_config").with(httpBasic("admin","090980")))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "admin")
    void getAllMeters() throws Exception {
        meterConfigRepo.save(MeterConfiguration.builder().ipAddress("1.1.1.1").build());
        meterConfigRepo.save(MeterConfiguration.builder().ipAddress("1.1.1.2").build());

        mockMvc.perform(get("/api/meter_config"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].ipAddress").value("1.1.1.1"))
                .andExpect(jsonPath("$[1].ipAddress").value("1.1.1.2"));
    }

    @Test
    @WithMockUser(value = "admin")
    void getMeter() throws Exception {
        Long id = meterConfigRepo.save(MeterConfiguration.builder().ipAddress("1.1.1.3").build()).getId();

        mockMvc.perform(get("/api/meter_config/{id}",id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("ipAddress").value("1.1.1.3"));

        mockMvc.perform(get("/api/meter_config/1234"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(value = "admin")
    void addMeter() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        MeterConfiguration meter = MeterConfiguration.builder().ipAddress("1.1.1.4").build();

        mockMvc.perform(post("/api/meter_config")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(meter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("ipAddress").value("1.1.1.4"));
    }

    @Test
    @WithMockUser(value = "admin")
    void putMeter() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        MeterConfiguration meter = MeterConfiguration.builder().ipAddress("1.1.1.4").build();
        Long id = meterConfigRepo.save(meter).getId();
        meter.setIpAddress("1.1.1.123");
//        meter.setId(id);

        mockMvc.perform(put("/api/meter_config/{id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(meter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("ipAddress").value("1.1.1.123"));

        mockMvc.perform(put("/api/meter_config/1234")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(meter)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(value = "admin")
    void deleteMeter() throws Exception {
        Long id = meterConfigRepo.save(MeterConfiguration.builder().ipAddress("1.1.1.3").build()).getId();

        mockMvc.perform(delete("/api/meter_config/{id}",id))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/meter_config/123"))
                .andExpect(status().isNotFound());
    }
}
