import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Dependencies {

    private HashMap<Integer, ArrayList<Integer>> dependencyMap;
    public Dependencies(){dependencyMap = new HashMap<>();}
    public void addDependency(int id, int dependency){
        ArrayList<Integer> dependencyValues = dependencyMap.getOrDefault(id,new ArrayList<Integer>());
        dependencyValues.add(dependency);
        dependencyMap.put(id,dependencyValues);
    }

    public ArrayList<Integer> getDependencies(int id){
        if (dependencyMap.containsKey(id)){
            return dependencyMap.get(id);
        }
        return null;
    }

}
