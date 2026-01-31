package sg.edu.nus.iss.d13revision.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sg.edu.nus.iss.d13revision.models.Person;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {
    
    private PersonService personService;
    
    @BeforeEach
    void setUp() {
        personService = new PersonService();
    }
    
    @Test
    void testPersonServiceInitialization() {
        assertNotNull(personService);
        List<Person> persons = personService.getPersons();
        assertNotNull(persons);
        assertEquals(2, persons.size());
    }
    
    @Test
    void testGetPersons() {
        List<Person> persons = personService.getPersons();
        assertEquals(2, persons.size());
        
        // Check if initial persons are Mark Zuckerberg and Elon Musk
        assertTrue(persons.stream().anyMatch(p -> "Mark".equals(p.getFirstName())));
        assertTrue(persons.stream().anyMatch(p -> "Elon".equals(p.getFirstName())));
    }
    
    @Test
    void testAddPerson() {
        List<Person> personsBefore = personService.getPersons();
        int initialSize = personsBefore.size();
        
        Person newPerson = new Person("Steve", "Jobs");
        personService.addPerson(newPerson);
        
        List<Person> personsAfter = personService.getPersons();
        assertEquals(initialSize + 1, personsAfter.size());
        assertTrue(personsAfter.stream().anyMatch(p -> "Steve".equals(p.getFirstName()) && "Jobs".equals(p.getLastName())));
    }
    
    @Test
    void testAddMultiplePersons() {
        List<Person> persons = personService.getPersons();
        int initialSize = persons.size();
        
        personService.addPerson(new Person("Bill", "Gates"));
        personService.addPerson(new Person("Jeff", "Bezos"));
        
        persons = personService.getPersons();
        assertEquals(initialSize + 2, persons.size());
    }
    
    @Test
    void testRemovePerson() {
        List<Person> persons = personService.getPersons();
        int initialSize = persons.size();
        
        Person personToRemove = persons.get(0);
        personService.removePerson(personToRemove);
        
        persons = personService.getPersons();
        assertEquals(initialSize - 1, persons.size());
        assertFalse(persons.contains(personToRemove));
    }
    
    @Test
    void testRemoveNonExistentPerson() {
        List<Person> persons = personService.getPersons();
        int initialSize = persons.size();
        
        Person nonExistentPerson = new Person("999", "NonExistent", "Person");
        personService.removePerson(nonExistentPerson);
        
        persons = personService.getPersons();
        // Size should remain the same since person doesn't exist
        assertEquals(initialSize, persons.size());
    }
    
    @Test
    void testUpdatePerson() {
        List<Person> persons = personService.getPersons();
        Person personToUpdate = persons.get(0);
        String originalId = personToUpdate.getId();
        
        Person updatedPerson = new Person(originalId, "UpdatedFirst", "UpdatedLast");
        personService.updatePerson(updatedPerson);
        
        persons = personService.getPersons();
        Person foundPerson = persons.stream()
            .filter(p -> originalId.equals(p.getId()))
            .findAny()
            .orElse(null);
        
        assertNotNull(foundPerson);
        assertEquals("UpdatedFirst", foundPerson.getFirstName());
        assertEquals("UpdatedLast", foundPerson.getLastName());
    }
    
    @Test
    void testUpdatePersonPreservesId() {
        List<Person> persons = personService.getPersons();
        Person personToUpdate = persons.get(1);
        String originalId = personToUpdate.getId();
        
        Person updatedPerson = new Person(originalId, "NewFirst", "NewLast");
        personService.updatePerson(updatedPerson);
        
        persons = personService.getPersons();
        Person foundPerson = persons.stream()
            .filter(p -> originalId.equals(p.getId()))
            .findAny()
            .orElse(null);
        
        assertNotNull(foundPerson);
        assertEquals(originalId, foundPerson.getId());
    }
    
    @Test
    void testGetPersonsReturnsActualList() {
        List<Person> persons1 = personService.getPersons();
        List<Person> persons2 = personService.getPersons();
        
        // Should return the same list instance
        assertSame(persons1, persons2);
    }
    
    @Test
    void testAddPersonCreatesNewInstance() {
        Person originalPerson = new Person("Test", "User");
        String originalId = originalPerson.getId();
        
        personService.addPerson(originalPerson);
        
        List<Person> persons = personService.getPersons();
        Person addedPerson = persons.stream()
            .filter(p -> "Test".equals(p.getFirstName()) && "User".equals(p.getLastName()))
            .findFirst()
            .orElse(null);
        
        assertNotNull(addedPerson);
        // The added person should have a different ID than the original
        assertNotEquals(originalId, addedPerson.getId());
    }
}
