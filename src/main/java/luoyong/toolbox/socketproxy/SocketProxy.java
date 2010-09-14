package luoyong.toolbox.socketproxy;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Luo Yong &lt; luo.yong.name@gmail.com &gt;
 */
public class SocketProxy {

   private int proxyPort;
   private String remoteHost = null;
   private int remotePort;

   public SocketProxy(int proxyPort, String remoteHost, int remotePort) {
      this.proxyPort = proxyPort;
      this.remoteHost = remoteHost;
      this.remotePort = remotePort;
   }

   public void testRemoteServerConnection() throws UnknownHostException, IOException {
      Socket socket = new Socket(this.remoteHost, this.remotePort);
   }

   private Socket connectToRemoteServer() throws UnknownHostException, IOException {
      Socket socket = new Socket(this.remoteHost, this.remotePort);
      return socket;
   }

   public void start() {
      try {
         ServerSocket serverSocket = new ServerSocket(proxyPort, 500);
         for (;;) {
            try {
               Socket clientSocket = serverSocket.accept();
               Socket remoteSocket = this.connectToRemoteServer();
               SocketPair socketPair = new SocketPair(
                       clientSocket, remoteSocket);

               (new Thread(new ThreadClientToServer(socketPair))).start();
               (new Thread(new ThreadServerToClient(socketPair))).start();
            }catch(Throwable t) {
               t.printStackTrace(System.err);
            }
         }
      } catch (IOException ex) {
         ex.printStackTrace(System.err);
      }
   }
}
