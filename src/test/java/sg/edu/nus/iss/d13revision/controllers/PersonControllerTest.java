package sg.edu.nus.iss.d13revision.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import sg.edu.nus.iss.d13revision.models.Person;
import sg.edu.nus.iss.d13revision.models.PersonForm;
import sg.edu.nus.iss.d13revision.services.PersonService;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PersonControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private PersonService personService;
    
    @BeforeEach
    void setUp() {
        // Reset the service before each test
        personService = new PersonService();
    }
    
    @Test
    void testIndexPage() throws Exception {
        mockMvc.perform(get("/person/"))
            .andExpect(status().isOk())
            .andExpect(view().name("index"))
            .andExpect(model().attributeExists("message"));
    }
    
    @Test
    void testIndexPageHome() throws Exception {
        mockMvc.perform(get("/person/home"))
            .andExpect(status().isOk())
            .andExpect(view().name("index"));
    }
    
    @Test
    void testIndexPageIndex() throws Exception {
        mockMvc.perform(get("/person/index"))
            .andExpect(status().isOk())
            .andExpect(view().name("index"));
    }
    
    @Test
    void testGetAllPersons() throws Exception {
        mockMvc.perform(get("/person/testRetrieve")
            .contentType("application/json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].firstName", is("Mark")))
            .andExpect(jsonPath("$[1].firstName", is("Elon")));
    }
    
    @Test
    void testPersonListPage() throws Exception {
        mockMvc.perform(get("/person/personList"))
            .andExpect(status().isOk())
            .andExpect(view().name("personList"))
            .andExpect(model().attributeExists("persons"))
            .andExpect(model().attribute("persons", hasSize(2)));
    }
    
    @Test
    void testShowAddPersonPage() throws Exception {
        mockMvc.perform(get("/person/addPerson"))
            .andExpect(status().isOk())
            .andExpect(view().name("addPerson"))
            .andExpect(model().attributeExists("personForm"));
    }
    
    @Test
    void testSavePersonSuccess() throws Exception {
        mockMvc.perform(post("/person/addPerson")
            .param("firstName", "John")
            .param("lastName", "Doe"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/person/personList"));
    }
    
    @Test
    void testSavePersonWithEmptyFirstName() throws Exception {
        mockMvc.perform(post("/person/addPerson")
            .param("firstName", "")
            .param("lastName", "Doe"))
            .andExpect(status().isOk())
            .andExpect(view().name("addPerson"))
            .andExpect(model().attributeExists("errorMessage"));
    }
    
    @Test
    void testSavePersonWithEmptyLastName() throws Exception {
        mockMvc.perform(post("/person/addPerson")
            .param("firstName", "John")
            .param("lastName", ""))
            .andExpect(status().isOk())
            .andExpect(view().name("addPerson"))
            .andExpect(model().attributeExists("errorMessage"));
    }
    
    @Test
    void testSavePersonWithBothNamesEmpty() throws Exception {
        mockMvc.perform(post("/person/addPerson")
            .param("firstName", "")
            .param("lastName", ""))
            .andExpect(status().isOk())
            .andExpect(view().name("addPerson"))
            .andExpect(model().attributeExists("errorMessage"));
    }
    
    @Test
    void testPersonToEdit() throws Exception {
        // First get a person from the list
        mockMvc.perform(post("/person/personToEdit")
            .param("id", "123")
            .param("firstName", "Mark")
            .param("lastName", "Zuckerberg"))
            .andExpect(status().isOk())
            .andExpect(view().name("editPerson"))
            .andExpect(model().attributeExists("per"));
    }
    
    @Test
    void testPersonEdit() throws Exception {
        mockMvc.perform(post("/person/personEdit")
            .param("id", "123")
            .param("firstName", "UpdatedName")
            .param("lastName", "UpdatedLast"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/person/personList"));
    }
    
    @Test
    void testPersonDelete() throws Exception {
        mockMvc.perform(post("/person/personDelete")
            .param("id", "123")
            .param("firstName", "Mark")
            .param("lastName", "Zuckerberg"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/person/personList"));
    }
}
