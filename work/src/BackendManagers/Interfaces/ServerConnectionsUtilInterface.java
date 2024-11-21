package BackendManagers.Interfaces;

import java.util.List;

/**
 * Interface for the Server Connections utilities class.  The implementing class should follow the specifications
 *   listed in the project description ("Story 7").
 *
 * <bold>251 students: you may use any of the data structures you have previously created, but may not use
 *   any Java util library except List/ArrayList and java.util.stream.* .</bold>
 */
public interface ServerConnectionsUtilInterface {
    /**
     * Simple container representing a connection between two servers
     */
    class ServerConnection {
        public int server1, server2;
        //simple constructor for a simple object
        public ServerConnection(int server1, int server2) { this.server1 = server1; this.server2 = server2; }

        //default intellij-generated equals
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ServerConnection)) return false;
            ServerConnection that = (ServerConnection) o;
            return server1 == that.server1 && server2 == that.server2;
        }

        /**
         * This might be helpful for debugging. Default generated one.
         * @return string version of object
         */
        @Override
        public String toString() {
            return "ServerConnection{" + "server1=" + server1 + ", server2=" + server2 + '}';
        }
    }

    /**
     * Selects server connections per the specifications (minimum cost to connect all servers).  Assumes
     *   all servers can be connected.
     *
     * @param filename file to read input from
     * @return list of server connections, per the specifications
     */
    List<ServerConnection> getMinimumServerConnections(String filename);
}
