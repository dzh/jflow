/**
 * 
 */
package jflow.example.analysis.lack;

import java.util.Map;

import jflow.core.node.AbstractNode;
import jflow.core.node.NodeChain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dzh
 * @date Apr 25, 2014 2:21:45 PM
 * @since 1.0
 */
public class MonitorAnalysis extends AbstractNode<Map<String, String>, Boolean> {

	static final Logger LOG = LoggerFactory.getLogger(MonitorAnalysis.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.Node#run(jflow.core.node.NodeChain,
	 * java.lang.Object)
	 */
	public Boolean run(NodeChain chain, Map<String, String> properties) {
		LOG.info("MonitorAnalysis is running");
		properties.put("MonitorAnalysis", "正常");
		chain.getFlow().invoke(chain, properties, "NetAnalysis");
		LOG.info("MonitorAnalysis is done");
		return true;
	}

}
