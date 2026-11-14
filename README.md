# PROG2004 Assessment 1: Current-day appointment system

## Run the complete demonstration

The entry point is `public static void main(String[] args)` in `src/AssignmentOne.java`. The project uses only the Java standard library and works with Java 11 or later. Extract the ZIP, open a terminal in the extracted project directory (the directory containing `src`), and use the commands for your system. A JDK must be installed so that both `javac` and `java` are available.

Windows PowerShell:

```powershell
New-Item -ItemType Directory -Force out | Out-Null
javac -d out (Get-ChildItem .\src\*.java).FullName
java -cp out AssignmentOne
```

macOS or Linux:

```sh
mkdir -p out
javac -d out src/*.java
java -cp out AssignmentOne
```

In a Java IDE, open this project directory and run the `main` method in `AssignmentOne.java`. The console output shows the professionals, successful and rejected bookings, sorted and filtered lists, cancellations, and the updated list.

## Design

- `HealthProfessional` is an abstract parent for four concrete roles. Its ID, name and working days are validated at construction. Working days are copied on input and output, so callers cannot change the stored schedule indirectly.
- The concrete roles have their own details and behaviours. A `HealthProfessional` array demonstrates polymorphic printing. Two professionals are equal when their IDs match; `hashCode` uses the same ID.
- `Patient` contains patient name and mobile number. `Appointment` contains a patient, any kind of health professional and a current-day time. It implements `Comparable<Appointment>` using minutes after midnight.
- `AppointmentManager` stores appointments in an `ArrayList`. It accepts starts every 30 minutes from 08:00 through 15:30. The final appointment ends at 16:00. It prevents the same professional ID from being booked twice at the same time. Different professionals can share a time.
- All listings and filtered listings are sorted by time. Cancellation by phone works when the phone identifies one appointment. If it identifies several, the method returns a warning and asks for the professional ID and time, avoiding an arbitrary cancellation.
- Working days are recorded as required, but this simulation contains no calendar date. The brief requires only current-day booking and does not specify a rule for comparing a professional's weekly days with a date.

`AssignmentOne.main` demonstrates valid bookings, duplicate and invalid bookings, all and filtered listings, both cancellation methods, and warnings for unsuccessful operations.
