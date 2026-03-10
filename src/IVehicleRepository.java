import java.io.File;
import java.util.List;

public interface IVehicleRepository {

    public void rentVehicle(Vehicle vehicle);
    public Vehicle returnVehicle(Vehicle vehicle);
    public List<Vehicle> getVehicles();
    public void save(File file);
    public void load(File file);

}
