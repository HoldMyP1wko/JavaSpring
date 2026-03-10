import java.io.File;

public class Main {
    public static void main(String[] args) {
        Vehicle v1 = new Car(Car.class.getName().toUpperCase(), "0","Audi","A4",2022, 200.0,false);
        Vehicle v2 = new Car(Car.class.getName().toUpperCase(), "1","BMW","M4",2021, 240.0,false);
        Vehicle v3 = new Car(Car.class.getName().toUpperCase(),"3","Toyota","C-HR",2017,100.0,false);
        Vehicle v4 = new Motorcycle(Motorcycle.class.getName().toUpperCase(), "3","HONDA","CBD1000",2012, 400.0,false, DrivingLicense.A);
        Vehicle v5 = new Motorcycle(Motorcycle.class.getName().toUpperCase(), "4","HONDA","CBR125",2015, 130.0,false, DrivingLicense.B);

        VehicleRepositoryImpl vehicleRepository = new VehicleRepositoryImpl();
//        File file = new File("vehicles.txt");
//        vehicleRepository.load(file);

        for (Vehicle v : vehicleRepository.getVehicles()){
            System.out.println(v.toCSV());
        }


    }
}