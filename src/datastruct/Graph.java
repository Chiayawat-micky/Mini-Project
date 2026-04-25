package datastruct;

public class Graph {

    private Vertex vertexList;

    public Graph() {

    }

    class Vertex {

        private String name;
        private Vertex next;
        private Edge edgeList;
    }

    class Edge {

        private Vertex source;
        private Vertex destination;
        private double resistance;
        private Edge next;
    }

    public void addVertex(String name) {
        Vertex newVertex = new Vertex();
        newVertex.name = name;
        if (vertexList == null) {
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
        System.out.println("--- โครงสร้างวงจรไฟฟ้า ---");
        if (tempV == null) {
            System.out.println("วงจรว่างเปล่า (ยังไม่มี Node)");
            return;
        }
        while (tempV != null) {
            System.out.print("จุด [" + tempV.name + "] เชื่อมต่อกับ: ");
            Edge tempE = tempV.edgeList;
            if (tempE == null) {
                System.out.print("ไม่มีสายไฟเชื่อมต่อ");
            } else {
                while (tempE != null) {
                    System.out.print(" -> (ไป " + tempE.destination.name + " | R = " + tempE.resistance + " Ohm)");
                    tempE = tempE.next;
                }
            }
            System.out.println();
            tempV = tempV.next;
        }
        System.out.println("-----------------------");
    }

    public double seriesEquation(double num1,double num2){
        double sum = num1 + num2;
        return sum;
    }

    public double parallelEquation(double num1,double num2){
        double sum = (num1 * num2) / (num1 + num2);
        return sum;
    }

    public void circuitReductionParallel(){
        Vertex Vtemp = vertexList;
        while(Vtemp != null){
            Edge Etemp = Vtemp.edgeList;
            while(Etemp != null){
                Edge prev = Etemp;
                Edge nametemp = Etemp.next;
                while(nametemp != null){
                    if(Etemp.destination.equals(nametemp.destination)){
                        Etemp.resistance = parallelEquation(Etemp.resistance, nametemp.resistance);
                        prev.next = nametemp.next;
                        nametemp = prev.next;
                    }else{
                        prev = nametemp;
                        nametemp = nametemp.next;
                    }
                }
                Etemp = Etemp.next;
            }
            Vtemp = Vtemp.next;
        }
    }
}
