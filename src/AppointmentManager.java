import java.util.ArrayList;
import java.util.Collections;

/** Manages the current day's appointments in one collection. */
public class AppointmentManager {
    // Every value is a start time. The final 15:30 appointment ends at 16:00.
    private static final int[] DAILY_SLOTS = {
        480, 510, 540, 570, 600, 630, 660, 690,
        720, 750, 780, 810, 840, 870, 900, 930
    };

    private final ArrayList<Appointment> appointments;

    /** Starts with an empty schedule. */
    public AppointmentManager() {
        appointments = new ArrayList<Appointment>();
    }

    /** Adds a booking if the slot is open for that professional. */
    public String addAppointment(Appointment appointment) {
        if (appointment == null) {
            return "Warning: An appointment is required.";
        }
        if (!isDailySlot(appointment.getTimeInMinutes())) {
            return "Warning: " + appointment.getTimeText()
                + " is outside the 08:00-16:00 half-hour schedule.";
        }
        for (Appointment existing : appointments) {
            if (existing.getTimeInMinutes() == appointment.getTimeInMinutes()
                && existing.getProfessional().equals(appointment.getProfessional())) {
                return "Warning: " + appointment.getProfessional().getName()
                    + " is already booked at " + appointment.getTimeText() + ".";
            }
        }
        appointments.add(appointment);
        return "Added: " + appointment.getTimeText() + " for "
            + appointment.getPatient().getName() + " with "
            + appointment.getProfessional().getName() + ".";
    }

    /** Returns all appointments in time order. */
    public ArrayList<Appointment> getAllAppointments() {
        return sortedCopy(appointments);
    }

    /** Returns bookings for the given professional ID in time order. */
    public ArrayList<Appointment> getByProfessionalId(String id) {
        ArrayList<Appointment> matches = new ArrayList<Appointment>();
        if (id != null) {
            for (Appointment appointment : appointments) {
                if (appointment.getProfessional().getId().equals(id.trim())) {
                    matches.add(appointment);
                }
            }
        }
        return sortedCopy(matches);
    }

    /** Returns bookings for the given patient phone number in time order. */
    public ArrayList<Appointment> getByPatientPhone(String phone) {
        ArrayList<Appointment> matches = new ArrayList<Appointment>();
        if (phone != null) {
            for (Appointment appointment : appointments) {
                if (appointment.getPatient().getMobilePhone().equals(phone.trim())) {
                    matches.add(appointment);
                }
            }
        }
        return sortedCopy(matches);
    }

    /** Returns a readable list, or a warning when no appointment matches. */
    public String formatAppointments(ArrayList<Appointment> selected) {
        if (selected == null || selected.size() == 0) {
            return "Warning: No matching appointments were found.";
        }
        ArrayList<Appointment> sorted = sortedCopy(selected);
        String result = "Appointments (" + sorted.size() + "):\n";
        for (Appointment appointment : sorted) {
            result += "  " + appointment + "\n";
        }
        return result;
    }

    /** Cancels one booking identified by professional ID and time. */
    public String cancelByProfessionalAndTime(String id, int hour, int minute) {
        if (id == null || id.trim().length() == 0
            || hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            return "Warning: A valid professional ID and time are required.";
        }
        int requestedTime = hour * 60 + minute;
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            if (appointment.getProfessional().getId().equals(id.trim())
                && appointment.getTimeInMinutes() == requestedTime) {
                appointments.remove(i);
                return "Cancelled: " + appointment.getTimeText() + " with "
                    + appointment.getProfessional().getName() + ".";
            }
        }
        return "Warning: No appointment matches that professional ID and time.";
    }

    /**
     * Cancels by phone when it identifies exactly one booking. If several
     * bookings share a phone, a more precise professional ID and time is needed.
     */
    public String cancelByPatientPhone(String phone) {
        ArrayList<Appointment> matches = getByPatientPhone(phone);
        if (matches.size() == 0) {
            return "Warning: No appointment matches that patient phone number.";
        }
        if (matches.size() > 1) {
            return "Warning: This phone number has multiple appointments; "
                + "cancel by professional ID and time instead.";
        }
        Appointment appointment = matches.get(0);
        appointments.remove(appointment);
        return "Cancelled: " + appointment.getTimeText() + " for "
            + appointment.getPatient().getName() + ".";
    }

    /** Checks the fixed list of valid appointment start times. */
    private boolean isDailySlot(int time) {
        for (int slot : DAILY_SLOTS) {
            if (slot == time) {
                return true;
            }
        }
        return false;
    }

    /** Sorts a copy so callers cannot change the manager's collection. */
    private ArrayList<Appointment> sortedCopy(ArrayList<Appointment> source) {
        ArrayList<Appointment> copy = new ArrayList<Appointment>(source);
        Collections.sort(copy);
        return copy;
    }
}
