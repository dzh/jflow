/**
 * 
 */
package jflow.core;

import jflow.core.node.Node;
import jflow.core.node.NodeChain;

/**
 * @author dzh
 * @date Apr 23, 2014 10:16:20 AM
 * @since 1.0
 */
public class FlowEvent {

	public static enum Event {
		ChainFork, ChainFinish,
	};

	Node<?, ?> node;

	NodeChain chain;

	Event event;

	Object result;

	// TODO
	boolean doit = false;

	public FlowEvent(Event e, NodeChain chain, Node<?, ?> node, Object result) {
		this.node = node;
		this.chain = chain;
		this.event = e;
	}

	public Node<?, ?> getNode() {
		return node;
	}

	public NodeChain getChain() {
		return chain;
	}

	public Event getEvent() {
		return event;
	}

}
