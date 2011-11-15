package FinderBots;


/**
 * Die Klasse Node implementiert einen Knoten für die A* Suche
 * Die Berechnung für die Heuristik h ist die Luftlinie in einem Labyrinth
 * g wird vom Algorithmus (in diesem Falle ein Roboter) in die Höhe getrieben
 */

// Problem
public class Node implements Comparable {

    double h = 0; // Heuristik, abgeschaetzter Wert. Hier Luftlinie
    int g = 0; // bisherige Kosten vom Startknoten aus
    double f = 0;
    int index = 0;


    Coordinate coordinate = null; // Koordinate des Knoten


    /**
     * Default Konstruktor für eine Koordinate die in die Liste kommt
     * @param coordinate Klasse, hält eigentlich nur das Tupel zu x und y
     * @param g bisheriger Weg
     * @param index Zeiger auf den Vorgaenger
     */

    public Node(Coordinate coordinate, int g, int index) {

        this.coordinate = coordinate;

        // wir berechnen den Weg h zum Ziel mit dem Pythagoras Satz
        // hierbei liegt das Ziel bei (0,0) - ist das nicht so (die Aufgabe spezifiziert es nie)
        // ist das Problem durch eine weitere lineare Suche zu erweitern
        this.h = Math.sqrt(Math.pow(coordinate.getX(), 2) + Math.pow(coordinate.getY(), 2));
        this.g = g; // muss angegeben werden und erhoeht sich ueber den Weg
        this.f = this.g + this.h; // A* Berechnung zur Gewichtung des Knoten zur Koordinate
        this.index = index;
    }

    /**
     *
     * @return f als f(x) = g(x) + h(x). Ist dieser Welt am kleinsten, ist dies der Nachbarknoten zum Ziel
     * auf kürzestem Wege
     */
    public double getF() {
        return this.f;
    }

    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    public int getIndex() {
        return this.index;
    }


    @Override
    public int compareTo(Object o) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
