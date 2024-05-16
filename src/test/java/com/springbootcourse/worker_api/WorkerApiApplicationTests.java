package com.springbootcourse.worker_api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.springbootcourse.worker_api.model.Worker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WorkerApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    private Worker worker;

    private ObjectMapper om;

    @BeforeAll
    public void setUpMapper() {
        om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
    }

    @BeforeAll
    public void addWorker() throws Exception {

        String workerJson = """
                {"firstName": "John",
                 "lastName": "Doe",
                 "birthday": "1990-01-01",
                "email": "john1@example.com",
                "positionJob": "Manager"
                }""";

        MvcResult resultCreate = mockMvc.perform(post("/api/workers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(workerJson))
                .andExpect(status().isCreated())
                .andReturn();

        String userString = resultCreate.getResponse().getContentAsString();
        System.out.println("Response from /api/workers: " + userString);

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());

        worker = om.readValue(userString, Worker.class);
    }

    @Test
    public void getWorkersListFilterByFirstName() throws Exception {
        mockMvc.perform(get("/api/workers")
                        .param("firstName", "John")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName", is("John")));
    }

    @Test
    public void getWorkersListFilterByAllParams() throws Exception {
        mockMvc.perform(get("/api/workers")
                        .param("firstName", "John")
                        .param("lastName", "Doe")
                        .param("birthday", "1990-01-01")
                        .param("email", "john1@example.com")
                        .param("positionJob", "Manager")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Doe")))
                .andExpect(jsonPath("$[0].birthday", is("1990-01-01")))
                .andExpect(jsonPath("$[0].email", is("john1@example.com")))
                .andExpect(jsonPath("$[0].positionJob", is("Manager")));

    }

    @Test
    public void getWorkersListFilterByNullParam() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/workers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String workerString = result.getResponse().getContentAsString();
        List<Worker> users = om.readValue(workerString, new TypeReference<>() {});

        assertThat(users.contains(worker));
    }

    @Test
    public void getWorkerByIdTest() throws Exception {

        ResultActions result = mockMvc.perform(get("/api/workers/{id}", worker.getId()));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getWorkerByIdTestUnsuccessful() throws Exception {

        ResultActions result = mockMvc.perform(get("/api/workers/{id}", 0));

        // Assert
        result.andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createWorkerTest() throws Exception {
        String workerJson = """
                {"firstName": "Valeriia",
                 "lastName": "Doe",
                 "birthday": "1995-03-03",
                "email": "valeriia.doe@example.com",
                "positionJob": "Manager"
                }""";

        mockMvc.perform(post("/api/workers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(workerJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("firstName", is("Valeriia")))
                .andExpect(jsonPath("lastName", is("Doe")))
                .andExpect(jsonPath("birthday", is("1995-03-03")))
                .andExpect(jsonPath("email", is("valeriia.doe@example.com")))
                .andExpect(jsonPath("positionJob", is("Manager")));
    }

    @Test
    public void createWorkerUnsuccessfulTest() throws Exception {
        String workerJson = """
                {"firstName": "Valeriia",
                 "birthday": "1995-03-03",
                "email": "valeriia.doe@"
                }""";

        mockMvc.perform(post("/api/workers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(workerJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteUserTest() throws Exception {

        String userJson = """
                 {"firstName": "Mariia",
                 "lastName": "Doe",
                 "birthday": "1995-03-03",
                "email": "mariia.doe@example.com",
                "positionJob": "Manager"
                }""";

        MvcResult resultCreate = mockMvc.perform(post("/api/workers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andReturn();

        String userString = resultCreate.getResponse().getContentAsString();

        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());

        Worker deleteWorker = om.readValue(userString, Worker.class);

        ResultActions result = mockMvc.perform(delete("/api/workers/{id}", deleteWorker.getId()));

        // Assert
        result.andExpect(status().isNoContent());
    }

}
