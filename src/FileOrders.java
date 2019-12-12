
import java.io.BufferedReader;
import java.io.*;
import java.util.*;

public class FileOrders {
    private String fname = null;
    public FileOrders(String fname) {
        this.fname = fname;
    }

    public HashMap<Integer,Orders> readData() {
        HashMap<Integer,Orders> orderMap = new HashMap<>();
        int lineCounter = 1;

        try {
            // FileReader reads text file
            FileReader file = new FileReader(fname);
            int size = file.read();
            if (size == -1) throw new FileException("Order file is empty");
            // Wrap FileReader in BufferedReader
            BufferedReader buff = new BufferedReader(file);
            String line = buff.readLine();
            while (line != null) {
                if (line.matches("(-)*[0-9]+[,](.*)")){
                    String[] lineSplit = line.split(",");
                    if (lineSplit.length > 2){
                        throw new FileException("Order file line "+lineCounter+" has more than 2 inputs");
                    } else {
                        if (lineSplit.length != 2) throw new FileException("Order file line "+lineCounter+" does not have two inputs");
                        else {
                            int orderID = Integer.parseInt(lineSplit[0].trim());
                            if (orderID < 0 || orderID > 10000) throw new FileException("Order file line "+lineCounter+" ID does not fit specification");
                            String orderName = lineSplit[1].trim();
                            if (orderName.length() < 1) throw new FileException("Order file line "+lineCounter+" Order Name does not fit specification");
                            orderMap.put(orderID,new Orders(orderID,orderName));
                        }
                    }
                }
                if (line.matches("(-)*[0-9]+[,][0-9]+")) throw new FileException("Not in order file format");
                line = buff.readLine();
                lineCounter++;
            }
            buff.close();
        } catch (Exception e) {
            System.out.println("Error -- " + e.toString());
            return null;
        }

        return orderMap;
    }
}
