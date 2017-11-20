package hopcroftKarp;

import java.util.List;

/***
 * Interface for use with HopcroftKarp Solver. If graph nodes implement this
 * interface you can use HK solver to match.
 * 
 * @author benjamain richardson
 *
 * @param <T>
 *            - Class implementing graphNode.
 */
public interface HKGraphNode<T extends HKGraphNode<T>> {

	// List must be of same type as graphNode implementer.
	public List<T> getEdges();
}
