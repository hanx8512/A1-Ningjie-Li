/** Stores the patient details needed for an appointment. */
public class Patient {
    private final String name;
    private final String mobilePhone;

    /** Creates a patient with a name and mobile contact number. */
    public Patient(String name, String mobilePhone) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("Patient name is required.");
        }
        if (mobilePhone == null) {
            throw new IllegalArgumentException("Mobile phone number is required.");
        }

        String phone = mobilePhone.trim();
        int firstDigit = phone.startsWith("+") ? 1 : 0;
        int digitCount = phone.length() - firstDigit;
        if (digitCount < 8 || digitCount > 15) {
            throw new IllegalArgumentException("Mobile phone number needs 8 to 15 digits.");
        }
        for (int i = firstDigit; i < phone.length(); i++) {
            if (phone.charAt(i) < '0' || phone.charAt(i) > '9') {
                throw new IllegalArgumentException("Mobile phone number may contain only digits and a leading +.");
            }
        }

        this.name = name.trim();
        this.mobilePhone = phone;
    }

    /** Returns the patient's name. */
    public String getName() {
        return name;
    }

    /** Returns the patient's mobile phone number. */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /** Returns patient details suitable for an appointment listing. */
    @Override
    public String toString() {
        return name + " (" + mobilePhone + ")";
    }
}
