package src;

import src.backend.AllocationLogic;

public class TestApp {
    public static void main(String[] args) {
        System.out.println("=== Faculty Allocation Started ===");

        AllocationLogic allocationLogic = new AllocationLogic();

        // Allocate one semester at a time
        allocationLogic.generateAllocation("3");
        allocationLogic.generateAllocation("5");
        allocationLogic.generateAllocation("7");

        System.out.println("=== Allocation Finished ===");
    }
}
