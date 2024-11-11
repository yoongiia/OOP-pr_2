import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

interface Appointment {
    void scheduleAppointment(PetOwner owner, String appointmentType, LocalDateTime dateTime);
}

class AppointmentDateTime {
    private LocalDateTime dateTime;

    public AppointmentDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }
}

abstract class Veterinarian {
    String name;

    abstract void treat(Animal animal);  // лікування
    abstract void vaccinate(Animal animal);  // вакцинація
}


class DoctorTherapist extends Veterinarian {
    @Override
    void treat(Animal animal) {
        System.out.println("Treating animal: " + animal.getName());
    }

    @Override
    void vaccinate(Animal animal) {
        System.out.println("Vaccinating animal: " + animal.getName());
    }
}

class Animal {
    private String name;
    private String species;

    public Animal(String name, String species) {
        this.name = name;
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }
}

class PetOwner {
    private Animal animal;
    private String name;

    public PetOwner(String name, Animal animal) {
        this.name = name;
        this.animal = animal;
    }

    public String getName() {
        return name;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void scheduleAnimalVisit(AppointmentSystem system, String appointmentType, LocalDateTime dateTime) {
        system.scheduleAppointment(this, appointmentType, dateTime);
    }
}

class AppointmentSystem implements Appointment {
    private List<String> appointments = new ArrayList<>();

    @Override
    public void scheduleAppointment(PetOwner owner, String appointmentType, LocalDateTime dateTime) {
        String appointmentDetails = "Appointment scheduled for " + owner.getName() +
                " with animal: " + owner.getAnimal().getName() +
                " for " + appointmentType + " at " + new AppointmentDateTime(dateTime);
        appointments.add(appointmentDetails);
        System.out.println(appointmentDetails);
    }

    public void showAllAppointments() {
        System.out.println("\nAll Appointments:");
        for (String appointment : appointments) {
            System.out.println(appointment);
        }
    }
}


public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {  
            System.out.print("Enter pet owner's name: ");
            String ownerName = scanner.nextLine();

            System.out.print("Enter animal's name: ");
            String animalName = scanner.nextLine();

            System.out.print("Enter animal's species: ");
            String animalSpecies = scanner.nextLine();

            Animal animal = new Animal(animalName, animalSpecies);
            PetOwner owner = new PetOwner(ownerName, animal);
            AppointmentSystem system = new AppointmentSystem();

            System.out.print("Enter appointment date and time (yyyy-MM-dd HH:mm): ");
            String dateTimeInput = scanner.nextLine();
            LocalDateTime appointmentDateTime = LocalDateTime.parse(dateTimeInput, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            System.out.print("Enter appointment type (vaccination/treatment): ");
            String appointmentType = scanner.nextLine();

            owner.scheduleAnimalVisit(system, appointmentType, appointmentDateTime);

            DoctorTherapist therapist = new DoctorTherapist();
            if (appointmentType.equalsIgnoreCase("treatment")) {
                therapist.treat(animal);
            } else if (appointmentType.equalsIgnoreCase("vaccination")) {
                therapist.vaccinate(animal);
            } else {
                System.out.println("Invalid appointment type.");
            }

            system.showAllAppointments();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
