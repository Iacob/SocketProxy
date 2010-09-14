package luoyong.toolbox.socketproxy;

import java.net.Socket;

/**
 *
 * @author Luo Yong &lt; luo.yong.name@gmail.com &gt;
 */
public class SocketPair {

   private Socket clientSocket = null;
   private Socket remoteSocket = null;
   private boolean clientToRemoteComplete = false;
   private boolean remoteToClientComplete = false;

   public SocketPair(Socket clientSocket, Socket remoteSocket) {
      this.clientSocket = clientSocket;
      this.remoteSocket = remoteSocket;
   }

   public Socket getClientSocket() {
      return clientSocket;
   }

   public void setClientSocket(Socket clientSocket) {
      this.clientSocket = clientSocket;
   }

   public Socket getRemoteSocket() {
      return remoteSocket;
   }

   public void setRemoteSocket(Socket remoteSocket) {
      this.remoteSocket = remoteSocket;
   }

   public boolean isClientToRemoteComplete() {
      return clientToRemoteComplete;
   }

   public void setClientToRemoteComplete(boolean clientToRemoteComplete) {
      this.clientToRemoteComplete = clientToRemoteComplete;
   }

   public boolean isRemoteToClientComplete() {
      return remoteToClientComplete;
   }

   public void setRemoteToClientComplete(boolean remoteToClientComplete) {
      this.remoteToClientComplete = remoteToClientComplete;
   }
}
