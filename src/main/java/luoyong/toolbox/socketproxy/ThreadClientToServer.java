package luoyong.toolbox.socketproxy;

import java.io.InputStream;
import java.io.OutputStream;
import luoyong.toolbox.socketproxy.util.SocketProxyUtil;

/**
 *
 * @author Luo Yong &lt; luo.yong.name@gmail.com &gt;
 */
public class ThreadClientToServer implements Runnable {

   private SocketPair socketPair = null;

   public ThreadClientToServer(SocketPair socketPair) {
      this.socketPair = socketPair;
   }

   public void run() {

      InputStream clientInputStream = null;
      OutputStream remoteOutputStream = null;

      try {
         clientInputStream = socketPair.getClientSocket().getInputStream();
         remoteOutputStream = socketPair.getRemoteSocket().getOutputStream();

         byte dataBuffer[] = new byte[512];
         int dataTransferred = 0;

         for (;;) {
            dataTransferred = clientInputStream.read(dataBuffer);
            if (dataTransferred < 0) {
               break;
            }
            remoteOutputStream.write(dataBuffer, 0, dataTransferred);
         }
      } catch (Throwable t) {
      }

      socketPair.setClientToRemoteComplete(true);
      for (;;) {
         if (socketPair.isRemoteToClientComplete()) {
            SocketProxyUtil.closeInputStream(clientInputStream);
            SocketProxyUtil.closeOutputStream(remoteOutputStream);
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
