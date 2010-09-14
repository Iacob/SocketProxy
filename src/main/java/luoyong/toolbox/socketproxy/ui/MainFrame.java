package luoyong.toolbox.socketproxy.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import luoyong.toolbox.socketproxy.SocketProxy;

/**
 *
 * @author Luo Yong &lt; luo.yong.name@gmail.com &gt;
 */
public class MainFrame extends JFrame {

   private JTextField textFieldRemoteHost = null;
   private JTextField textFieldRemotePort = null;
   private JTextField textFieldListenerPort = null;
   private JButton buttonStartForward = null;

   private SocketProxy proxy = null;

   public MainFrame() {

      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      this.setTitle("SocketProxy");

      textFieldRemoteHost = new JTextField(10);
      textFieldRemotePort = new JTextField(5);
      textFieldListenerPort = new JTextField(5);
      buttonStartForward = new JButton("开始转发");

      JPanel topPanel = new JPanel();
      topPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
      topPanel.add(new JLabel("服务器地址："));
      topPanel.add(textFieldRemoteHost);
      topPanel.add(new JLabel("服务器端口："));
      topPanel.add(textFieldRemotePort);
      topPanel.add(new JLabel("本地监听端口："));
      topPanel.add(textFieldListenerPort);
      topPanel.add(buttonStartForward);

      buttonStartForward.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            startForward();
         }
      });

      this.getContentPane().setLayout(new BorderLayout());
      this.getContentPane().add(topPanel, BorderLayout.CENTER);
   }

   public void startForward() {
      
      String remoteHost = this.textFieldRemoteHost.getText();

      int remotePort = 0;
      try {
         remotePort = Integer.parseInt(this.textFieldRemotePort.getText());
      }catch(Throwable t) {
         JOptionPane.showMessageDialog(this, "服务器端口应当为数字", "输入错误",
                 JOptionPane.ERROR_MESSAGE);
         return;
      }

      int listenPort = 0;
      try {
         listenPort = Integer.parseInt(this.textFieldListenerPort.getText());
      }catch(Throwable t) {
         JOptionPane.showMessageDialog(this, "监听端口应当为数字", "输入错误",
                 JOptionPane.ERROR_MESSAGE);
         return;
      }

      proxy = new SocketProxy(listenPort, remoteHost, remotePort);

      // 测试本机到远程服务器的连接
      try {
         proxy.testRemoteServerConnection();
      } catch (UnknownHostException ex) {
         ex.printStackTrace(System.err);
         JOptionPane.showMessageDialog(this, "找不到服务器", "出错",
                 JOptionPane.ERROR_MESSAGE);
         return;
      } catch (IOException ex) {
         ex.printStackTrace(System.err);
         JOptionPane.showMessageDialog(this, "连接出错", "出错",
                 JOptionPane.ERROR_MESSAGE);
         return;
      }

      // Lock user input.
      textFieldRemoteHost.setEditable(false);
      textFieldRemotePort.setEditable(false);
      textFieldListenerPort.setEditable(false);
      buttonStartForward.setEnabled(false);

      // Start this proxy in a new thread.
      Thread newThread = new Thread() {
         @Override
         public void run() {
            proxy.start();
         }
      };
      newThread.start();

      // Tell user that socket forwarding is started.
      this.buttonStartForward.setText("正在转发...");
      this.pack();
   }
}
