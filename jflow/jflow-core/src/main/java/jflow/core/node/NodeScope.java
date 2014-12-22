/**
 * 
 */
package jflow.core.node;

/**
 * Scope decides node's life-cycle and
 * <p>
 * Each scope's node
 * <li>Global,node object live in FlowExecutors</li>
 * <li>Flow,node object live in each flow instance</li>
 * <li>Chain,node object live in each chain instance</li>
 * </p>
 * *
 * <p>
 * node instance's scope path format:
 * <li>global,global</li>
 * <li>flow,flow:flowid</li>
 * <li>chain,chain:flowid:chainid</li>
 * </p>
 * 
 * @author dzh
 * @date Apr 23, 2014 1:12:36 PM
 * @since 1.0
 */
public enum NodeScope {

	Global {
		@Override
		public String toString() {
			return "global";
		}

		@Override
		public String getName() {
			return "global";
		}

		/**
		 * id: [nodeconfigid]
		 */
		@Override
		public String getNodePath(String... id) {
			return getName() + Sepr + id;
		}
	},
	Flow {
		@Override
		public String toString() {
			return "flow";
		}

		@Override
		public String getName() {
			return "flow";
		}

		/**
		 * id: [flowid,nodeconfigid]
		 */
		@Override
		public String getNodePath(String... id) {
			return getName() + Sepr + id[0];
		}
	},
	Chain {
		@Override
		public String toString() {
			return "chain";
		}

		@Override
		public String getName() {
			return "chain";
		}

		/**
		 * id: [flowid,chainid,nodeconfigid]
		 */
		@Override
		public String getNodePath(String... id) {
			return getName() + Sepr + id[0] + Sepr + id[1] + Sepr + id[2];
		}
	};

	final static String Sepr = ".";

	/**
	 * scope's name
	 * 
	 * @return
	 */
	public abstract String getName();

	/**
	 * 
	 * @param id 
	 * @return node path of this scope
	 */
	public abstract String getNodePath(String... id);

	public static final NodeScope getScope(String name) {
		if (Flow.name().equals(name))
			return Flow;
		if (Chain.name().equals(name))
			return Chain;
		if (Global.name().equals(name))
			return Global;
		return Flow;
	}

}
