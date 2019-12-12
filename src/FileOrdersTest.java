import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class FileOrdersTest {
    private FileOrders file;

    // Test empty file
    @Test
    public void readData() {
        file = new FileOrders("ordersEmptyTest.txt");
        HashMap<Integer,Orders> test = file.readData();
        assertEquals(null,test);
        file = null;
    }

    // Test if correctly reading format if order list does not include name
    @Test
    public void readData2(){
        file = new FileOrders("ordersFormatTest1.txt");
        HashMap<Integer,Orders> test = file.readData();
        assertEquals(null,test);
        file = null;
    }

    // Test if can catch if error in format if one entry of id has more than 2 "columns"
    @Test
    public void readData3(){
        file = new FileOrders("ordersFormatTest2.txt");
        HashMap<Integer,Orders> test = file.readData();
        assertEquals(null,test);
        file = null;
    }

    // Test if situation where mixed up order and dependency text files
    @Test
    public void readData4(){
        file = new FileOrders("dependencies.txt");
        HashMap<Integer,Orders> test = file.readData();
        assertEquals(null,test);
        file = null;
    }
}