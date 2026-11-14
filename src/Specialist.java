/** A doctor with a particular medical specialty. */
public class Specialist extends HealthProfessional {
    private final String specialty;

    /** Creates a specialist with chosen working days. */
    public Specialist(String id, String name, String[] days, String specialty) {
        super(id, name, days);
        this.specialty = requireText(specialty, "Specialty");
    }

    /** Returns the role name. */
    @Override
    public String getRole() {
        return "Specialist";
    }

    /** Describes a specialist requesting a diagnostic test. */
    public String requestDiagnosticTest(String test) {
        return getName() + " requested " + requireText(test, "Test") + ".";
    }

    /** Includes both shared and specialist-specific information. */
    @Override
    public String toString() {
        return super.toString() + " | Specialty: " + specialty;
    }
}
