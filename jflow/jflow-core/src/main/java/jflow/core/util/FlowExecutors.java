/**
 * 
 */
package jflow.core.util;

import java.io.File;
import java.util.List;

import jflow.core.Flow;
import jflow.core.FlowConfig;
import jflow.core.FlowListener;
import jflow.core.node.NodeConfig;
import jflow.core.node.NodeScope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * <li>scope mode,store node instance</li>
 * <li>TODO real-time mode</li>
 * </p>
 * 
 * @author dzh
 * @date Apr 22, 2014 1:43:00 PM
 * @since 1.0
 */
public class FlowExecutors {

	private static final Logger LOG = LoggerFactory
			.getLogger(FlowExecutors.class);

	private List<FlowConfig> _flows;

	private List<NodeConfig> _nodes;

	/**
	 * TODO ExecutorService
	 */
	private FlowExecutors() {
	}

	@Deprecated
	public <K> Flow submitFlow(String id, K properties, FlowListener l)
			throws FlowExecutorException {
		FlowConfig fc = getFlowConfig(id);
		if (fc == null)
			throw new FlowExecutorException("Not found flow config: " + id);
		Flow f = createFlow(fc);
		if (l != null)
			f.addFlowListener(l);
		f.<K> start(null, properties);
		return f;
	}

	public <K> Flow submitFlow(String flowID, K properties, String nodeID,
			FlowListener l) throws FlowExecutorException {
		FlowConfig fc = getFlowConfig(flowID);
		if (fc == null)
			throw new FlowExecutorException("Not found flow config: " + flowID);
		Flow f = createFlow(fc);
		if (l != null)
			f.addFlowListener(l);
		f.init(fc);
		f.<K> start(null, properties, nodeID);
		return f;
	}

	public Flow createFlow(String flowID) throws FlowExecutorException {
		FlowConfig fc = getFlowConfig(flowID);
		if (fc == null)
			throw new FlowExecutorException("Not found flow config: " + flowID);
		Flow f = createFlow(fc);
		f.init(fc);
		return f;
	}

	private FlowConfig getFlowConfig(String id) {
		if (_flows == null)
			return null;
		for (FlowConfig fc : _flows) {
			if (fc.getID().equals(id))
				return fc;
		}
		return null;
	}

	static final Flow createFlow(FlowConfig fc) throws FlowExecutorException {
		try {
			return fc.getClazz().getConstructor(String.class)
					.newInstance(IDUtil.generateFlowID());
		} catch (Exception e) {
			throw new FlowExecutorException(
					"Create flow instance error! Exception: " + e.getMessage());
		}
	}

	private static final FlowExecutors _Instance = new FlowExecutors();

	public static final FlowExecutors getInstance() {
		return _Instance;
	}

	public static final FlowExecutors createInstance() {
		return new FlowExecutors();
	}

	/**
	 * 
	 * @param path
	 * @throws FlowParseException
	 */
	public void loadConfig(File nodes, File flows) throws FlowParseException {
		loadConfig(nodes, flows,
				Thread.currentThread().getContextClassLoader(), Thread
						.currentThread().getContextClassLoader());
	}

	/**
	 * 
	 * @param path
	 * @param fcl
	 *            flow's classloader
	 * @param ncl
	 *            node's classloader
	 */
	public void loadConfig(File nodes, File flows, ClassLoader ncl,
			ClassLoader fcl) throws FlowParseException {
		LOG.info("Loading nodes file: {}", nodes.getAbsolutePath());
		if (!nodes.exists()) {
			throw new FlowParseException("Not found nodes file!");
		}
		_nodes = FlowParseUtil.parseNodesXml(nodes, ncl);

		LOG.info("Loading flows file: {}", flows.getAbsolutePath());
		if (!flows.exists()) {
			throw new FlowParseException("Not found flows file!");
		}
		_flows = FlowParseUtil.parseFlowsXml(flows, fcl, _nodes);
	}

	/**
	 * 
	 */
	public void finish() {
		_flows.clear();
		for (NodeConfig nc : _nodes) {
			if (nc.getScope() == NodeScope.Global)
				nc.fnhNode(NodeScope.Global.getNodePath(nc.getID()));
		}
		_nodes.clear();
		// TODO finish global scope,add clear
		LOG.info("FlowExecutors finished.");
	}
}
