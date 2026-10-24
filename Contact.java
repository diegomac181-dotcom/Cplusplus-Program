/**
 * Objeto Contact.
 *
 * Reglas de negocio:
 *  - contactId : obligatorio, maximo 10 caracteres, no nulo, NO actualizable (final).
 *  - firstName : obligatorio, maximo 10 caracteres, no nulo.
 *  - lastName  : obligatorio, maximo 10 caracteres, no nulo.
 *  - phone     : obligatorio, exactamente 10 digitos, no nulo.
 *  - address   : obligatorio, maximo 30 caracteres, no nulo.
 */
public class Contact {

    private static final int ID_MAX_LENGTH = 10;
    private static final int NAME_MAX_LENGTH = 10;
    private static final int ADDRESS_MAX_LENGTH = 30;
    private static final int PHONE_LENGTH = 10;

    /** El ID es final: se asigna una sola vez y no puede actualizarse. */
    private final String contactId;

    private String firstName;
    private String lastName;
    private String phone;
    private String address;

    public Contact(String contactId, String firstName, String lastName, String phone, String address) {
        if (contactId == null || contactId.length() > ID_MAX_LENGTH) {
            throw new IllegalArgumentException("Invalid contact ID");
        }
        this.contactId = contactId;
        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
        setAddress(address);
    }

    // ------------------------------------------------------------------
    // Getters
    // ------------------------------------------------------------------

    public String getContactId() {
        return contactId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    // ------------------------------------------------------------------
    // Setters (no existe setContactId: el ID es inmutable)
    // ------------------------------------------------------------------

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("Invalid first name");
        }
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("Invalid last name");
        }
        this.lastName = lastName;
    }

    /** El telefono debe tener exactamente 10 digitos (0-9), sin espacios ni simbolos. */
    public void setPhone(String phone) {
        if (phone == null || phone.length() != PHONE_LENGTH || !phone.matches("\\d{" + PHONE_LENGTH + "}")) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        this.phone = phone;
    }

    public void setAddress(String address) {
        if (address == null || address.length() > ADDRESS_MAX_LENGTH) {
            throw new IllegalArgumentException("Invalid address");
        }
        this.address = address;
    }

    @Override
    public String toString() {
        return "Contact{contactId='" + contactId + "', firstName='" + firstName
                + "', lastName='" + lastName + "', phone='" + phone
                + "', address='" + address + "'}";
    }
}
