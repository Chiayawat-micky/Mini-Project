package app;

import datastruct.Graph;
import java.util.Scanner;

public class miniProject {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph myGraph = new Graph();
        System.out.println("Press the name NODE and num of resistance(A B 50) (Press 'END' to end the system ): ");
        while (true) {
            String source = sc.next();
            if (source.equals("END")) {
                break;
            }
            String destination = sc.next();
            double data = sc.nextDouble();
            myGraph.addVertex(source);
            myGraph.addVertex(destination);
            myGraph.addEdge(source, destination, data);

        }
        myGraph.display();
        myGraph.circuitReductionParallel();
        myGraph.circuitReductionSeries();
        // System.out.println("After reduction circuit");
        // myGraph.display();
        myGraph.totalResistance();
    }
}
