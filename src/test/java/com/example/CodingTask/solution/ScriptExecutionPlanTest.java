package com.example.CodingTask.solution;

import com.example.CodingTask.model.VulnerabilityScript;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScriptExecutionPlanTest {

    @Test
    public void testExecutionPlanNoDependencies() {
        VulnerabilityScript script1 = new VulnerabilityScript(1, Collections.emptyList());
        VulnerabilityScript script2 = new VulnerabilityScript(2, Collections.emptyList());

        List<VulnerabilityScript> scripts = Arrays.asList(script1, script2);
        ScriptExecutionPlan plan = new ScriptExecutionPlan();

        List<Integer> expected = Arrays.asList(1, 2);
        List<Integer> actual = plan.getExecutionPlan(scripts);

        assertEquals(expected, actual);
    }

    @Test
    public void testExecutionPlanWithDependencies() {
        VulnerabilityScript script1 = new VulnerabilityScript(1, Collections.emptyList());
        VulnerabilityScript script2 = new VulnerabilityScript(2, Arrays.asList(1));
        VulnerabilityScript script3 = new VulnerabilityScript(3, Arrays.asList(1, 2));

        List<VulnerabilityScript> scripts = Arrays.asList(script1, script2, script3);
        ScriptExecutionPlan plan = new ScriptExecutionPlan();

        List<Integer> actual = plan.getExecutionPlan(scripts);
        assertEquals(3, actual.size());
        assertEquals(true, actual.containsAll(Arrays.asList(1, 2, 3)));
    }

    @Test
    public void testCyclicDependency() {
        VulnerabilityScript script1 = new VulnerabilityScript(1, Arrays.asList(2));
        VulnerabilityScript script2 = new VulnerabilityScript(2, Arrays.asList(1));

        List<VulnerabilityScript> scripts = Arrays.asList(script1, script2);
        ScriptExecutionPlan plan = new ScriptExecutionPlan();

        assertThrows(RuntimeException.class, () -> plan.getExecutionPlan(scripts));
    }
}
