public abstract class Vehicle{
    String id;
    String brand;
    String model;
    int year;
    double price;
    boolean isRented;

    public Vehicle(String id, String brand, String model, int year, double price, boolean rented) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.price = price;
        this.isRented = rented;
    }

    public String toCSV(){
        return id + ";" + this.brand + ";" + this.model +  ";" + this.year
                + ";" + this.price + ";";
    }

    public Vehicle(Vehicle vehicle){
        this.id = vehicle.id;
        this.brand = vehicle.brand;
        this.model = vehicle.model;
        this.year = vehicle.year;
        this.price = vehicle.price;
        this.isRented = vehicle.isRented;
    }

//    @Override
//    public String toString(){
//
//    }


}
