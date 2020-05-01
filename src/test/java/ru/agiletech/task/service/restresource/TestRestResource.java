package ru.agiletech.task.service.restresource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.agiletech.task.service.Application;
import ru.agiletech.task.service.application.task.TaskDTO;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;

@Slf4j
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {Application.class})
public class TestRestResource {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .build();

        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
    }

    @Test
    public void testFullFlow() throws Exception {
        TaskDTO taskDTO = testPost();

        assertNotNull(taskDTO.getId());
        assertNotNull(taskDTO.getDescription());
        assertNotNull(taskDTO.getSummary());
        assertNotNull(taskDTO.getPriority());
        assertNotNull(taskDTO.getWorkFlowStatus());

        String id = taskDTO.getId();

        testPutPriority(id);
        testPutAssignee(id);
        testPutStart(id);
        testPutWorkHours(id);
        testPutStop(id);

        TaskDTO foundTaskDTO = testGet(id);

        assertNotNull(foundTaskDTO.getId());
        assertNotNull(foundTaskDTO.getDescription());
        assertNotNull(foundTaskDTO.getSummary());
        assertNotNull(foundTaskDTO.getPriority());
        assertNotNull(foundTaskDTO.getWorkFlowStatus());
        assertNotNull(foundTaskDTO.getAssignee());
        assertNotNull(foundTaskDTO.getStartDate());
        assertNotNull(foundTaskDTO.getEndDate());
        assertNotNull(foundTaskDTO.getWorkDays());
        assertNotNull(foundTaskDTO.getWorkHours());
    }

    private TaskDTO testPost() throws Exception {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setSummary("New task");
        taskDTO.setDescription("Description for new task");
        taskDTO.setProjectKey("TST");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
                .content(objectMapper.writeValueAsString(taskDTO))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        String content = mvcResult.getResponse()
                .getContentAsString();

        return objectMapper.readValue(content, TaskDTO.class);
    }

    private TaskDTO testGet(String id) throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        String content = mvcResult.getResponse()
                .getContentAsString();

        return objectMapper.readValue(content, TaskDTO.class);
    }

    private void testPutPriority(String id) throws Exception {
        String priorityValue = "MEDIUM";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/{id}?priority={value}", id, priorityValue)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }

    private void testPutAssignee(String id) throws Exception {
        String teammateId = UUID.randomUUID().toString();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/{id}/assignee?teammateId={value}", id, teammateId)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }

    private void testPutStart(String id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/{id}/start", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }

    private void testPutWorkHours(String id) throws Exception {
        Long workHours = 48L;

        mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/{id}/tracker?workHours={value}", id, workHours)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }

    private void testPutStop(String id) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/{id}/stop", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
    }

}
