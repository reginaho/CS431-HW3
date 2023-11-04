import java.util.Scanner;

public class BankersAlgorithm {
//inspired by https://www.geeksforgeeks.org/bankers-algorithm-in-operating-system-2/#
    private int need[][], allocate[][], max[][], available[];
    private int numOfProcesses, numOfResources;
    private Scanner sc = new Scanner(System.in);

    private void initializeData() {
        System.out.print("Enter number of processes and resources: ");
        numOfProcesses = sc.nextInt();  // number of processes
        numOfResources = sc.nextInt();  // number of resources

        // Initialize arrays based on the number of processes and resources
        need = new int[numOfProcesses][numOfResources];
        max = new int[numOfProcesses][numOfResources];
        allocate = new int[numOfProcesses][numOfResources];
        available = new int[numOfResources];

        // Populate the allocation, max, and available matrices
        System.out.println("Enter allocation matrix(with spaces in between and enter for a new row:");
        for (int i = 0; i < numOfProcesses; i++)
            for (int j = 0; j < numOfResources; j++)
                allocate[i][j] = sc.nextInt();

        System.out.println("Enter max matrix(with spaces in between and enter for a new row):");
        for (int i = 0; i < numOfProcesses; i++)
            for (int j = 0; j < numOfResources; j++)
                max[i][j] = sc.nextInt();

        System.out.println("Enter available matrix(with spaces in between):");
        for (int i = 0; i < numOfResources; i++)
            available[i] = sc.nextInt();
    }

    private void calculateNeed() {
        for (int i = 0; i < numOfProcesses; i++)
            for (int j = 0; j < numOfResources; j++)
                need[i][j] = max[i][j] - allocate[i][j];
    }

    private boolean canAllocate(int process) {
        for (int resource = 0; resource < numOfResources; resource++)
            if (available[resource] < need[process][resource])
                return false;
        return true;
    }

    public void isSafe() {
        initializeData();
        calculateNeed();

        boolean[] finished = new boolean[numOfProcesses];
        int[] safeSequence = new int[numOfProcesses];
        int index = 0;

        while (index < numOfProcesses) {
            boolean allocated = false;
            for (int process = 0; process < numOfProcesses; process++) {
                if (!finished[process] && canAllocate(process)) {
                    for (int resource = 0; resource < numOfResources; resource++)
                        available[resource] += allocate[process][resource];
                    safeSequence[index++] = process;  // Add this process to the safe sequence
                    finished[process] = true;
                    allocated = true;
                }
            }
            if (!allocated) {
                break;  // No allocation could be done, unsafe state
            }
        }

        if (index == numOfProcesses) {
            System.out.println("System is in a safe state.");
            System.out.print("Safe sequence is: ");
            for (int i : safeSequence) System.out.print("P" + i + " ");
        } else {
            System.out.println("System is not in a safe state.");
        }
    }

    public static void main(String[] args) {
        new BankersAlgorithm().isSafe();
    }
}

