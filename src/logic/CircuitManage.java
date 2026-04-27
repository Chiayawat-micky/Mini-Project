package logic;

import datastruct.Graph;
import datastruct.Graph.Vertex;
import datastruct.Graph.Edge;

public class CircuitManage {
    public double seriesEquation(double num1, double num2) {
        double sum = num1 + num2;
        return sum;
    }

    public double parallelEquation(double num1, double num2) {
        double sum = (num1 * num2) / (num1 + num2);
        return sum;
    }

    public void circuitReductionParallel(Graph g) {
        Vertex Vtemp = g.getVertexList();
        while (Vtemp != null) {
            Edge Etemp = Vtemp.getEdgeList();
            while (Etemp != null) {
                Edge prev = Etemp;
                Edge nametemp = Etemp.getNext();
                while (nametemp != null) {
                    if (Etemp.getDestination().equals(nametemp.getDestination())) {
                        Etemp.setResistance(parallelEquation(Etemp.getResistance(), nametemp.getResistance()));
                        prev.setNext(nametemp.getNext());
                        nametemp = prev.getNext();
                    } else {
                        prev = nametemp;
                        nametemp = nametemp.getNext();
                    }
                }
                Etemp = Etemp.getNext();
            }
            Vtemp = Vtemp.getNext();
        }
    }

    public void circuitReductionSeries(Graph g) {
        Vertex Vtemp = g.getVertexList();
        Vertex prevV = null;
        while (Vtemp != null) {
            if (g.getDegree(Vtemp.getName()) == 2) {
                Edge e1 = Vtemp.getEdgeList();
                Edge e2 = e1.getNext();
                Vertex n1 = e1.getDestination();
                Vertex n2 = e2.getDestination();
                double datatemp = seriesEquation(e1.getResistance(), e2.getResistance());
                g.addEdge(n1.getName(), n2.getName(), datatemp);
                g.deleteEdgeFrom(n1, Vtemp);
                g.deleteEdgeFrom(n2, Vtemp);
                if (prevV == null) {
                    g.setVertexList(Vtemp.getNext());
                } else {
                    prevV.setNext(Vtemp.getNext());

                }
                Vtemp = g.getVertexList();
                prevV = null;
                continue;
            }
            prevV = Vtemp;
            Vtemp = Vtemp.getNext();
        }
    }

    public void totalResistance(Graph g) {
        if (g.getVertexList() == null) {
            System.out.println("Not found Vertex in Graph.");
            return;
        }
        Vertex tempV = g.getVertexList();
        Edge tempE = tempV.getEdgeList();
        if (tempE == null) {
            System.out.println("No resistance data found.");
            return;
        }
        System.out.println("------------------------------");
        System.out.printf("Total of resistanece is %.2f Ohm.\n", tempE.getResistance());
        System.out.println("------------------------------");
    }
}
