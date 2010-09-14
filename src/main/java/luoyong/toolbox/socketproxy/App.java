package luoyong.toolbox.socketproxy;

import luoyong.toolbox.socketproxy.ui.MainFrame;

/**
 *
 * @author Luo Yong &lt; luo.yong.name@gmail.com &gt;
 */
public class App {

   public static void main(String[] args) {

      MainFrame mainFrame = new MainFrame();
      mainFrame.setSize(500, 200);

      mainFrame.setVisible(true);
      mainFrame.pack();
   }
}
