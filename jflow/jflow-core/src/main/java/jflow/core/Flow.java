/**
 * 
 */
package jflow.core;

import jflow.core.node.NodeChain;

/**
 * <p>
 * A flow indicates a flow task
 * <li>manage nodes's life cycle,Node's creator destroy node object</li>
 * <li>manage chains,</li>
 * </p>
 * 
 * @author dzh
 * @date Apr 22, 2014 12:55:46 PM
 * @since 1.0
 */
public interface Flow {

	/**
	 * flow instance id
	 * 
	 * @return
	 */
	String getID();

	FlowConfig getConfig();

	void init(FlowConfig config);

	void addFlowListener(FlowListener l);

	void removeFlowListener(FlowListener l);

	/**
	 * This method has the same effect with
	 * <code> invoke(chain, properties, startID)</code>
	 * 
	 * @param chain
	 * @param properties
	 */
	@Deprecated
	<K> void start(NodeChain chain, K properties);

	<K> void start(NodeChain chain, K properties, String nodeID);

	/**
	 * <p>
	 * <li>finish the chain</li>
	 * <li>if the chain is null,finish all chains</li>
	 * </p>
	 * 
	 * @param chain
	 */
	void finish(NodeChain chain);

	/**
	 * 
	 * @param chain
	 * @return true then can invoke
	 */
	boolean canInvoke(NodeChain chain);

	/**
	 * Invoke a new node's run method.If this node hasn't been created ,create
	 * it's object first.
	 * 
	 * @param nodeID
	 * @param chain
	 * @param properties
	 */
	<K> void invoke(NodeChain chain, K properties, String nodeID);

	/**
	 * notify flow's changed event to flowlistener
	 * 
	 * @param e
	 */
	void broadcast(FlowEvent e);

	void finish();

}
