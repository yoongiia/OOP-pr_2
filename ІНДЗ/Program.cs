using System;
using System.Collections.Generic;

interface IAppointment
{
    void ScheduleAppointment(PetOwner owner, string appointmentType, DateTime dateTime);
}

class AppointmentDateTime
{
    private DateTime dateTime;

    public AppointmentDateTime(DateTime dateTime)
    {
        this.dateTime = dateTime;
    }

    public override string ToString()
    {
        return dateTime.ToString("yyyy-MM-dd HH:mm");
    }
}

abstract class Veterinarian
{
    public string Name { get; set; }

    public abstract void Treat(Animal animal);
    public abstract void Vaccinate(Animal animal);
}

class DoctorTherapist : Veterinarian
{
    public override void Treat(Animal animal)
    {
        Console.WriteLine($"Treating animal: {animal.Name}");
    }

    public override void Vaccinate(Animal animal)
    {
        Console.WriteLine($"Vaccinating animal: {animal.Name}");
    }
}

class Animal
{
    public string Name { get; }
    public string Species { get; }

    public Animal(string name, string species)
    {
        Name = name;
        Species = species;
    }
}

class PetOwner
{
    public string Name { get; }
    public Animal Animal { get; }

    public PetOwner(string name, Animal animal)
    {
        Name = name;
        Animal = animal;
    }

    public void ScheduleAnimalVisit(AppointmentSystem system, string appointmentType, DateTime dateTime)
    {
        system.ScheduleAppointment(this, appointmentType, dateTime);
    }
}

class AppointmentSystem : IAppointment
{
    private List<string> appointments = new List<string>();

    public void ScheduleAppointment(PetOwner owner, string appointmentType, DateTime dateTime)
    {
        string appointmentDetails = $"Appointment scheduled for {owner.Name} with animal: {owner.Animal.Name} for {appointmentType} at {new AppointmentDateTime(dateTime)}";
        appointments.Add(appointmentDetails);
        Console.WriteLine(appointmentDetails);
    }

    public void ShowAllAppointments()
    {
        Console.WriteLine("\nAll Appointments:");
        foreach (string appointment in appointments)
        {
            Console.WriteLine(appointment);
        }
    }
}

class Program
{
    static void Main(string[] args)
    {
        try
        {
            Console.Write("Enter pet owner's name: ");
            string ownerName = Console.ReadLine();

            Console.Write("Enter animal's name: ");
            string animalName = Console.ReadLine();

            Console.Write("Enter animal's species: ");
            string animalSpecies = Console.ReadLine();

            Animal animal = new Animal(animalName, animalSpecies);
            PetOwner owner = new PetOwner(ownerName, animal);
            AppointmentSystem system = new AppointmentSystem();

            Console.Write("Enter appointment date and time (yyyy-MM-dd HH:mm): ");
            string dateTimeInput = Console.ReadLine();
            DateTime appointmentDateTime = DateTime.ParseExact(dateTimeInput, "yyyy-MM-dd HH:mm", null);

            Console.Write("Enter appointment type (vaccination/treatment): ");
            string appointmentType = Console.ReadLine();

            owner.ScheduleAnimalVisit(system, appointmentType, appointmentDateTime);

            DoctorTherapist therapist = new DoctorTherapist();
            if (appointmentType.Equals("treatment", StringComparison.OrdinalIgnoreCase))
            {
                therapist.Treat(animal);
            }
            else if (appointmentType.Equals("vaccination", StringComparison.OrdinalIgnoreCase))
            {
                therapist.Vaccinate(animal);
            }
            else
            {
                Console.WriteLine("Invalid appointment type.");
            }

            system.ShowAllAppointments();
        }
        catch (Exception e)
        {
            Console.WriteLine($"An error occurred: {e.Message}");
        }
    }
}
