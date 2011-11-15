package FinderBots;

// special Import for excerise
import robocode.BattlefieldMap;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * @author Marius
 */
public final class AStarMazeSolver {
    // Vectors used in expand(Node)
    private final int[] relativeMovementsX = {-1, 0, 1, 0};
    private final int[] relativeMovementsY = {0, 1, 0, -1};

    private final BattlefieldMap map;
    private final int mapWidth;
    private final int mapHeight;
    private boolean[][] closedList;
    private boolean[][] foundList;
    private PriorityQueue<Node> openList;
    private Node startNode = null;

    public static Vector2D[] getWaypoints(BattlefieldMap map) {
        AStarMazeSolver solver = new AStarMazeSolver(map);
        Node currentNode = solver.startNode;
        ArrayList<Vector2D> waypoints = new ArrayList<Vector2D>();
        while (currentNode != null) {
            Vector2D currentVector = new Vector2D(currentNode.realPosX, currentNode.realPosY);

            if (waypoints.size() >= 2) {
                // check if last waypoint can be omitted
                Vector2D last = waypoints.get(waypoints.size() - 1);
                Vector2D beforeLast = waypoints.get(waypoints.size() - 2);
                Vector2D lastHeading = last.copy().subtract(beforeLast);
                Vector2D currentHeading = currentVector.copy().subtract(last);
                if (currentHeading.getPhiRad() == lastHeading.getPhiRad()) {
                    waypoints.remove(waypoints.size() - 1);
                }
            }
            waypoints.add(currentVector);
            currentNode = currentNode.getPrecursor();
        }
        waypoints.remove(0);
        return waypoints.toArray(new Vector2D[waypoints.size()]);
    }

    private AStarMazeSolver(BattlefieldMap map) {
        this.map = map;
        mapWidth = map.getWidth() / 100;
        mapHeight = map.getHeight() / 100;
        closedList = new boolean[mapWidth][mapHeight]; // automatically initialized false
        foundList = new boolean[mapWidth][mapHeight];
        openList = new PriorityQueue<Node>();
        solve();
    }

    private void solve() {
        Node endZoneNode = new Node(0, 0, 0, null);
        openList.add(endZoneNode);
        while (startNode == null) {
            Node current = openList.remove();
            expand(current);
        }
    }

    private void expand(Node current) {
        // close current node
        closedList[current.posX][current.posY] = true;
        for (int i = 0; i < 4; i++) {
            // get neighbour coordinates
            int neighbourX = current.posX + relativeMovementsX[i];
            int neighbourY = current.posY + relativeMovementsY[i];
            // if neighbour coordinates inside grid
            if (neighbourX >= 0 && neighbourX < mapWidth && neighbourY >= 0 && neighbourY < mapHeight) {
                // if neighbour not yet expanded/closed
                if (!closedList[neighbourX][neighbourY]) {
                    // check if wall between nodes
                    int wallPosX = current.realPosX + (relativeMovementsX[i] * 50);
                    int wallPosY = current.realPosY + (relativeMovementsY[i] * 50);
                    boolean reachable = map.isPassable(wallPosX, wallPosY);
                    // add node to open list if reachable and unknown
                    if (reachable && !foundList[neighbourX][neighbourY]) {
                        foundList[neighbourX][neighbourY] = true;
                        int neighbourH = (mapWidth - neighbourX - 1) + (mapHeight - neighbourY - 1);
                        Node neighbour = new Node(neighbourX, neighbourY, neighbourH, current);
                        openList.add(neighbour);
                        if (neighbourX == mapWidth - 1 && neighbourY == mapHeight - 1) {
                            startNode = neighbour; // start zone found
                        }
                    }
                }
            }
        }
    }

    private class Node implements Comparable<Node> {
        public final int posX;
        public final int posY;
        public final int realPosX;
        public final int realPosY;
        public final int h;
        private Node precursor;
        private int g;

        public Node(int posX, int posY, int h, Node precursor) {
            this.posX = posX;
            this.posY = posY;
            this.h = h;
            this.precursor = precursor;
            this.g = precursor == null ? 0 : precursor.g + 1;
            realPosX = (posX * 100) + 50;
            realPosY = (posY * 100) + 50;
        }

        public Node getPrecursor() {
            return precursor;
        }

        public void setPrecursor(Node precursor) {
            this.precursor = precursor;
            g = precursor.g + 1;
        }

        public int getG() {
            return g;
        }

        public int getF() {
            return g + h;
        }

        @Override
        public int compareTo(Node o) {
            return getF() - o.getF();
        }
    }
}
