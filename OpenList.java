package FinderBots;


import java.util.PriorityQueue;

/**
 *  Diese Klasse implementiert eine OpenList fuer eine A* Suche
 */

public class OpenList {

    PriorityQueue<Node> KnownNodes = null;

    /**
     * Default Konstruktor für die OpenList bei A* Suche
     * @param StartNode Startknoten fuer die OpenList
     */

    public OpenList(Node StartNode) {

        this.KnownNodes = new PriorityQueue<Node>();
        this.KnownNodes.add(StartNode); // StartNode ist bekannt und kommt als erstes in die OpenList

    }

    public void addNode(Node node) {
        this.KnownNodes.add(node);
    }

    public void getPred(Node node) {

        int currentIndex = node.getIndex();
        int wantedIndex = currentIndex - 1;


        Node pred = this.KnownNodes.peek();

    }



}
