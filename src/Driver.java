
public class Driver {
    public static void main(String[] args) {
        CalculateDependencies calculateDependencies = new CalculateDependencies();
        calculateDependencies.setOrderFile("orders2.txt");
        calculateDependencies.setDependencyFile("dependencies2.txt");
        calculateDependencies.readOrderAndDependencyFiles();
        calculateDependencies.makeDependencyTree();
        calculateDependencies.writeOAndDToFile();
    }
}
