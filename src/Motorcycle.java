public class Motorcycle extends Vehicle{
    DrivingLicense drivingLicense;
    String typeOfVehicle;

    public Motorcycle(String typeOfVehicle, String id, String brand, String model, int year, double price, boolean rented, DrivingLicense drivingLicense) {
        super(id, brand, model, year, price, rented);
        this.drivingLicense = drivingLicense;
    }
}
