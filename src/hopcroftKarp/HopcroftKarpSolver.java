package hopcroftKarp;

import java.util.*;

/***
 * This class is an implementation of HopcroftKarp algorithm for solving
 * matching problems
 * 
 * @author benja
 *
 * @param <T>
 *            - The class which implements the graphNode interface
 */
public class HopcroftKarpSolver<T extends HKGraphNode<T>> {

	private final int INF = Integer.MAX_VALUE; // Shorthand for readability
	private List<T> graphNodes; // List of all the nodes in the graph. Must be
								// bipartite for Hopscroft Karp to work
	private HashMap<T, T> vMatches; // matches (also called pairs) to the nodes
									// on the U side of the bipartite graph
	private HashMap<T, T> uMatches; // matches (also called pairs) to the nodes
									// on the V side of the bipartite graph
	private HashMap<T, Integer> dist; // distance to any node from the "start"
										// on the U side of the matching

	public HopcroftKarpSolver() {
	}

	/***
	 * Create symmetrical matching graph from single list of nodes. Assuming all
	 * nodes are interconnected well enough The goal is to use Hopscroft-Karp to
	 * find a valid pairing of a single list of nodes Each node is treated as
	 * two, one on the U side and one on the V side. One U is out, the V is in.
	 * This will always result in a bipartite graph We will find a maximal
	 * matching where each node can have out and one in.
	 * 
	 * @param graphNodes
	 *            - List of nodes in the graph,
	 */
	public void createMatchingGraph(List<T> graphNodes) {
		this.graphNodes = new ArrayList<T>(graphNodes); // This function is
														// randomized and will
														// shuffle this list
														// frequently. This is
														// to maintain the
														// original ordering of
														// objects in the list
														// passed in
		Collections.shuffle(this.graphNodes);

		// When a new graph is created we want to clear out what is already in
		// the matches and distance
		// Also instantiates if not used yet
		this.clearMatchingsAndDistance();
	}

	/***
	 * Finds a maximal matching and returns the size of said matching. The
	 * values of the matching are stored in this class and can be accessed using
	 * getMatching This is randomized and subsequent runs will have different
	 * pairings, although the maximal matching size will remain the same Note:
	 * The ordering of the edges of these nodes might change after execution
	 * 
	 * @return int - number of nodes in the matching
	 */
	public int solveHopcroftKarp() {
		if (graphNodes == null) {
			throw new IllegalStateException("No graph created on which to run Hopcroft Karp");
		}
		this.clearMatchingsAndDistance();

		int matching = 0;
		// reset current matchings
		for (T node : graphNodes) {
			vMatches.put(node, null);
			uMatches.put(node, null);
		}

		while (bfs()) {
			for (T node : graphNodes) {
				if (uMatches.get(node) == null) {
					if (dfs(node)) {
						matching++;
					}
				}
			}
		}

		return matching;
	}

	/***
	 * Retrieves current matching, will throw an excpetion if no such matching
	 * exists Run solver before calling
	 * 
	 * @return
	 */
	public HashMap<T, T> getMatching() {
		if (uMatches.isEmpty()) {
			throw new IllegalStateException("No matches determined yet. Run solver before retrieving matching");
		}
		return uMatches;
	}

	/***
	 * Hopscroft-Karp specific modification of depth first search
	 * 
	 * @param u
	 *            - Node which we are starting the search from
	 * @return true if there is an augmenting path, false otherwise
	 */
	private boolean dfs(T u) {
		if (u != null) {
			List<T> uEdges = getRandomizedEdges(u);
			for (T v : uEdges) {
				T vPair = vMatches.get(v); // a node in U, matched with v in V
				if (dist.get(vPair) == dist.get(u) + 1) {
					if (dfs(vPair) == true) {
						uMatches.put(u, v);
						vMatches.put(v, u);
						return true;
					}
				}
			}
			dist.put(u, INF);
			return false;
		}
		return true;
	}

	/***
	 * Hopscroft-Karp specific modification of breadth first search
	 * 
	 * @return true when graph fully explored
	 */
	private boolean bfs() {
		// Setup
		Queue<T> q = new LinkedList<T>();
		for (T node : graphNodes) {
			// Add unmatched u nodes to the queue
			if (uMatches.get(node) == null) {
				dist.put(node, 0);
				q.add(node);
			} else {
				dist.put(node, INF);
			}
		}
		dist.put(null, INF);

		// Meat of the function
		while (!q.isEmpty()) {
			T u = q.remove();
			if (dist.get(u) < dist.get(null)) {

				List<T> uEdges = getRandomizedEdges(u);
				for (T v : uEdges) {
					T vPair = vMatches.get(v);
					// This indicates that the pair to v is unmatched/untouched
					// or is null itself
					if (dist.get(vPair) == INF) {
						dist.put(vPair, dist.get(u) + 1);
						q.add(vPair);
					}
				}
			}
		}
		return dist.get(null) != INF;
	}

	/***
	 * Creates copy of edges and randomizes. The copy is to prevent altering
	 * original edge order and to protect iterations from future randomizations
	 * of same edges.
	 * 
	 * @param node
	 *            - the node we are getting the edges for
	 * @return
	 */
	private List<T> getRandomizedEdges(T node) {
		List<T> edges = new LinkedList<T>(node.getEdges());
		Collections.shuffle(edges);
		return edges;
	}

	/**
	 * helper function to instantiate empty maps
	 */
	private void clearMatchingsAndDistance() {
		vMatches = new HashMap<T, T>();
		uMatches = new HashMap<T, T>();
		dist = new HashMap<T, Integer>();
	}

}
