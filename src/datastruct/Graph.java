package datastruct;

public class Graph {

    private Vertex vertexList;

    public Vertex getVertexList() {
        return this.vertexList;
    }

    public void setVertexList(Vertex vertexList) {
        this.vertexList = vertexList;
    }

    public Graph() {

    }

    public class Vertex {
        private String name;
        private Vertex next;
        private Edge edgeList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Vertex getNext() {
            return next;
        }

        public void setNext(Vertex newNext) {
            this.next = newNext;
        }

        public Edge getEdgeList() {
            return edgeList;
        }

        public void setEdgeList(Edge edgeList) {
            this.edgeList = edgeList;
        }
    }

    public class Edge {
        private Vertex source;
        private Vertex destination;
        private double resistance;
        private Edge next;

        public Vertex getDestination() {
            return destination;
        }

        public void setDestination(Vertex des) {
            this.destination = des;
        }

        public double getResistance() {
            return resistance;
        }

        public void setResistance(double r) {
            this.resistance = r;
        }

        public Edge getNext() {
            return next;
        }

        public void setNext(Edge next) {
            this.next = next;
        }
    }

    public boolean checkName(String name) {
        Vertex tempV = vertexList;
        while (tempV != null) {
            if (tempV.name.equals(name)) {
                return true;
            }
            tempV = tempV.next;
        }
        return false;
    }

    public void addVertex(String name) {
        Vertex newVertex = new Vertex();
        newVertex.name = name;
        if (checkName(name) == true) {
            return;
        } else if (vertexList == null) {
            vertexList = newVertex;
            return;
        }
        Vertex temp = vertexList;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = newVertex;
    }

    public Vertex findVertex(String source) {
        Vertex temp = vertexList;
        while (temp != null) {
            if (source.equals(temp.name)) {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }

    public void addEdge(String source, String destination, double data) {
        Vertex scr = findVertex(source);
        Vertex des = findVertex(destination);
        Edge newEdge = new Edge();
        newEdge.resistance = data;
        newEdge.source = scr;
        newEdge.destination = des;
        newEdge.next = scr.edgeList;
        scr.edgeList = newEdge;

        Edge returnEdge = new Edge();
        returnEdge.source = des;
        returnEdge.destination = scr;
        returnEdge.resistance = data;
        returnEdge.next = des.edgeList;
        des.edgeList = returnEdge;
    }

    public int getDegree(String source) {
        Vertex countVertex = findVertex(source);
        int count = 0;
        if (countVertex == null) {
            return -1;
        }
        Edge temp = countVertex.edgeList;
        while (temp != null) {
            temp = temp.next;
            count++;
        }
        return count;
    }

    public void display() {
        Vertex tempV = vertexList;
        System.out.println("--- Electrical Circuit Structure ---");
        if (tempV == null) {
            System.out.println("Circuit is empty (No Nodes)");
            return;
        }
        while (tempV != null) {
            System.out.print("Node [" + tempV.name + "] connects to: ");
            Edge tempE = tempV.edgeList;
            if (tempE == null) {
                System.out.print("No connections");
            } else {
                while (tempE != null) {
                    System.out.printf("-> (to + %s | R = %.2f Ohm)", tempE.destination.name, tempE.resistance);
                    tempE = tempE.next;
                }
            }
            System.out.println();
            tempV = tempV.next;
        }
        System.out.println("------------------------------");
    }

    public double seriesEquation(double num1, double num2) {
        double sum = num1 + num2;
        return sum;
    }

    public double parallelEquation(double num1, double num2) {
        double sum = (num1 * num2) / (num1 + num2);
        return sum;
    }

    public void circuitReductionParallel() {
        Vertex Vtemp = vertexList;
        while (Vtemp != null) {
            Edge Etemp = Vtemp.edgeList;
            while (Etemp != null) {
                Edge prev = Etemp;
                Edge nametemp = Etemp.next;
                while (nametemp != null) {
                    if (Etemp.destination.equals(nametemp.destination)) {
                        Etemp.resistance = parallelEquation(Etemp.resistance, nametemp.resistance);
                        prev.next = nametemp.next;
                        nametemp = prev.next;
                    } else {
                        prev = nametemp;
                        nametemp = nametemp.next;
                    }
                }
                Etemp = Etemp.next;
            }
            Vtemp = Vtemp.next;
        }
    }

    public void circuitReductionSeries() {
        Vertex Vtemp = vertexList;
        Vertex prevV = null;
        while (Vtemp != null) {
            if (getDegree(Vtemp.name) == 2) {
                Edge e1 = Vtemp.edgeList;
                Edge e2 = e1.next;
                Vertex n1 = e1.destination;
                Vertex n2 = e2.destination;
                double datatemp = seriesEquation(e1.resistance, e2.resistance);
                addEdge(n1.name, n2.name, datatemp);
                deleteEdgeFrom(n1, Vtemp);
                deleteEdgeFrom(n2, Vtemp);
                if (prevV == null) {
                    vertexList = Vtemp.next;
                } else {
                    prevV.next = Vtemp.next;

                }
                Vtemp = vertexList;
                prevV = null;
                continue;
            }
            prevV = Vtemp;
            Vtemp = Vtemp.next;
        }
    }

    public void deleteEdgeFrom(Vertex from, Vertex target) {
        Edge Etemp = from.edgeList;
        Edge prev = null;
        while (Etemp != null) {
            if (Etemp.destination == target) {
                if (prev == null) {
                    from.edgeList = Etemp.next;
                } else {
                    prev.next = Etemp.next;
                }
                return;
            }
            prev = Etemp;
            Etemp = Etemp.next;
        }
    }

    public void totalResistance() {
        if (vertexList == null) {
            System.out.println("Not found Vertex in Graph.");
            return;
        }
        Vertex tempV = vertexList;
        Edge tempE = tempV.edgeList;
        if (tempE == null) {
            System.out.println("No resistance data found.");
            return;
        }
        System.out.println("------------------------------");
        System.out.printf("Total of resistanece is %.2f Ohm.\n", tempE.resistance);
        System.out.println("------------------------------");
    }
}
