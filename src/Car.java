public class Car extends Vehicle{
    String typeOfVehicle;
    public Car(String typeOfVehicle, String id, String brand, String model, int year, double price, boolean rented) {
        super(id, brand, model, year, price, rented);
        this.typeOfVehicle = typeOfVehicle;
    }

}
