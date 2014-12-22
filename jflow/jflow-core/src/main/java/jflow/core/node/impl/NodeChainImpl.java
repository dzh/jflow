/**
 * 
 */
package jflow.core.node.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jflow.core.Flow;
import jflow.core.FlowEvent;
import jflow.core.node.NodeChain;
import jflow.core.node.NodeConfig;
import jflow.core.node.NodeScope;
import jflow.core.util.IDUtil;

/**
 * @ThreadSafe
 * @author dzh
 * @date Apr 23, 2014 5:40:32 PM
 * @since 1.0
 */
public class NodeChainImpl implements NodeChain {

	private String id;

	private Flow flow;

	protected NodeChain parent;

	protected List<String> nodes = Collections
			.synchronizedList(new LinkedList<String>());

	protected Map<String, Integer> index = Collections
			.synchronizedMap(new HashMap<String, Integer>());

	public NodeChainImpl(String id, Flow flow) {
		this.id = id;
		this.flow = flow;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeChain#getID()
	 */
	public String getID() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeChain#getFlow()
	 */
	public Flow getFlow() {
		return flow;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeChain#parent()
	 */
	public NodeChain parent() {
		return parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeChain#fork()
	 */
	public NodeChain fork() {
		NodeChainImpl chain = new NodeChainImpl(IDUtil.generateNodeChainID(),
				this.flow);
		// TODO parent chain
		chain.nodes = Collections.synchronizedList(new LinkedList<String>(
				this.nodes));
		chain.index = Collections.synchronizedMap(new HashMap<String, Integer>(
				this.index));
		chain.parent = this;
		return chain;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeChain#appendNode(java.lang.String)
	 */
	public void appendNode(String nodeID) {
		nodes.add(nodeID);
		index.put(nodeID, nodes.size() - 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeChain#prevNode(java.lang.String)
	 */
	public String prevNode(String nodeID) {
		Integer loc = index.get(nodeID);
		if (loc == null || loc == 0) // TODO
			return null;
		return nodes.get(loc - 1);
	}

	private Map<String, Object> attachMap = Collections
			.synchronizedMap(new HashMap<String, Object>());

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeChain#getAttachment(java.lang.String)
	 */
	public Object getAttachment(String nodeID) {
		return attachMap.get(nodeID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeChain#finish()
	 */
	public void finish() {
		if (fnh)
			return;
		fnh = true;
		// finish nodes
		finishChainScopeNodes();
		// flow = null;
		nodes = null;
		index = null;
		parent = null;
		attachMap = null;
		flow.broadcast(new FlowEvent(FlowEvent.Event.ChainFinish, this, null,
				null));
	}

	/**
	 * @param id2
	 */
	private void finishChainScopeNodes() {
		for (String nodeID : nodes) {
			NodeConfig nc = flow.getConfig().getNodeConfig(nodeID);
			nc.fnhNode(NodeScope.Chain.getNodePath(flow.getID(), this.getID(),
					nodeID));
		}
	}

	private volatile boolean fnh = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeChain#isFinished()
	 */
	public boolean isFinished() {
		return fnh;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.NodeChain#putAttachment(java.lang.String,
	 * java.lang.Object)
	 */
	public void putAttachment(String nodeID, Object obj) {
		attachMap.put(nodeID, obj);
	}

}
