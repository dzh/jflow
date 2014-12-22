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
 * @date Apr 25, 2014 2:22:50 PM
 * @since 1.0
 */
public class NetAnalysis extends AbstractNode<Map<String, String>, Boolean> {

	static final Logger LOG = LoggerFactory.getLogger(NetAnalysis.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see jflow.core.node.Node#run(jflow.core.node.NodeChain,
	 * java.lang.Object)
	 */
	public Boolean run(NodeChain chain, Map<String, String> properties) {
		LOG.info("NetAnalysis is running");
		properties.put("NetAnalysis", "异常");
		for (String key : properties.keySet()) {
			LOG.info("key: " + key + ", value: " + properties.get(key));
		}

		LOG.info("NetAnalysis is done");
		chain.finish();
		return false;
	}

}
