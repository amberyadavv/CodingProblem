package com.example.CodingTask.solution;

import com.example.CodingTask.model.VulnerabilityScript;

import java.util.*;

public class ScriptExecutionPlan {

    public static List<Integer> getExecutionPlan(List<VulnerabilityScript> scripts) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();

        for (VulnerabilityScript script : scripts) {
            graph.putIfAbsent(script.getScriptId(), new ArrayList<>());
            inDegree.putIfAbsent(script.getScriptId(), 0);
            for (Integer dep : script.getDependencies()) {
                graph.computeIfAbsent(dep, k -> new ArrayList<>()).add(script.getScriptId());
                inDegree.put(script.getScriptId(), inDegree.getOrDefault(script.getScriptId(), 0) + 1);
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        List<Integer> order = new ArrayList<>();
        while (!queue.isEmpty()) {
            int current = queue.poll();
            order.add(current);
            for (Integer neighbor : graph.getOrDefault(current, Collections.emptyList())) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        if (order.size() != scripts.size()) {
            throw new IllegalArgumentException("There exists a cycle in the graph");
        }

        return order;
    }

    public static void main(String[] args) {
        List<VulnerabilityScript> scripts = Arrays.asList(
                new VulnerabilityScript(1, Arrays.asList()),
                new VulnerabilityScript(2, Arrays.asList(1)),
                new VulnerabilityScript(3, Arrays.asList(1, 2)),
                new VulnerabilityScript(4, Arrays.asList(2)),
                new VulnerabilityScript(5, Arrays.asList(3, 4))
        );

        System.out.println(getExecutionPlan(scripts)); // Output: [1, 2, 4, 3, 5]
    }
}