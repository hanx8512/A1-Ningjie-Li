/** Runs a scripted demonstration of the current-day appointment system. */
public class AssignmentOne {
    /** Creates professionals and demonstrates both successful and failed operations. */
    public static void main(String[] args) {
        String[] weekdays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[] alternateDays = {"Monday", "Wednesday", "Friday"};

        GeneralPractitioner gpA = new GeneralPractitioner("1001", "Dr Maya Chen", weekdays, "City Clinic");
        GeneralPractitioner gpB = new GeneralPractitioner("1002", "Dr Oliver Reed", "Westside Clinic");
        GeneralPractitioner gpC = new GeneralPractitioner("1003", "Dr Amira Khan", alternateDays, "City Clinic");
        Specialist specialistA = new Specialist("2001", "Dr Sofia Patel", weekdays, "Cardiology");
        Specialist specialistB = new Specialist("2002", "Dr James Lee", alternateDays, "Paediatrics");
        Nurse nurseA = new Nurse("3001", "Grace Walker", weekdays, "Outpatients");
        Nurse nurseB = new Nurse("3002", "Noah Martin", alternateDays, "Pathology");
        Dietitian dietitianA = new Dietitian("4001", "Emma Wilson", weekdays, "Sports nutrition");

        // A common parent type lets one loop work with every concrete role.
        HealthProfessional[] professionals = {
            gpA, gpB, gpC, specialistA, specialistB, nurseA, nurseB, dietitianA
        };

        section("PARTS 1-3: HEALTH PROFESSIONALS");
        for (HealthProfessional professional : professionals) {
            System.out.println(professional);
        }
        System.out.println("\nDifferent roles perform different tasks:");
        System.out.println(gpA.prescribeMedication("an antibiotic"));
        System.out.println(specialistA.requestDiagnosticTest("an ECG"));
        System.out.println(nurseA.takeBloodSample());
        System.out.println(dietitianA.createMealPlan());

        Patient alice = new Patient("Alice Brown", "0412345678");
        Patient ben = new Patient("Ben Taylor", "0412345679");
        Patient chloe = new Patient("Chloe Davis", "0412345680");
        Patient daniel = new Patient("Daniel Moore", "0412345681");
        Patient eva = new Patient("Eva White", "0412345682");
        Patient finn = new Patient("Finn Harris", "0412345683");
        Patient gia = new Patient("Gia Thomas", "0412345684");

        AppointmentManager manager = new AppointmentManager();
        section("PARTS 4-6: ADD APPOINTMENTS");
        // Insert out of time order to show that listings sort correctly.
        System.out.println(manager.addAppointment(new Appointment(chloe, specialistA, 10, 30)));
        System.out.println(manager.addAppointment(new Appointment(alice, gpA, 8, 0)));
        System.out.println(manager.addAppointment(new Appointment(ben, nurseA, 9, 0)));
        System.out.println(manager.addAppointment(new Appointment(alice, dietitianA, 9, 0)));
        System.out.println(manager.addAppointment(new Appointment(daniel, gpB, 11, 30)));
        System.out.println(manager.addAppointment(new Appointment(eva, specialistB, 15, 30)));
        System.out.println(manager.addAppointment(new Appointment(finn, gpA, 13, 0)));
        System.out.println(manager.addAppointment(new Appointment(gia, nurseB, 8, 30)));

        section("FAILED BOOKINGS");
        // A second object with ID 1001 still represents the same professional.
        GeneralPractitioner sameGp = new GeneralPractitioner("1001", "Dr Maya Chen", "City Clinic");
        System.out.println(manager.addAppointment(new Appointment(ben, sameGp, 8, 0)));
        System.out.println(manager.addAppointment(new Appointment(ben, gpC, 16, 0)));
        System.out.println(manager.addAppointment(new Appointment(ben, gpC, 8, 15)));
        System.out.println(manager.addAppointment(null));
        try {
            new Appointment(new Patient("", "0412345699"), gpA, 10, 0);
        } catch (IllegalArgumentException error) {
            System.out.println("Warning: " + error.getMessage());
        }

        section("ALL APPOINTMENTS, SORTED BY TIME");
        System.out.print(manager.formatAppointments(manager.getAllAppointments()));

        section("FILTERED BY PROFESSIONAL ID 1001, SORTED BY TIME");
        System.out.print(manager.formatAppointments(manager.getByProfessionalId("1001")));

        section("FILTERED BY PATIENT PHONE 0412345678, SORTED BY TIME");
        System.out.print(manager.formatAppointments(manager.getByPatientPhone("0412345678")));

        section("FILTER WITH NO MATCHES");
        System.out.println(manager.formatAppointments(manager.getByProfessionalId("9999")));

        section("CANCELLATIONS");
        System.out.println(manager.cancelByProfessionalAndTime("1001", 13, 0));
        System.out.println(manager.cancelByPatientPhone("0412345679"));
        System.out.println(manager.cancelByPatientPhone("0412345678"));
        System.out.println(manager.cancelByProfessionalAndTime("1001", 14, 0));
        System.out.println(manager.cancelByPatientPhone("0499999999"));

        section("APPOINTMENTS AFTER CANCELLATION");
        System.out.print(manager.formatAppointments(manager.getAllAppointments()));
    }

    /** Prints a clear heading between parts of the demonstration. */
    private static void section(String title) {
        System.out.println("\n=== " + title + " ===");
    }
}
