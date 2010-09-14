package luoyong.toolbox.socketproxy.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Luo Yong &lt; luo.yong.name@gmail.com &gt;
 */
public class SocketProxyUtil {

   public static void closeInputStream(InputStream inputStream) {
      try {
         inputStream.close();
      }catch(Throwable t) {}
   }

   public static void closeOutputStream(OutputStream outputStream) {
      try {
         outputStream.close();
      }catch(Throwable t) {}
   }

   public static void closeSocket(Socket socket) {
      try {
         socket.close();
      }catch(Throwable t) {}
   }

   public static void closeServerSocket(ServerSocket serverSocket) {
      try {
         serverSocket.close();
      } catch (Throwable t) {}
   }
}
