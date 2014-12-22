/**
 * 
 */
package jflow.core.impl;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jflow.core.Flow;
import jflow.core.FlowConfig;
import jflow.core.FlowEvent;
import jflow.core.FlowListener;
import jflow.core.node.Node;
import jflow.core.node.NodeChain;
import jflow.core.node.NodeConfig;
import jflow.core.node.NodeScope;
import jflow.core.node.impl.NodeChainImpl;
import jflow.core.util.IDUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ThreadSafe
 * @author dzh
 * @date Apr 23, 2014 5:17:48 PM
 * @since 1.0
 */
public class FlowImpl implements Flow {

	static final Logger LOG = LoggerFactory.getLogger(FlowImpl.class);

	private String id;

	private FlowConfig _config;

	private ExecutorService _executor;

	public FlowImpl(String id) {
		this(id, null);
	}

	public FlowImpl(String id, ExecutorService es) {
		if (es == null) {
			es = Executors.newCachedThreadPool();
		}
		this.id = id;
		this._executor = es;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.Flow#getID()
	 */
	public String getID() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.Flow#getConfig()
	 */
	public FlowConfig getConfig() {
		return _config;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.Flow#init(jflow.core.FlowConfig)
	 */
	public void init(FlowConfig config) {
		this._config = config;
		this.addFlowListener(new InnerFlowListener());
		initFlowScopeNode(config);
	}

	class InnerFlowListener implements FlowListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see jflow.core.FlowListener#flowChanged(jflow.core.FlowEvent)
		 */
		public void flowChanged(FlowEvent e) {
			if (e.getEvent() == FlowEvent.Event.ChainFinish) {
				nodes.remove(e.getChain());
			}
		}

	}

	/**
	 * @param config
	 */
	private void initFlowScopeNode(FlowConfig config) {
		// TODO
	}

	private CopyOnWriteArrayList<FlowListener> _listeners = new CopyOnWriteArrayList<FlowListener>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.Flow#addFlowListener(jflow.core.FlowListener)
	 */
	public void addFlowListener(FlowListener l) {
		_listeners.add(l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.Flow#removeFlowListener(jflow.core.FlowListener)
	 */
	public void removeFlowListener(FlowListener l) {
		_listeners.remove(l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.Flow#start(jflow.core.node.NodeChain, java.lang.Object)
	 */
	public <K> void start(NodeChain chain, K properties) {
		if (chain == null)
			chain = new NodeChainImpl(IDUtil.generateNodeChainID(), this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.Flow#finish(jflow.core.node.NodeChain)
	 */
	public void finish(NodeChain chain) {
		if (chain == null) {
			finishChainScopeNodes();
			return;
		}
		chains.remove(chain);
		chain.finish();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.Flow#canInvoke(jflow.core.node.NodeChain)
	 */
	public boolean canInvoke(NodeChain chain) {
		// TODO sub-class
		return !chain.isFinished();
	}

	/**
	 * flow scope nodes' id
	 */
	private List<String> nodes = Collections
			.synchronizedList(new LinkedList<String>());

	private List<NodeChain> chains = Collections
			.synchronizedList(new LinkedList<NodeChain>());

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.Flow#invoke(jflow.core.node.NodeChain, java.lang.Object,
	 * java.lang.String)
	 */
	public <K> void invoke(final NodeChain chain, final K properties,
			String nodeID) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Invoke flow:{}, chain:{}, node:{}", getID(),
					chain.getID(), nodeID);
		}

		if (chain.isFinished())
			return;
		if (!chains.contains(chain)) {
			chains.add(chain);
		}
		try {
			// TODO
			@SuppressWarnings("unchecked")
			final Node<K, ?> node = (Node<K, ?>) getNode(this, chain, nodeID);
			if (node == null) {
				// TODO
				LOG.error("Not found node: " + nodeID);
				return;
			}
			_executor.execute(new Runnable() {
				public void run() {
					try {
						node.run(chain, properties);
					} catch (Exception e) {
						LOG.error(e.getMessage());
					}
				}
			});
		} catch (Exception e) {
			// TODO
			LOG.error(e.getMessage());
		}
	}

	private final Node<?, ?> getNode(Flow flow, NodeChain chain, String nodeID)
			throws Exception {
		final NodeConfig nc = flow.getConfig().getNodeConfig(nodeID);
		if (nc == null)
			return null;
		final NodeScope scope = nc.getScope();
		String path = null;
		Node<?, ?> node = null;
		if (NodeScope.Global == scope) {
			path = scope.getNodePath(nodeID);
		} else if (NodeScope.Flow == scope) {
			path = scope.getNodePath(flow.getID(), nodeID);
		} else if (NodeScope.Chain == scope) {
			path = scope.getNodePath(flow.getID(), chain.getID(), nodeID);
		}

		synchronized (nc) { // TODO
			node = nc.getNode(path);
			if (node == null) {
				node = nc.getClazz().newInstance();
				node.init(nc);
				nc.putNode(path, node);
			}
		}

		return node;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.Flow#broadcast(jflow.core.FlowEvent)
	 */
	public void broadcast(FlowEvent e) {
		Iterator<FlowListener> iter = _listeners.iterator();
		while (iter.hasNext()) {
			iter.next().flowChanged(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.Flow#finish()
	 */
	public void finish() {
		finishChainScopeNodes();
		finishFlowScopeNodes();
		if (_executor != null) {
			_executor.shutdownNow();
			// _executor.shutdown();
			// try {
			// _executor.awaitTermination(30, TimeUnit.SECONDS);
			// } catch (InterruptedException e) {
			// }
		}
	}

	/**
	 * 
	 */
	private void finishChainScopeNodes() {
		for (NodeChain chain : chains) {
			chain.finish();
		}
		chains.clear();
	}

	/**
	 * 
	 */
	private void finishFlowScopeNodes() {
		for (String id : nodes) {
			NodeConfig nc = this.getConfig().getNodeConfig(id);
			nc.fnhNode(NodeScope.Flow.getNodePath(this.getID(), id));
		}
		nodes.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.Flow#start(jflow.core.node.NodeChain, java.lang.Object,
	 * java.lang.String)
	 */
	public <K> void start(NodeChain chain, K properties, String nodeID) {
		if (chain == null)
			chain = new NodeChainImpl(IDUtil.generateNodeChainID(), this);
		if (nodeID == null) {
			// TODO
		}
		invoke(chain, properties, nodeID);
	}

}
