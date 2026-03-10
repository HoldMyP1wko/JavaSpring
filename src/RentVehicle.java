import java.util.Scanner;

public class RentVehicle extends VehicleRepositoryImpl {
    DrivingLicense drivingLicense;
    VehicleRepositoryImpl vehicleRepository = new VehicleRepositoryImpl();
    static Scanner scanner = new Scanner(System.in);


    public void run(){
        System.out.println("Witaj w systemie wynajmu pojazdów");
        System.out.println("""
                Jeśli chcesz sprawdzić dostępne pojazdy wpisz: 1\s
                Jeśli chcesz wynająć pojazd wpisz:             2\s
                Jeśli chcesz zakończyć wpisz :                 3\s
               \s""");

        int choice = scanner.nextInt();
        switch (choice){
            case 1:
                for (Vehicle v : vehicleRepository.getVehicles()){
                System.out.println(v.toString());
                }
            case 2:
                for (Vehicle v : vehicleRepository.getVehicles()){
                    System.out.println(v.toString());
                }
                System.out.println("Wybierz id pojazdu, który chcesz wynająć");
                int choiceForRent = scanner.nextInt();
                Vehicle curr = vehicleRepository.getVehicles().get(choiceForRent - 1);
                System.out.println(curr.toString());
                if (!curr.isRented){
                    rentVehicle(curr);
                } else {
                    System.out.println("Nie możesz wynająć wynajętego pojazdu");
                }
            case 3:
        }
    }


}
