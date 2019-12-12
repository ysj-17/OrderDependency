import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class FileDependencies {
    private String fname = null;
    public FileDependencies(String fname) {
        this.fname = fname;
    }

    public Dependencies readData() {
        int lineCounter = 1;
        Dependencies dependencies = new Dependencies();

        try {
            // FileReader reads text file
            FileReader file = new FileReader(fname);
            int size = file.read();
            if (size == -1) throw new FileException("Dependency file is empty");
            // Wrap FileReader in BufferedReader
            BufferedReader buff = new BufferedReader(file);
            String line = buff.readLine();
            while (line != null) {
                if (line.matches("^[0-9]+[,](.*)")){
                    String[] lineSplit = line.split(",");
                    if (lineSplit.length > 2){
                        throw new FileException("Dependency file line "+lineCounter+" has more than 2 inputs");
                    } else {
                        if (lineSplit.length != 2) throw new FileException("Dependency file line "+lineCounter+" does not have two inputs");
                        int id = Integer.parseInt(lineSplit[0].trim());
                        int dependency = Integer.parseInt(lineSplit[1].trim());
                        dependencies.addDependency(id,dependency);
                    }
                }
                line = buff.readLine();
                lineCounter++;
            }
            buff.close();
        } catch (Exception e) {
            System.out.println("Error -- " + e.toString());
            return null;
        }

        return dependencies;
    }
}
