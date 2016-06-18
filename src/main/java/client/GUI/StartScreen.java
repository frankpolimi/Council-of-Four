package client.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jdom2.JDOMException;
import client.ClientInterface;
import client.ClientRMI;
import client.ClientSocket;
import client.ConfigReader;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.Enumeration;

public class StartScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -941282003726349006L;
	private JPanel contentPane;
	private JTextField txtInsertYourName;
	private String host;
	private int socketPort;
	private int rmiPort;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartScreen frame = new StartScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void start(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartScreen frame = new StartScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	public StartScreen() throws JDOMException, IOException 
	{
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		screenSize.width=screenSize.height*714/1000;
		
		ConfigReader reader=new ConfigReader();
		this.host=reader.getIP();
		this.rmiPort=reader.getRMIPort();
		this.socketPort=reader.getSocketPort();
		this.setMinimumSize(screenSize);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setAlwaysOnTop(true);
		
		
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		this.setContentPane(contentPane);
		
		JPanel background=new ImagePanel("src/main/resources/Immagini/locandina.jpg",screenSize);
		contentPane.add(background);
		
		JPanel userInputPanel=new JPanel();
		background.add(userInputPanel);
		
		JLabel lblSelect = new JLabel("Select the connection type");
		userInputPanel.add(lblSelect);
		
		JRadioButton rdbtnRmi = new JRadioButton("RMI");
		rdbtnRmi.setSelected(true);
		userInputPanel.add(rdbtnRmi);
		
		JRadioButton rdbtnSocket = new JRadioButton("Socket");
		userInputPanel.add(rdbtnSocket);
		
		txtInsertYourName = new JTextField();
		txtInsertYourName.setText("Insert your name");
		txtInsertYourName.setColumns(10);
		userInputPanel.add(txtInsertYourName);
		
		ButtonGroup connectionGroup = new ButtonGroup();
		connectionGroup.add(rdbtnRmi);
		connectionGroup.add(rdbtnSocket);
			
		JButton btnConnection = new JButton("CONNECTION");
		userInputPanel.add(btnConnection);
		
		
		btnConnection.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JRadioButton connectionSelected=(JRadioButton) getRadioSelected(connectionGroup);
				String name=txtInsertYourName.getText();
				System.out.println(connectionSelected.getText());
				ClientInterface client=null;
				if(connectionSelected.getText().equalsIgnoreCase("socket")){
				
					client=new ClientSocket(host, socketPort, new GUI());
					try {
						client.runClient(name);
					} catch (IOException | NotBoundException | JDOMException | AlreadyBoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//((GUI)client.getClientView()).setRegionsBackground();
					//((GUI)client.getClientView()).cityBonusLoader();
					//((GUI)client.getView()).setVisible(true);
				}else if(connectionSelected.getText().equalsIgnoreCase("RMI")){
					try {
						client=new ClientRMI(host, rmiPort, new GUI());
					} catch (NotBoundException | JDOMException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				try {
					client.runClient(name);
				} catch (IOException | NotBoundException | JDOMException | AlreadyBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				((GUI)client.getClientView()).setRegionsBackground();
				try {
					((GUI)client.getClientView()).cityBonusLoader();
				} catch (JDOMException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				((GUI)client.getClientView()).setVisible(true);
				
			}
		});
		
	}
	
	private AbstractButton getRadioSelected(ButtonGroup group){
		if(group==null)
			throw new NullPointerException("Group is null");
		Enumeration<AbstractButton> buttons=group.getElements();
		AbstractButton button=buttons.nextElement();
		if(button.isSelected()) return button;
		while(buttons.hasMoreElements()){
			button= buttons.nextElement();
			if(button.isSelected()) return button;
		}
		return null;
	}

}

