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

    public boolean circuitReductionParallel(Graph g) {
        boolean changed = false;
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
                        changed = true;
                    } else {
                        prev = nametemp;
                        nametemp = nametemp.getNext();
                    }
                }
                Etemp = Etemp.getNext();
            }
            Vtemp = Vtemp.getNext();
        }
        return changed;
    }

    public boolean circuitReductionSeries(Graph g) {
        boolean changed = false;
        Vertex Vtemp = g.getVertexList();
        Vertex prevV = null;
        while (Vtemp != null) {
            if (canReduceSeries(g, Vtemp)) {
                Edge e1 = Vtemp.getEdgeList();
                Edge e2 = e1.getNext();
                Vertex n1 = e1.getDestination();
                Vertex n2 = e2.getDestination();
                double datatemp = seriesEquation(e1.getResistance(), e2.getResistance());
                g.addEdge(n1.getName(), n2.getName(), datatemp);
                deleteEdgeFrom(n1, Vtemp);
                deleteEdgeFrom(n2, Vtemp);
                if (prevV == null) {
                    g.setVertexList(Vtemp.getNext());
                } else {
                    prevV.setNext(Vtemp.getNext());

                }
                Vtemp = g.getVertexList();
                prevV = null;
                changed = true;
                continue;
            }
            prevV = Vtemp;
            Vtemp = Vtemp.getNext();
        }
        return changed;
    }

    private boolean canReduceSeries(Graph g, Vertex vertex) {
        if (g.getDegree(vertex.getName()) != 2) {
            return false;
        }

        Edge e1 = vertex.getEdgeList();
        Edge e2 = e1.getNext();
        return e1.getDestination() != e2.getDestination();
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

    public void deleteEdgeFrom(Vertex from, Vertex target) {
        Edge Etemp = from.getEdgeList();
        Edge prev = null;
        while (Etemp != null) {
            if (Etemp.getDestination() == target) {
                if (prev == null) {
                    from.setEdgeList(Etemp.getNext());
                    Etemp = from.getEdgeList();
                    continue;
                } else {
                    prev.setNext(Etemp.getNext());
                    Etemp = prev.getNext();
                    continue;
                }
            }
            prev = Etemp;
            Etemp = Etemp.getNext();
        }
    }
}
