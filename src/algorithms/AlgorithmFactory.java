package algorithms;

public class AlgorithmFactory {
    public final static String BFS = "Przeszukiwanie wszerz";
    public final static String DFS = "Przeszukiwanie w głąb";
    public final static String DIJKSTRA = "Algorytm Dijkstry";
    public final static String ASTAR = "Algorytm A*";
    public final static String BELLMAN_FORD = "Algorytm Bellmana-Forda";
    public final static String FLOYD_WARSHALL = "Algorytm Floyda-Warshalla";
    protected static String[] algorithms={BFS,DFS,DIJKSTRA,ASTAR,BELLMAN_FORD,FLOYD_WARSHALL};

    public static AbstractAlgorithm createAlgorithm(String name) {
        AbstractAlgorithm algorithm = null;
        switch (name) {
            case BFS:
                //algorithm = new BFSMap();
                //algorithm = new BFSArray();
                algorithm = new BFS();
                break;
            case DFS:
                algorithm = new DFS();
                break;
            case DIJKSTRA:
                algorithm = new Dijkstra();
                break;
            case ASTAR:
                algorithm = new AStar();
                break;
            case BELLMAN_FORD:
                algorithm = new BellmanFord();
                break;
            case FLOYD_WARSHALL:
                algorithm = new FloydWarshall();
                break;
        }
        return algorithm;
    }
    
    public static String[] getAlgorithmsArray(){
        return algorithms;
    }
}
