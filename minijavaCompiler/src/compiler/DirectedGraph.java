package compiler;

import java.util.*;

public class DirectedGraph {

//    public static void main(String[] args) {
//        DirectedGraph graph = new DirectedGraph();
//        graph.addEdge("0", "1");
//        graph.addEdge("1", "2");
//        graph.addEdge("2", "0"); // Creates a cycle
//
//        if (graph.hasCycle()) {
//            System.out.println("Graph has a cycle");
//        } else {
//            System.out.println("Graph is acyclic");
//        }
//
//    }

    Map<String, List<String>> adjacencyList;

    public DirectedGraph() {
        adjacencyList = new HashMap<>();
    }

    public void addEdge(String source, String destination) {
        adjacencyList.putIfAbsent(source, new ArrayList<>());
        adjacencyList.get(source).add(destination);
    }

    public boolean hasCycle() {
        Map<String, Boolean> visited = new HashMap<>();
        Map<String, Boolean> recStack = new HashMap<>();

        for (String vertex : adjacencyList.keySet()) {
            if (hasCycleUtil(vertex, visited, recStack)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasCycleUtil(String vertex, Map<String, Boolean> visited, Map<String, Boolean> recStack) {
        if (recStack.getOrDefault(vertex, false)) {
            return true; // Cycle found
        }

        if (visited.getOrDefault(vertex, false)) {
            return false; // Already visited, no cycle
        }

        visited.put(vertex, true);
        recStack.put(vertex, true);

        for (String neighbor : adjacencyList.getOrDefault(vertex, Collections.emptyList())) {
            if (hasCycleUtil(neighbor, visited, recStack)) {
                return true;
            }
        }

        recStack.put(vertex, false); // Backtrack
        return false;
    }


    public List<String> findCycle() {
        Map<String, Boolean> visited = new HashMap<>();
        Map<String, Boolean> recStack = new HashMap<>();
        Map<String, String> parentMap = new HashMap<>(); // Track parents for cycle reconstruction

        for (String vertex : adjacencyList.keySet()) {
            List<String> cycle = findCycleUtil(vertex, visited, recStack, parentMap);
            if (cycle != null) {
                return cycle;
            }
        }
        return null;
    }

    private List<String> findCycleUtil(String vertex, Map<String, Boolean> visited,
                                       Map<String, Boolean> recStack, Map<String, String> parentMap) {
        if (recStack.getOrDefault(vertex, false)) {
            // Cycle found, reconstruct it
            List<String> cycle = new ArrayList<>();
            String node = vertex;
            cycle.add(node);
            while (parentMap.get(node) != null && !node.equals(parentMap.get(node))) {
                node = parentMap.get(node);
                cycle.add(node);
            }
            cycle.add(vertex); // Close the cycle
            Collections.reverse(cycle); // Return in correct order
            return cycle;
        }

        if (visited.getOrDefault(vertex, false)) {
            return null; // Already visited, no cycle
        }

        visited.put(vertex, true);
        recStack.put(vertex, true);

        for (String neighbor : adjacencyList.getOrDefault(vertex, Collections.emptyList())) {
            parentMap.put(neighbor, vertex); // Track parent for cycle reconstruction
            List<String> cycle = findCycleUtil(neighbor, visited, recStack, parentMap);
            if (cycle != null) {
                return cycle;
            }
        }

        recStack.put(vertex, false); // Backtrack
        return null;
    }
}
