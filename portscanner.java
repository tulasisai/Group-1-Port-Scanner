import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.net.*;

public class portscanner {

	private JFrame frmPortscanner;
	private JTextField hostname;
	private JTextField startport;
	private JTextField endport;
	private JLabel lblPortNumber;
	private JLabel lblRunningService;
	private JTextArea textArea;
	private JButton btnClearConsole;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					portscanner window = new portscanner();
					window.frmPortscanner.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public portscanner() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPortscanner = new JFrame();
		frmPortscanner.getContentPane().setBackground(Color.BLACK);
		frmPortscanner.getContentPane().setLayout(null);
		
		hostname = new JTextField();
		hostname.setForeground(Color.WHITE);
		hostname.setBackground(Color.BLACK);
		hostname.setBounds(35, 43, 150, 24);
		frmPortscanner.getContentPane().add(hostname);
		hostname.setColumns(10);
		
		startport = new JTextField();
		startport.setForeground(Color.WHITE);
		startport.setBackground(Color.BLACK);
		startport.setBounds(231, 44, 114, 22);
		frmPortscanner.getContentPane().add(startport);
		startport.setColumns(10);
		
		endport = new JTextField();
		endport.setForeground(Color.WHITE);
		endport.setBackground(Color.BLACK);
		endport.setBounds(392, 43, 114, 24);
		frmPortscanner.getContentPane().add(endport);
		endport.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 106, 589, 240);
		frmPortscanner.getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(Color.BLACK);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		
		JButton btnStart = new JButton("Start");
		btnStart.setForeground(Color.WHITE);
		btnStart.setBackground(Color.BLACK);
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
				String ipaddress  = hostname.getText();
				String start_port = startport.getText();
				String end_port = endport.getText();
				int startPort = Integer.valueOf(start_port);
				int endPort = Integer.valueOf(end_port);
				BufferedReader br = null;
				try {
					FileInputStream fstream = new FileInputStream("ports.txt");
					br = new BufferedReader(new InputStreamReader(fstream));
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for(int port=startPort;port<=endPort;port++)
				{
					try {
			            Socket socket = new Socket();
			            socket.connect(new InetSocketAddress(ipaddress, port), 100);
			            socket.close();
			            String openport = Integer.toString(port);
			            String strline;
			            String desc="";
			            String data="";
			            while((strline = br.readLine())!=null) {
			            	String[] ddr = strline.split("/tcp:");
			            	if(openport.equals(ddr[0])) {
			            		desc = ddr[1];
			            		data = openport+"\t\t\t"+ddr[1]+"\n";
			            		
			            		break;
			            	}
			            	else {
			            		desc = "Service Not found";
			            		data = openport+"\t\t\t"+desc+"\n";
			            	}
			            }
			            textArea.append(data);
			        } catch (Exception ex) {
			        }
				}
				JOptionPane.showMessageDialog(frmPortscanner, "scanning complete", "Port-Scanner", JOptionPane.INFORMATION_MESSAGE);		    
			}
		});
		btnStart.setBounds(542, 42, 82, 25);
		frmPortscanner.getContentPane().add(btnStart);
		
		lblPortNumber = new JLabel("Port Number");
		lblPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
		lblPortNumber.setForeground(Color.WHITE);
		lblPortNumber.setBounds(45, 79, 101, 15);
		frmPortscanner.getContentPane().add(lblPortNumber);
		
		lblRunningService = new JLabel("Running Service");
		lblRunningService.setHorizontalAlignment(SwingConstants.CENTER);
		lblRunningService.setForeground(Color.WHITE);
		lblRunningService.setBounds(289, 79, 126, 15);
		frmPortscanner.getContentPane().add(lblRunningService);
		
		btnClearConsole = new JButton("Clear Console");
		btnClearConsole.setForeground(Color.WHITE);
		btnClearConsole.setBackground(Color.BLACK);
		btnClearConsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
			}
		});
		btnClearConsole.setBounds(486, 358, 138, 25);
		frmPortscanner.getContentPane().add(btnClearConsole);
		
		JLabel lblHostname = new JLabel("HostName");
		lblHostname.setForeground(Color.WHITE);
		lblHostname.setHorizontalAlignment(SwingConstants.CENTER);
		lblHostname.setBounds(35, 16, 150, 15);
		frmPortscanner.getContentPane().add(lblHostname);
		
		JLabel lblStartPort = new JLabel("Start Port");
		lblStartPort.setForeground(Color.WHITE);
		lblStartPort.setHorizontalAlignment(SwingConstants.CENTER);
		lblStartPort.setBounds(231, 17, 114, 15);
		frmPortscanner.getContentPane().add(lblStartPort);
		
		JLabel lblEndPort = new JLabel("End Port");
		lblEndPort.setForeground(Color.WHITE);
		lblEndPort.setHorizontalAlignment(SwingConstants.CENTER);
		lblEndPort.setBounds(392, 16, 114, 15);
		frmPortscanner.getContentPane().add(lblEndPort);
		frmPortscanner.setResizable(false);
		frmPortscanner.setTitle("Port-Scanner");
		frmPortscanner.setBounds(100, 100, 652, 421);
		frmPortscanner.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
