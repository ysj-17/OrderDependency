import java.io.PrintWriter;
import java.util.*;

public class CalculateDependencies {
    private String orderFile;
    private String dependencyFile;
    private String writeFile = "output.txt"; // Default value unless changed
    private FileOrders fileOrders;
    private FileDependencies fileDependencies;
    private HashMap<Integer,Orders> orderMap;
    private Dependencies dependencies;
    private ArrayList<String> completedDependencies = new ArrayList<>();
    private TrieTree trieTree;
    private ArrayList<Object> formattedTree = new ArrayList<>();

    public CalculateDependencies(){};

    // Function sets Order File name
    public void setOrderFile(String filename){
        this.orderFile = filename;
    }

    // Function sets Dependency File name
    public void setDependencyFile(String filename){
        this.dependencyFile = filename;
    }

    // Function can change output file name
    public void changeDefaultWriteFile(String filename){
        this.writeFile = filename;
    }

    public HashMap<Integer,Orders> getOrderMap(){
        return orderMap;
    }

    public Dependencies getDependencies(){
        return dependencies;
    }

    public ArrayList<String> getCompletedDependencies(){
        return completedDependencies;
    }

    // Function reads Order and Dependency files
    // Can throw own Exceptions
    public void readOrderAndDependencyFiles(){
        fileOrders = new FileOrders(orderFile);
        fileDependencies = new FileDependencies(dependencyFile);
        if (fileOrders != null && fileDependencies != null){
            orderMap = fileOrders.readData();
            dependencies = fileDependencies.readData();
            TrieTree.DEPENDENCY_SIZE = findOrderSize();
        }
    }

    public int findOrderSize(){
        int maxOrder = 0;
        for (Map.Entry<Integer,Orders> entry : orderMap.entrySet())
        {
            maxOrder = Math.max(maxOrder,entry.getKey());
        }
        return maxOrder + 1;
    }

    public void makeDependencyTree(){
        if (orderMap == null || dependencies == null) return;
        trieTree = new TrieTree();

        // Sort orderMap
        TreeMap<Integer,Orders> sortedOrders = new TreeMap<>(orderMap);
        for (Map.Entry<Integer,Orders> entry :sortedOrders.entrySet()) {
            if (dependencies.getDependencies(entry.getValue().getId()) != null) {
                for (int depend : dependencies.getDependencies(entry.getValue().getId())) {
                    trieTree.insert(entry.getKey(),depend);
                }
            }
        }
        combineOrdersAndDependencies();
        formattedTree = trieTree.formatTree();
    }

    public void combineOrdersAndDependencies(){
        for (Map.Entry<Integer,Orders> entry :orderMap.entrySet()){
            trieTree.combineOrdersAndDependencies(entry.getKey());
        }
    }


    public void writeOAndDToFile() {
        try {
            PrintWriter writer = new PrintWriter(writeFile,"UTF-8");
            writeDependencyTree(writer);
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void writeDependencyTree(PrintWriter writer){
        int level = 0;
        for (Object obj : formattedTree){
            if (obj instanceof Integer){
               writer.println("Id: "+obj+", Name: "+orderMap.get(obj).getName());
            } else if (obj instanceof ArrayList){
                if (((ArrayList) obj).size() > 0){
                    writer.println("Dependencies");
                    writeDependencyLevels((ArrayList)obj,level+1,writer);
                }
            }
        }
    }

    private void writeDependencyLevels(ArrayList<Object> list, int level, PrintWriter writer){
        int gap = 3 * level;
        for (Object obj : list){
            if (obj instanceof Integer) {
                writer.printf("%"+gap+"s"+"Id: " + obj + ", Name: " + orderMap.get(obj).getName()+"\n"," ");
            }
            else if (obj instanceof ArrayList){
                if (((ArrayList) obj).size() > 0)
                    writer.printf("%"+gap+"s"+"Dependencies\n"," ");
                writeDependencyLevels((ArrayList)obj,level+1,writer);
            }
        }
    }
}
