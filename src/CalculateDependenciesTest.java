import org.junit.Test;

import static org.junit.Assert.*;

public class CalculateDependenciesTest {

    // Test if variables set if wrong files given
    @Test
    public void readOrderAndDependencyFiles1() {
        CalculateDependencies test = new CalculateDependencies();
        test.setOrderFile("");
        test.setDependencyFile("dependencies.txt");
        assertEquals(null,test.getOrderMap());
        assertEquals(null,test.getDependencies());
    }

    // Test if variables set if wrong files given
    @Test
    public void readOrderAndDependencyFiles2() {
        CalculateDependencies test = new CalculateDependencies();
        test.setOrderFile("orders.txt");
        test.setDependencyFile("");
        assertEquals(null,test.getOrderMap());
        assertEquals(null,test.getDependencies());
    }

}