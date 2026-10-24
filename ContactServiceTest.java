import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias del servicio de contactos:
 * agregar con ID unico, eliminar por ID y actualizar campos por ID.
 */
class ContactServiceTest {

    private ContactService service;
    private Contact contact;

    @BeforeEach
    void setUp() {
        service = new ContactService();
        contact = new Contact("001", "Diego", "Machuca", "5194500547", "Av. Los Alamos 123");
        service.addContact(contact);
    }

    // ------------------------------------------------------------------
    // Agregar
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Agregar un contacto lo deja disponible en el servicio")
    void testAddContact() {
        assertEquals(1, service.getContactCount());
        assertNotNull(service.getContact("001"));
        assertEquals("Diego", service.getContact("001").getFirstName());
    }

    @Test
    @DisplayName("Se pueden agregar varios contactos con IDs distintos")
    void testAddVariosContactos() {
        service.addContact(new Contact("002", "Glorys", "Perez", "5199887766", "Jr. Union 456"));

        assertEquals(2, service.getContactCount());
        assertEquals("Glorys", service.getContact("002").getFirstName());
    }

    @Test
    @DisplayName("No se permite agregar un contacto con un ID duplicado")
    void testAddContactIdDuplicado() {
        Contact duplicado = new Contact("001", "Otro", "Usuario", "5111111111", "Otra direccion");

        assertThrows(IllegalArgumentException.class, () -> service.addContact(duplicado));
        assertEquals(1, service.getContactCount());
        assertEquals("Diego", service.getContact("001").getFirstName());
    }

    @Test
    @DisplayName("No se permite agregar un contacto nulo")
    void testAddContactNulo() {
        assertThrows(IllegalArgumentException.class, () -> service.addContact(null));
    }

    // ------------------------------------------------------------------
    // Eliminar
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Eliminar por ID retira el contacto del servicio")
    void testDeleteContact() {
        service.deleteContact("001");

        assertEquals(0, service.getContactCount());
        assertNull(service.getContact("001"));
    }

    @Test
    @DisplayName("Eliminar un ID inexistente lanza excepcion")
    void testDeleteContactInexistente() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteContact("999"));
        assertEquals(1, service.getContactCount());
    }

    @Test
    @DisplayName("Eliminar con ID nulo lanza excepcion")
    void testDeleteContactIdNulo() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteContact(null));
    }

    // ------------------------------------------------------------------
    // Actualizar
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Actualizar firstName por ID")
    void testUpdateFirstName() {
        service.updateFirstName("001", "Daniel");

        assertEquals("Daniel", service.getContact("001").getFirstName());
    }

    @Test
    @DisplayName("Actualizar lastName por ID")
    void testUpdateLastName() {
        service.updateLastName("001", "Huaman");

        assertEquals("Huaman", service.getContact("001").getLastName());
    }

    @Test
    @DisplayName("Actualizar phone por ID")
    void testUpdatePhone() {
        service.updatePhone("001", "5100000000");

        assertEquals("5100000000", service.getContact("001").getPhone());
    }

    @Test
    @DisplayName("Actualizar address por ID")
    void testUpdateAddress() {
        service.updateAddress("001", "Calle Nueva 789");

        assertEquals("Calle Nueva 789", service.getContact("001").getAddress());
    }

    @Test
    @DisplayName("Actualizar un ID inexistente lanza excepcion en todos los campos")
    void testUpdateIdInexistente() {
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("999", "Daniel"));
        assertThrows(IllegalArgumentException.class, () -> service.updateLastName("999", "Huaman"));
        assertThrows(IllegalArgumentException.class, () -> service.updatePhone("999", "5100000000"));
        assertThrows(IllegalArgumentException.class, () -> service.updateAddress("999", "Calle Nueva 789"));
    }

    @Test
    @DisplayName("Un valor invalido no modifica el contacto existente")
    void testUpdateConValoresInvalidos() {
        assertThrows(IllegalArgumentException.class, () -> service.updateFirstName("001", null));
        assertThrows(IllegalArgumentException.class, () -> service.updateLastName("001", "ApellidoMuyLargo"));
        assertThrows(IllegalArgumentException.class, () -> service.updatePhone("001", "12345"));
        assertThrows(IllegalArgumentException.class,
                () -> service.updateAddress("001", "1234567890123456789012345678901"));

        Contact guardado = service.getContact("001");
        assertEquals("Diego", guardado.getFirstName());
        assertEquals("Machuca", guardado.getLastName());
        assertEquals("5194500547", guardado.getPhone());
        assertEquals("Av. Los Alamos 123", guardado.getAddress());
    }

    @Test
    @DisplayName("El ID del contacto se mantiene despues de actualizar otros campos")
    void testIdNoCambiaTrasUpdate() {
        service.updateFirstName("001", "Daniel");
        service.updateAddress("001", "Calle Nueva 789");

        assertEquals("001", service.getContact("001").getContactId());
    }
}
