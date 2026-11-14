/** Links a patient, a professional and a time on the current day. */
public class Appointment implements Comparable<Appointment> {
    private final Patient patient;
    private final HealthProfessional professional;
    private final int timeInMinutes;

    /** Creates an appointment using a 24-hour time. */
    public Appointment(Patient patient, HealthProfessional professional, int hour, int minute) {
        if (patient == null || professional == null) {
            throw new IllegalArgumentException("Patient and professional are required.");
        }
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Appointment time is invalid.");
        }
        this.patient = patient;
        this.professional = professional;
        this.timeInMinutes = hour * 60 + minute;
    }

    /** Returns the patient. */
    public Patient getPatient() {
        return patient;
    }

    /** Returns the professional, whatever the concrete role is. */
    public HealthProfessional getProfessional() {
        return professional;
    }

    /** Returns the time as minutes after midnight for simple comparisons. */
    public int getTimeInMinutes() {
        return timeInMinutes;
    }

    /** Returns the appointment time in HH:mm format. */
    public String getTimeText() {
        int hour = timeInMinutes / 60;
        int minute = timeInMinutes % 60;
        String hourText = hour < 10 ? "0" + hour : "" + hour;
        String minuteText = minute < 10 ? "0" + minute : "" + minute;
        return hourText + ":" + minuteText;
    }

    /** Orders appointments from earliest to latest. */
    @Override
    public int compareTo(Appointment other) {
        if (timeInMinutes < other.timeInMinutes) {
            return -1;
        }
        if (timeInMinutes > other.timeInMinutes) {
            return 1;
        }
        return 0;
    }

    /** Returns the appointment and the chosen professional's full details. */
    @Override
    public String toString() {
        return getTimeText() + " | Patient: " + patient + " | " + professional;
    }
}
