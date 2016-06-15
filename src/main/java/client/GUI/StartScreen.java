package client.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdom2.JDOMException;

import client.ClientSocket;
import client.ConfigReader;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JRadioButton;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Enumeration;

public class StartScreen extends JFrame {

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
	public StartScreen() throws JDOMException, IOException {
		ConfigReader reader=new ConfigReader();
		this.host=reader.getIP();
		this.rmiPort=reader.getRMIPort();
		this.socketPort=reader.getSocketPort();
		
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		Dimension backSize=new Dimension((int) (screenSize.width*0.5), screenSize.height);
		JPanel background=new ImagePanel("src/main/resources/Immagini/locandina.jpg",backSize);
		contentPane.add(background);
		GridBagLayout gbl_background = new GridBagLayout();
		gbl_background.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_background.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_background.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_background.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		background.setLayout(gbl_background);
		
		JLabel lblSelect = new JLabel("Select the connection type");
		GridBagConstraints gbc_lblSelect = new GridBagConstraints();
		gbc_lblSelect.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelect.gridwidth = 2;
		gbc_lblSelect.gridx = 5;
		gbc_lblSelect.gridy = 2;
		background.add(lblSelect, gbc_lblSelect);
		
		JRadioButton rdbtnRmi = new JRadioButton("RMI");
		rdbtnRmi.setSelected(true);
		GridBagConstraints gbc_rdbtnRmi = new GridBagConstraints();
		gbc_rdbtnRmi.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnRmi.gridx = 5;
		gbc_rdbtnRmi.gridy = 3;
		background.add(rdbtnRmi, gbc_rdbtnRmi);
		
		JRadioButton rdbtnSocket = new JRadioButton("Socket");
		GridBagConstraints gbc_rdbtnSocket = new GridBagConstraints();
		gbc_rdbtnSocket.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnSocket.gridx = 5;
		gbc_rdbtnSocket.gridy = 4;
		background.add(rdbtnSocket, gbc_rdbtnSocket);
		
		txtInsertYourName = new JTextField();
		txtInsertYourName.setText("Insert your name");
		GridBagConstraints gbc_txtInsertYourName = new GridBagConstraints();
		gbc_txtInsertYourName.insets = new Insets(0, 0, 5, 5);
		gbc_txtInsertYourName.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtInsertYourName.gridx = 5;
		gbc_txtInsertYourName.gridy = 5;
		background.add(txtInsertYourName, gbc_txtInsertYourName);
		txtInsertYourName.setColumns(10);

		ButtonGroup connectionGroup = new ButtonGroup();
		connectionGroup.add(rdbtnRmi);
		connectionGroup.add(rdbtnSocket);
		
		JButton btnConnection = new JButton("CONNECTION");
		btnConnection.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JRadioButton connectionSelected=(JRadioButton) getRadioSelected(connectionGroup);
				String name=txtInsertYourName.getText();
				System.out.println(connectionSelected.getText());
				if(connectionSelected.getText().equalsIgnoreCase("socket")){
				
					ClientSocket client=new ClientSocket(host, socketPort, new GUI());
					try {
						client.runClient(name);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					((GUI)client.getView()).setRegionsBackground();
					//((GUI)client.getView()).setVisible(true);
				}else if(connectionSelected.getText().equalsIgnoreCase("RMI")){
					
				}
				
			}
		});
		GridBagConstraints gbc_btnConnection = new GridBagConstraints();
		gbc_btnConnection.insets = new Insets(0, 0, 0, 5);
		gbc_btnConnection.gridx = 5;
		gbc_btnConnection.gridy = 6;
		background.add(btnConnection, gbc_btnConnection);
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

