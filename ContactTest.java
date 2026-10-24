import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias del objeto Contact.
 * Cada requisito del cliente tiene al menos una prueba positiva y una negativa.
 */
class ContactTest {

    // ------------------------------------------------------------------
    // Creacion valida
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Un contacto valido guarda todos sus campos")
    void testContactValido() {
        Contact contact = new Contact("1234567890", "Diego", "Machuca", "5194500547", "Av. Los Alamos 123, Lima");

        assertEquals("1234567890", contact.getContactId());
        assertEquals("Diego", contact.getFirstName());
        assertEquals("Machuca", contact.getLastName());
        assertEquals("5194500547", contact.getPhone());
        assertEquals("Av. Los Alamos 123, Lima", contact.getAddress());
    }

    @Test
    @DisplayName("Se aceptan valores exactamente en el limite permitido")
    void testValoresEnElLimite() {
        Contact contact = new Contact("ABCDE12345", "1234567890", "0987654321",
                "0123456789", "123456789012345678901234567890");

        assertEquals(10, contact.getContactId().length());
        assertEquals(10, contact.getFirstName().length());
        assertEquals(10, contact.getLastName().length());
        assertEquals(30, contact.getAddress().length());
    }

    // ------------------------------------------------------------------
    // contactId
    // ------------------------------------------------------------------

    @Test
    @DisplayName("El ID no puede ser nulo")
    void testIdNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact(null, "Diego", "Machuca", "5194500547", "Lima"));
    }

    @Test
    @DisplayName("El ID no puede exceder 10 caracteres")
    void testIdMuyLargo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("12345678901", "Diego", "Machuca", "5194500547", "Lima"));
    }

    @Test
    @DisplayName("El ID no es actualizable: la clase no expone un setter")
    void testIdNoActualizable() {
        boolean tieneSetter = Arrays.stream(Contact.class.getDeclaredMethods())
                .map(Method::getName)
                .anyMatch(name -> name.equalsIgnoreCase("setContactId"));

        assertFalse(tieneSetter, "Contact no debe exponer setContactId");
    }

    // ------------------------------------------------------------------
    // firstName
    // ------------------------------------------------------------------

    @Test
    @DisplayName("firstName no puede ser nulo")
    void testFirstNameNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("001", null, "Machuca", "5194500547", "Lima"));
    }

    @Test
    @DisplayName("firstName no puede exceder 10 caracteres")
    void testFirstNameMuyLargo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("001", "Maximiliano", "Machuca", "5194500547", "Lima"));
    }

    @Test
    @DisplayName("setFirstName rechaza valores invalidos")
    void testSetFirstNameInvalido() {
        Contact contact = new Contact("001", "Diego", "Machuca", "5194500547", "Lima");

        assertThrows(IllegalArgumentException.class, () -> contact.setFirstName(null));
        assertThrows(IllegalArgumentException.class, () -> contact.setFirstName("NombreDemasiadoLargo"));
        assertEquals("Diego", contact.getFirstName());
    }

    // ------------------------------------------------------------------
    // lastName
    // ------------------------------------------------------------------

    @Test
    @DisplayName("lastName no puede ser nulo")
    void testLastNameNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("001", "Diego", null, "5194500547", "Lima"));
    }

    @Test
    @DisplayName("lastName no puede exceder 10 caracteres")
    void testLastNameMuyLargo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("001", "Diego", "Machucarodriguez", "5194500547", "Lima"));
    }

    @Test
    @DisplayName("setLastName rechaza valores invalidos")
    void testSetLastNameInvalido() {
        Contact contact = new Contact("001", "Diego", "Machuca", "5194500547", "Lima");

        assertThrows(IllegalArgumentException.class, () -> contact.setLastName(null));
        assertThrows(IllegalArgumentException.class, () -> contact.setLastName("ApellidoMuyLargo"));
        assertEquals("Machuca", contact.getLastName());
    }

    // ------------------------------------------------------------------
    // phone
    // ------------------------------------------------------------------

    @Test
    @DisplayName("phone no puede ser nulo")
    void testPhoneNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("001", "Diego", "Machuca", null, "Lima"));
    }

    @Test
    @DisplayName("phone con menos de 10 digitos es rechazado")
    void testPhoneCorto() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("001", "Diego", "Machuca", "945005", "Lima"));
    }

    @Test
    @DisplayName("phone con mas de 10 digitos es rechazado")
    void testPhoneLargo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("001", "Diego", "Machuca", "51945005474", "Lima"));
    }

    @Test
    @DisplayName("phone con caracteres no numericos es rechazado")
    void testPhoneNoNumerico() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("001", "Diego", "Machuca", "945-005-47", "Lima"));
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("002", "Diego", "Machuca", "945 005 47", "Lima"));
    }

    @Test
    @DisplayName("setPhone rechaza valores invalidos y conserva el anterior")
    void testSetPhoneInvalido() {
        Contact contact = new Contact("001", "Diego", "Machuca", "5194500547", "Lima");

        assertThrows(IllegalArgumentException.class, () -> contact.setPhone("123"));
        assertEquals("5194500547", contact.getPhone());
    }

    // ------------------------------------------------------------------
    // address
    // ------------------------------------------------------------------

    @Test
    @DisplayName("address no puede ser nulo")
    void testAddressNulo() {
        assertThrows(IllegalArgumentException.class,
                () -> new Contact("001", "Diego", "Machuca", "5194500547", null));
    }

    @Test
    @DisplayName("address no puede exceder 30 caracteres")
    void testAddressMuyLarga() {
        String direccion = "Av. Javier Prado Este 1234, San Isidro, Lima, Peru";

        assertThrows(IllegalArgumentException.class,
                () -> new Contact("001", "Diego", "Machuca", "5194500547", direccion));
    }

    @Test
    @DisplayName("setAddress rechaza valores invalidos y conserva el anterior")
    void testSetAddressInvalida() {
        Contact contact = new Contact("001", "Diego", "Machuca", "5194500547", "Lima");

        assertThrows(IllegalArgumentException.class, () -> contact.setAddress(null));
        assertThrows(IllegalArgumentException.class,
                () -> contact.setAddress("1234567890123456789012345678901"));
        assertEquals("Lima", contact.getAddress());
    }

    @Test
    @DisplayName("toString incluye los datos del contacto")
    void testToString() {
        Contact contact = new Contact("001", "Diego", "Machuca", "5194500547", "Lima");

        String texto = contact.toString();

        assertTrue(texto.contains("001"));
        assertTrue(texto.contains("Diego"));
        assertTrue(texto.contains("5194500547"));
    }
}
