package compiler;

import java.util.*;

public class DirectedGraph {

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
            if (hasCycleUtil(vertex, visited, recStack, new ArrayList<>())) {
                return true;
            }
        }
        return false;
    }

    // Function to return all nodes in a cycle
    public List<String> findAllCycleNodes() {
        for (String vertex : adjacencyList.keySet()) {
            List<String> cycleNodes = new ArrayList<>();
            if (hasCycleUtil(vertex, new HashMap<>(), new HashMap<>(), cycleNodes)) {
                return cycleNodes;
            }
        }
        return Collections.emptyList(); // No cycle found
    }

    private boolean hasCycleUtil(String vertex, Map<String, Boolean> visited, Map<String, Boolean> recStack, List<String> cycleNodes) {
        if (recStack.getOrDefault(vertex, false)) {
            cycleNodes.add(vertex); // Add the cycle-forming node
            return true; // Cycle found
        }

        if (visited.getOrDefault(vertex, false)) {
            return false; // Already visited, no cycle
        }

        visited.put(vertex, true);
        recStack.put(vertex, true);
        cycleNodes.add(vertex); // Temporarily add for potential cycle

        for (String neighbor : adjacencyList.getOrDefault(vertex, Collections.emptyList())) {
            if (hasCycleUtil(neighbor, visited, recStack, cycleNodes)) {
                return true;
            }
        }

        cycleNodes.remove(vertex); // Backtrack
        recStack.put(vertex, false);
        return false;
    }
}
