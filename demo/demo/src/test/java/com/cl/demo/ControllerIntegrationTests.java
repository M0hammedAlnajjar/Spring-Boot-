package com.cl.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void clearInMemoryData() {
        DemoApplication.Person_List.clear();
        DemoApplication.Task_List.clear();
        DemoApplication.PhoneNumber_List.clear();
        DemoApplication.userNames.clear();
        DemoApplication.emails.clear();
    }

    @Test
    void personEndpointsHandleGoodAndMissingIdCases() throws Exception {
        mockMvc.perform(post("/person/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "personFirstName": "Mohammed",
                                  "personMiddleName": "Salim",
                                  "personLastName": "Al Najjar",
                                  "personUserName": "mohammed",
                                  "personEmail": "mohammed@example.com",
                                  "personCountryCode": "+968",
                                  "personPhoneNumber": 99112233
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("Person saved"));

        String personId = DemoApplication.Person_List.getFirst()
                .getId().toString();

        mockMvc.perform(get("/person/getById")
                        .param("uuid", personId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personId").value(personId))
                .andExpect(jsonPath("$.userName").value("mohammed"))
                .andExpect(jsonPath("$.phoneNumber").value("+968 99112233"));

        mockMvc.perform(get("/person/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].personId").value(personId));

        mockMvc.perform(put("/person/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "uuid": "%s",
                                  "userNameToUpdate": "mohammed.new",
                                  "emailToUpdate": "new@example.com"
                                }
                                """.formatted(personId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("mohammed.new"))
                .andExpect(jsonPath("$.email").value("new@example.com"));

        mockMvc.perform(delete("/person/deleteById")
                        .param("id", personId))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        String missingId = UUID.randomUUID().toString();

        mockMvc.perform(get("/person/getById")
                        .param("uuid", missingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personId").doesNotExist());

        mockMvc.perform(put("/person/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "uuid": "%s",
                                  "emailToUpdate": "missing@example.com"
                                }
                                """.formatted(missingId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.personId").doesNotExist());

        mockMvc.perform(delete("/person/deleteById")
                        .param("id", missingId))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

        mockMvc.perform(get("/person/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void taskEndpointsHandleGoodAndMissingIdCases() throws Exception {
        mockMvc.perform(post("/task/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "Finish Spring Boot task",
                                  "description": "Complete and test CRUD",
                                  "taskStatus": "TODO",
                                  "isAssigned": false
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("Task saved"));

        String taskId = DemoApplication.Task_List.getFirst()
                .getId().toString();

        mockMvc.perform(get("/task/getById")
                        .param("uuid", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").value(taskId))
                .andExpect(jsonPath("$.taskNumber").value(
                        org.hamcrest.Matchers.startsWith("TASK-")
                ));

        mockMvc.perform(get("/task/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].taskId").value(taskId));

        mockMvc.perform(put("/task/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "uuid": "%s",
                                  "titleToUpdate": "Tested Spring Boot task",
                                  "taskStatusToUpdate": "COMPLETED",
                                  "isAssignedToUpdate": true
                                }
                                """.formatted(taskId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Tested Spring Boot task"))
                .andExpect(jsonPath("$.taskStatus").value("COMPLETED"))
                .andExpect(jsonPath("$.isAssigned").value(true));

        mockMvc.perform(delete("/task/deleteById")
                        .param("id", taskId))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        String missingId = UUID.randomUUID().toString();

        mockMvc.perform(get("/task/getById")
                        .param("uuid", missingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").doesNotExist());

        mockMvc.perform(put("/task/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "uuid": "%s",
                                  "titleToUpdate": "Missing task"
                                }
                                """.formatted(missingId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").doesNotExist());

        mockMvc.perform(delete("/task/deleteById")
                        .param("id", missingId))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

        mockMvc.perform(get("/task/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void phoneNumberEndpointsHandleGoodAndMissingIdCases() throws Exception {
        mockMvc.perform(post("/phoneNumber/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "countryCode": "+968",
                                  "phoneNumber": 99112233
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countryCode").value("+968"))
                .andExpect(jsonPath("$.phoneNumber").value(99112233));

        String phoneNumberId = DemoApplication.PhoneNumber_List.getFirst()
                .getId().toString();

        mockMvc.perform(get("/phoneNumber/getById")
                        .param("uuid", phoneNumberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumberId").value(phoneNumberId));

        mockMvc.perform(get("/phoneNumber/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].phoneNumberId").value(phoneNumberId));

        mockMvc.perform(put("/phoneNumber/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "uuid": "%s",
                                  "countryCodeToUpdate": "+971",
                                  "phoneNumberToUpdate": 501234567
                                }
                                """.formatted(phoneNumberId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countryCode").value("+971"))
                .andExpect(jsonPath("$.phoneNumber").value(501234567));

        mockMvc.perform(delete("/phoneNumber/deleteById")
                        .param("id", phoneNumberId))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        String missingId = UUID.randomUUID().toString();

        mockMvc.perform(get("/phoneNumber/getById")
                        .param("uuid", missingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumberId").doesNotExist());

        mockMvc.perform(put("/phoneNumber/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "uuid": "%s",
                                  "phoneNumberToUpdate": 12345678
                                }
                                """.formatted(missingId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumberId").doesNotExist());

        mockMvc.perform(delete("/phoneNumber/deleteById")
                        .param("id", missingId))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));

        mockMvc.perform(get("/phoneNumber/getAll"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
