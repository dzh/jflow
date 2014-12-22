/**
 * 
 */
package jflow.core.node;

import jflow.core.Flow;

/**
 * <p>
 * <li>record a executing route</li>
 * <li>store node's attachment(temporary info)</li>
 * <li>handle dead cycle</li>
 * </p>
 * 
 * @author dzh
 * @date Apr 22, 2014 12:54:54 PM
 * @since 1.0
 */
public interface NodeChain {

	/**
	 * chain's identifier
	 * <p>
	 * Every chain's id is different
	 * </p>
	 * 
	 * @return chain's gene
	 */
	String getID();

	Flow getFlow();

	/**
	 * 
	 * @return chain's parent chain
	 */
	NodeChain parent();

	/**
	 * when a node invoking multi-nodes, fork a new chain for each child node.
	 * 
	 * @return
	 */
	NodeChain fork();

	/**
	 * append the node to chain's tail
	 * 
	 * @param nodeID
	 */
	void appendNode(String nodeID);

	/**
	 * previous node's id
	 * 
	 * @param nodeID
	 * @return
	 */
	String prevNode(String nodeID);

	/**
	 * attachment of this nodeID
	 * 
	 * @param nodeID
	 * @return
	 */
	Object getAttachment(String nodeID);

	void putAttachment(String nodeID, Object obj);

	/**
	 * dispose chain's resource
	 */
	void finish();

	/**
	 * 
	 * @return true after invoked finish(),then false
	 */
	boolean isFinished();

	// public interface NodeEntry {
	//
	// String getID();
	//
	// Object getInfo(Object key);
	// }

}
