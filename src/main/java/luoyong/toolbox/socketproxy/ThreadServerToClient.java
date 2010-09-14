package luoyong.toolbox.socketproxy;

import java.io.InputStream;
import java.io.OutputStream;
import luoyong.toolbox.socketproxy.util.SocketProxyUtil;

/**
 *
 * @author Luo Yong &lt; luo.yong.name@gmail.com &gt;
 */
public class ThreadServerToClient implements Runnable {

   private SocketPair socketPair = null;

   public ThreadServerToClient(SocketPair socketPair) {
      this.socketPair = socketPair;
   }

   public void run() {
      
      InputStream remoteInputStream = null;
      OutputStream clientOutputStream = null;

      try {
         remoteInputStream = socketPair.getRemoteSocket().getInputStream();
         clientOutputStream = socketPair.getClientSocket().getOutputStream();

         byte dataBuffer[] = new byte[512];
         int dataTransferred = 0;

         for (;;) {
            dataTransferred = remoteInputStream.read(dataBuffer);
            if (dataTransferred < 0) {
               break;
            }
            clientOutputStream.write(dataBuffer, 0, dataTransferred);
         }
      } catch (Throwable t) {
      }

      socketPair.setClientToRemoteComplete(true);
      for (;;) {
         if (socketPair.isRemoteToClientComplete()) {
            SocketProxyUtil.closeInputStream(remoteInputStream);
            SocketProxyUtil.closeOutputStream(clientOutputStream);
            SocketProxyUtil.closeSocket(socketPair.getClientSocket());
            SocketProxyUtil.closeSocket(socketPair.getRemoteSocket());
            return;
         }
         try {
            Thread.sleep(1000);
         } catch (InterruptedException ex) {
            ex.printStackTrace(System.err);
         }
      }
   }
}
