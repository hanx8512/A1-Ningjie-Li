/** A nurse assigned to a particular ward. */
public class Nurse extends HealthProfessional {
    private final String ward;

    /** Creates a nurse with chosen working days. */
    public Nurse(String id, String name, String[] days, String ward) {
        super(id, name, days);
        this.ward = requireText(ward, "Ward");
    }

    /** Returns the role name. */
    @Override
    public String getRole() {
        return "Nurse";
    }

    /** Describes a task a nurse can perform. */
    public String takeBloodSample() {
        return getName() + " took a blood sample.";
    }

    /** Includes both shared and nurse-specific information. */
    @Override
    public String toString() {
        return super.toString() + " | Ward: " + ward;
    }
}
