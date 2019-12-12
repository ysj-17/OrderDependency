import org.junit.Test;

import static org.junit.Assert.*;

public class FileDependenciesTest {
    private FileDependencies file;

    @Test
    public void readData() {
        file = new FileDependencies("dependenciesEmptyTest.txt");
        Dependencies test = file.readData();
        assertEquals(null,test);
        file = null;
    }

    // Test if correctly reading format if Dependency list does not list dependency
    // If no dependency listed for id
    @Test
    public void readData2(){
        file = new FileDependencies("dependenciesFormatTest1.txt");
        Dependencies test = file.readData();
        assertEquals(null,test);
        file = null;
    }

    // Test if can catch if error in format if one entry of id has more than 2 "columns"
    @Test
    public void readData3(){
        file = new FileDependencies("dependenciesFormatTest2.txt");
        Dependencies test = file.readData();
        assertEquals(null,test);
        file = null;
    }

    // Test if situation where mixed up order and dependency text files
    @Test
    public void readData4(){
        file = new FileDependencies("orders.txt");
        Dependencies test = file.readData();
        assertEquals(null,test);
        file = null;
    }
}