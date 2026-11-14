/**
 * Stores the details shared by every kind of health professional.
 * A plain health professional cannot be created because each one needs a role.
 */
public abstract class HealthProfessional {
    private static final String[] VALID_DAYS = {
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };
    private static final String[] DEFAULT_DAYS = {
        "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"
    };

    private final String id;
    private final String name;
    private final String[] workingDays;

    /** Creates a professional with explicitly selected working days. */
    public HealthProfessional(String id, String name, String[] workingDays) {
        if (id == null || id.length() == 0) {
            throw new IllegalArgumentException("Professional ID is required.");
        }
        for (int i = 0; i < id.length(); i++) {
            if (id.charAt(i) < '0' || id.charAt(i) > '9') {
                throw new IllegalArgumentException("Professional ID must contain digits only.");
            }
        }
        if (workingDays == null || workingDays.length == 0) {
            throw new IllegalArgumentException("At least one working day is required.");
        }

        this.id = id;
        this.name = requireText(name, "Professional name");
        this.workingDays = new String[workingDays.length];

        for (int i = 0; i < workingDays.length; i++) {
            String validDay = null;
            for (String day : VALID_DAYS) {
                if (workingDays[i] != null && day.equalsIgnoreCase(workingDays[i].trim())) {
                    validDay = day;
                    break;
                }
            }
            if (validDay == null) {
                throw new IllegalArgumentException("Invalid working day: " + workingDays[i]);
            }
            for (int j = 0; j < i; j++) {
                if (this.workingDays[j].equals(validDay)) {
                    throw new IllegalArgumentException("Working days cannot be repeated.");
                }
            }
            // Copy the values so callers cannot later change this object's schedule.
            this.workingDays[i] = validDay;
        }
    }

    /** Creates a professional who works Monday to Friday by default. */
    public HealthProfessional(String id, String name) {
        this(id, name, DEFAULT_DAYS);
    }

    /** Returns a checked, trimmed value for required text fields. */
    protected static String requireText(String value, String fieldName) {
        if (value == null || value.trim().length() == 0) {
            throw new IllegalArgumentException(fieldName + " is required.");
        }
        return value.trim();
    }

    /** Returns the professional's numeric ID as text, preserving leading zeroes. */
    public String getId() {
        return id;
    }

    /** Returns the professional's name. */
    public String getName() {
        return name;
    }

    /** Returns a copy of the working days. */
    public String[] getWorkingDays() {
        String[] copy = new String[workingDays.length];
        for (int i = 0; i < workingDays.length; i++) {
            copy[i] = workingDays[i];
        }
        return copy;
    }

    /** Returns the name of the concrete professional role. */
    public abstract String getRole();

    /** Returns the shared information in a readable form. */
    @Override
    public String toString() {
        String daysText = "[";
        for (int i = 0; i < workingDays.length; i++) {
            if (i > 0) {
                daysText += ", ";
            }
            daysText += workingDays[i];
        }
        daysText += "]";
        return getRole() + " | ID: " + id + " | Name: " + name
            + " | Working days: " + daysText;
    }

    /** Professionals represent the same person when their IDs are equal. */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof HealthProfessional)) {
            return false;
        }
        HealthProfessional professional = (HealthProfessional) other;
        return id.equals(professional.id);
    }

    /** Returns a hash code consistent with ID-based equality. */
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
