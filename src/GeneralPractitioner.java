/** A general practitioner who works at a named clinic. */
public class GeneralPractitioner extends HealthProfessional {
    private final String clinic;

    /** Creates a GP with chosen working days. */
    public GeneralPractitioner(String id, String name, String[] days, String clinic) {
        super(id, name, days);
        this.clinic = requireText(clinic, "Clinic");
    }

    /** Creates a GP with the default Monday-to-Friday schedule. */
    public GeneralPractitioner(String id, String name, String clinic) {
        super(id, name);
        this.clinic = requireText(clinic, "Clinic");
    }

    /** Returns the role name. */
    @Override
    public String getRole() {
        return "General practitioner";
    }

    /** Describes an action specific to a doctor. */
    public String prescribeMedication(String medication) {
        return getName() + " prescribed " + requireText(medication, "Medication") + ".";
    }

    /** Includes both shared and GP-specific information. */
    @Override
    public String toString() {
        return super.toString() + " | Clinic: " + clinic;
    }
}
