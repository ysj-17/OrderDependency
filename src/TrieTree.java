import java.util.*;

public class TrieTree {

    static int DEPENDENCY_SIZE;
    static HashMap<Integer,TrieNode> nodeMap = new HashMap<>();

    class TrieNode {
        TrieNode[] children = new TrieNode[DEPENDENCY_SIZE];
        int data;
        boolean leafNode;
        boolean firstNode;
        TrieNode(int data){
            this.data = data;
            leafNode = false;
            firstNode = false;
            for (int i = 0; i < DEPENDENCY_SIZE; ++i)
                children[i] = null;
        }
    }

    TrieNode root = new TrieNode(0);

    public void insert (int id, int dependencyId){

        TrieNode crawler = root;

        if (searchFirstLevel(dependencyId)){
//            System.out.println("first level dependency: "+dependencyId);
            insertFirstLevel(id, dependencyId);
        } else if (searchAnyLevel(id)) {
//            System.out.println("any level dependency: "+dependencyId);
            insertAnyLevel(id, dependencyId);
        } else {
            if (crawler.children[id] == null){
//                System.out.println("fresh tree: "+id + ", dependency: "+dependencyId+" "+crawler.children.length);
                crawler.children[id] = new TrieNode(id);
                crawler.children[id].firstNode = true;
                nodeMap.put(id,crawler.children[id]);
                crawler = crawler.children[id];
                crawler.children[dependencyId] = new TrieNode(dependencyId);
                crawler = crawler.children[dependencyId];
                crawler.leafNode = true;
                nodeMap.put(dependencyId,crawler);
            }
        }
    }

    private boolean searchFirstLevel(int dependencyId){
        TrieNode crawler = root;
        if (crawler.children[dependencyId] != null && nodeMap.containsKey(dependencyId) && nodeMap.get(dependencyId).firstNode == true) return true;
        return false;
    }

    private boolean searchAnyLevel(int id){
        return nodeMap.containsKey(id);
    }

    private void insertFirstLevel(int id, int dependency){
        TrieNode node = nodeMap.get(dependency);
        node.firstNode = false;
        TrieNode newNode = new TrieNode(id);
        newNode.children[id] = node;
        root.children[dependency] = newNode;
        newNode.firstNode = true;
        nodeMap.put(id,newNode);
    }

    private void insertAnyLevel(int id, int dependency){
        TrieNode node = nodeMap.get(id);
        node.children[dependency] = new TrieNode(dependency);
        //TrieNode crawler = node.children[dependency];
        node.leafNode = false;
        node.children[dependency].leafNode = true;
        nodeMap.put(dependency,node.children[dependency]);
    }

    void combineOrdersAndDependencies(int id){
        TrieNode crawler = root;
        if (crawler.children[id] == null && !nodeMap.containsKey(id)){
            crawler.children[id] = new TrieNode(id);
            crawler.children[id].firstNode = true;
            crawler.children[id].leafNode = true;
            nodeMap.put(id,crawler.children[id]);
        }
    }

    public ArrayList<Object> formatTree(){
        ArrayList<Object> temp = formatTrie(root);
        return temp;
    }

    private ArrayList<Object> formatTrie(TrieNode node){
        if (node == null) return null;
        ArrayList<Object> level = new ArrayList<>();
        for(TrieNode child : node.children) {
            if (child != null){
                level.add(child.data);
                ArrayList<Object> checkLevel = formatTrie(child);
                if (checkLevel != null){
                    level.add(checkLevel);
                }
            }
        }
        return level;
    }
}
