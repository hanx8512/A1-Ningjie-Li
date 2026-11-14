import java.util.ArrayList;

/** Small independent checks for booking rules and data protection. */
public class AssignmentOneChecks {
    /** Runs checks without an external testing library. */
    public static void main(String[] args) {
        String[] days = {"Monday", "Wednesday"};
        GeneralPractitioner gp = new GeneralPractitioner("001", "Dr Test", days, "Clinic");
        days[0] = "Sunday";
        check(gp.getWorkingDays()[0].equals("Monday"), "Input working days must be copied");
        String[] returnedDays = gp.getWorkingDays();
        returnedDays[0] = "Sunday";
        check(gp.getWorkingDays()[0].equals("Monday"), "Returned working days must be copied");

        GeneralPractitioner sameId = new GeneralPractitioner("001", "Dr Test", "Clinic");
        check(gp.equals(sameId), "Matching IDs must be equal");
        check(gp.hashCode() == sameId.hashCode(), "Equal professionals need equal hashes");
        expectInvalidProfessional();

        Nurse nurse = new Nurse("002", "Nurse Test", days, "Ward");
        Patient one = new Patient("One", "0411111111");
        Patient two = new Patient("Two", "0422222222");
        AppointmentManager manager = new AppointmentManager();
        check(manager.addAppointment(new Appointment(one, gp, 15, 30)).startsWith("Added"),
            "15:30 must be a valid final slot");
        check(manager.addAppointment(new Appointment(two, sameId, 15, 30)).startsWith("Warning"),
            "Equal professional IDs cannot share a slot");
        check(manager.addAppointment(new Appointment(two, nurse, 15, 30)).startsWith("Added"),
            "Different professionals can share a slot");
        check(manager.addAppointment(new Appointment(one, gp, 8, 0)).startsWith("Added"),
            "08:00 must be a valid first slot");
        check(manager.addAppointment(new Appointment(two, gp, 16, 0)).startsWith("Warning"),
            "16:00 must be outside clinic hours");
        check(manager.addAppointment(new Appointment(two, gp, 8, 15)).startsWith("Warning"),
            "Start times must be on a half-hour slot");

        ArrayList<Appointment> all = manager.getAllAppointments();
        check(all.size() == 3, "Rejected bookings must not be stored");
        check(all.get(0).getTimeText().equals("08:00"), "Bookings must sort from earliest");
        check(manager.getByProfessionalId("001").size() == 2, "ID filter must find both slots");
        check(manager.getByPatientPhone("0411111111").size() == 2,
            "Phone filter must find both patient bookings");
        all.clear();
        check(manager.getAllAppointments().size() == 3, "Listings must be copies");
        check(manager.cancelByPatientPhone("0411111111").startsWith("Warning"),
            "Ambiguous phone cancellation needs a warning");
        check(manager.cancelByProfessionalAndTime("001", 8, 0).startsWith("Cancelled"),
            "Cancellation by ID and time must work");
        check(manager.cancelByPatientPhone("0411111111").startsWith("Cancelled"),
            "A unique phone match must cancel");
        check(manager.getAllAppointments().size() == 1, "Cancellations must update the collection");
        System.out.println("All appointment checks passed.");
    }

    /** Confirms a nonnumeric professional ID cannot be constructed. */
    private static void expectInvalidProfessional() {
        try {
            new GeneralPractitioner("A1", "Bad ID", "Clinic");
            throw new AssertionError("Nonnumeric IDs must be rejected");
        } catch (IllegalArgumentException expected) {
            // The constructor correctly rejected the invalid ID.
        }
    }

    /** Stops immediately when a requirement is not met. */
    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
