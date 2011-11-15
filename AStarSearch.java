package FinderBots;


import robocode.BattlefieldMap;

import java.util.ArrayList;

public class AStarSearch {

    final int d = 4; // Distanz zwischen Zwei Knoten (Auflösung der Suche)
    OpenList Ol = null; // OpenList zur Speicherung bekannter Knoten
    ClosedList Cl = null; // zur Speicherung des kürzesten Weges nach der Berechnung
    Node TempNode = null; // zum Zwischenspeichern eines Knoten
    int g = 0; // bisherige Weg-Kosten, inital sicherlich 0, wächst mit der Suche
    ArrayList<Node> neighbourCross = null;  // hält die Nachbarn, biszu 4 - Buffer
    BattlefieldMap bfm = null;
    int index = 0; // Zeigerverwaltung für Nodes

    public AStarSearch(Coordinate startPoint, BattlefieldMap bfm) {

        this.bfm = bfm;


        this.TempNode = new Node(startPoint, g, this.index); // Erzeugung des StartKnoten aus der gegeben Koordinate
        this.Ol = new OpenList(this.TempNode); // Erzeugung der OpenList mit dem Knoten

    }

    private void search() {

        // Abbruchbedingung: in der ArrayList ist ein Knoten mit den Koordinaten der Endzone


    }


    /**
     * diese Methode berechnet von einem Knoten die 4 Nachbarn (rechts, links, unten, oben)
     *
     * @param centerNode der Mitt-Knoten
     */
    private void resolveNeighbours(Node centerNode) {

        // Liste initalisieren
        this.neighbourCross = new ArrayList<Node>();
        this.index++;

        this.g += d; // die Kosten sind die überwundene Distanz

        // mit den Koordinaten handtieren in 4 Richtungen innerhalb der Map
        Coordinate tempCoord = centerNode.getCoordinate();

        if (bfm.isPassable(tempCoord.getX(), tempCoord.getY() + this.d)) {
            Coordinate oben = new Coordinate(tempCoord.getX(), tempCoord.getY() + this.d); // oberer Nachbar
            this.TempNode = new Node(oben, g, this.index); // g bekannt, aus Koordinate bilden wir den Knoten
            this.Ol.addNode(this.TempNode); // Weg bekannt, in die OpenList
            this.neighbourCross.add(this.TempNode);
        }

        if (bfm.isPassable(tempCoord.getX(), tempCoord.getY() - this.d)) {
            Coordinate unten = new Coordinate(tempCoord.getX(), tempCoord.getY() - this.d); // unterer Nachbar
            this.TempNode = new Node(unten, g, this.index);
            this.Ol.addNode(this.TempNode);
            this.neighbourCross.add(this.TempNode);
        }

        if (bfm.isPassable(tempCoord.getX() - this.d, tempCoord.getY())) {
            Coordinate links = new Coordinate(tempCoord.getX() - this.d, tempCoord.getY()); // linker Nachbar
            this.TempNode = new Node(links, g, this.index);
            this.Ol.addNode(this.TempNode);
            this.neighbourCross.add(this.TempNode);
        }

        if (bfm.isPassable(tempCoord.getX() + this.d, tempCoord.getY())) {
            Coordinate rechts = new Coordinate(tempCoord.getX() + this.d, tempCoord.getY()); // rechter Nachbar
            this.TempNode = new Node(rechts, g, this.index);
            this.Ol.addNode(this.TempNode);
            this.neighbourCross.add(this.TempNode);
        }


    }


}
