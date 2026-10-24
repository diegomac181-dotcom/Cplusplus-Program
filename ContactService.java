import java.util.HashMap;
import java.util.Map;

/**
 * Servicio de contactos en memoria (sin base de datos).
 *
 * Responsabilidades:
 *  - Agregar contactos con ID unico.
 *  - Eliminar contactos por ID.
 *  - Actualizar firstName, lastName, phone y address por ID.
 */
public class ContactService {

    /** Estructura en memoria: la clave es el contactId, garantizando unicidad. */
    private final Map<String, Contact> contacts = new HashMap<>();

    /**
     * Agrega un contacto. Lanza excepcion si el contacto es nulo
     * o si el ID ya existe en el servicio.
     */
    public void addContact(Contact contact) {
        if (contact == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }
        if (contacts.containsKey(contact.getContactId())) {
            throw new IllegalArgumentException("Contact ID already exists");
        }
        contacts.put(contact.getContactId(), contact);
    }

    /** Elimina un contacto por ID. Lanza excepcion si el ID no existe. */
    public void deleteContact(String contactId) {
        requireExisting(contactId);
        contacts.remove(contactId);
    }

    public void updateFirstName(String contactId, String firstName) {
        requireExisting(contactId).setFirstName(firstName);
    }

    public void updateLastName(String contactId, String lastName) {
        requireExisting(contactId).setLastName(lastName);
    }

    public void updatePhone(String contactId, String phone) {
        requireExisting(contactId).setPhone(phone);
    }

    public void updateAddress(String contactId, String address) {
        requireExisting(contactId).setAddress(address);
    }

    /** Devuelve el contacto o null si no existe (util para las pruebas). */
    public Contact getContact(String contactId) {
        return contacts.get(contactId);
    }

    /** Cantidad de contactos almacenados. */
    public int getContactCount() {
        return contacts.size();
    }

    /**
     * Valida que el ID exista y devuelve el contacto asociado.
     * Centraliza la comprobacion usada por delete y por todos los updates.
     */
    private Contact requireExisting(String contactId) {
        if (contactId == null || !contacts.containsKey(contactId)) {
            throw new IllegalArgumentException("Contact ID not found");
        }
        return contacts.get(contactId);
    }
}
