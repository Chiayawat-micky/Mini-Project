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
}
