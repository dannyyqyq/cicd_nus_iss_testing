package sg.edu.nus.iss.d13revision.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    
    private Person person;
    
    @BeforeEach
    void setUp() {
        person = new Person();
    }
    
    @Test
    void testPersonNoArgsConstructor() {
        assertNotNull(person);
    }
    
    @Test
    void testPersonWithFirstAndLastName() {
        Person p = new Person("John", "Doe");
        assertNotNull(p.getId());
        assertEquals("John", p.getFirstName());
        assertEquals("Doe", p.getLastName());
        assertNotNull(p.getId());
        assertTrue(p.getId().length() > 0);
    }
    
    @Test
    void testPersonWithIdAndName() {
        Person p = new Person("123", "Jane", "Smith");
        assertEquals("123", p.getId());
        assertEquals("Jane", p.getFirstName());
        assertEquals("Smith", p.getLastName());
    }
    
    @Test
    void testSetFirstName() {
        person.setFirstName("Alice");
        assertEquals("Alice", person.getFirstName());
    }
    
    @Test
    void testSetLastName() {
        person.setLastName("Johnson");
        assertEquals("Johnson", person.getLastName());
    }
    
    @Test
    void testSetId() {
        person.setId("id-12345");
        assertEquals("id-12345", person.getId());
    }
    
    @Test
    void testPersonToString() {
        Person p = new Person("456", "Bob", "Wilson");
        String expected = "Person [firstName=Bob, id=456, lastName=Wilson]";
        assertEquals(expected, p.toString());
    }
    
    @Test
    void testPersonIdIsUnique() {
        Person p1 = new Person("Mark", "Zuckerberg");
        Person p2 = new Person("Elon", "Musk");
        assertNotEquals(p1.getId(), p2.getId());
    }
    
    @Test
    void testPersonIdLength() {
        Person p = new Person("Tom", "Hardy");
        // UUID substring(0,8) should have 8 characters
        assertEquals(8, p.getId().length());
    }
}
