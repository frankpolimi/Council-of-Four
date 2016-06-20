package client.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import model.game.Game;
import model.game.council.Councillor;
import model.game.topology.Region;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.util.Enumeration;
import java.util.Iterator;

import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ActionInput extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 660009630551746911L;
	
	private JPanel contentPane;
	private Dimension monitorDimension=Toolkit.getDefaultToolkit().getScreenSize();
	private Dimension frameDimension=new Dimension(monitorDimension.width*9/10, monitorDimension.height*3/4); 
	
	private ButtonGroup regionButton;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ActionInput frame = new ActionInput();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public ActionInput() {
		//TODO add Game
		this.setSize(frameDimension);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setMinimumSize(frameDimension);
		contentPane.setBorder(new LineBorder(Color.BLACK));
		contentPane.setSize(frameDimension);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSend = new JButton("Send");
		council();
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String x = getRadioSelected(regionButton).getText();
				JOptionPane.showMessageDialog(contentPane, x);
			}
		});
		btnSend.setSize(frameDimension.width*72/1000, frameDimension.height*399/10000);
		btnSend.setLocation(frameDimension.width*820/1000, frameDimension.height*873/1000);
		contentPane.add(btnSend);		
		/*
		JButton btnAbort = new JButton("Abort");
		btnAbort.setSize(frameDimension.width*72/1000, frameDimension.height*399/10000);
		btnAbort.setLocation(frameDimension.width*906/1000, frameDimension.height*873/1000);
		contentPane.add(btnAbort);
		*/
	}

	public void council() {
		JPanel councils = new JPanel();
		Dimension councilsDim = new Dimension(frameDimension.width*987/1000, frameDimension.height/3);
		Dimension councilDimension = new Dimension(councilsDim.width*154/1000, councilsDim.height/2);
		councils.setBounds(0, 0, councilsDim.width, councilsDim.height);
		contentPane.add(councils);
		councils.setLayout(null);
			
		JRadioButton region1 = new JRadioButton();
		region1.setName("land");
		region1.setHorizontalAlignment(SwingConstants.LEFT);
		region1.setSize(councilDimension);
		region1.setLocation(councilsDim.width*54/1000, councilsDim.height*146/1000);
		councils.add(region1);
		
		JRadioButton region2 = new JRadioButton();
		region2.setName("hill");
		region2.setHorizontalAlignment(SwingConstants.LEFT);
		region2.setSize(councilDimension);
		region2.setLocation(councilsDim.width*385/1000, councilsDim.height*146/1000);
		councils.add(region2);
		
		JRadioButton region3 = new JRadioButton();
		region3.setName("mountain");
		region3.setHorizontalAlignment(SwingConstants.LEFT);
		region3.setSize(councilDimension);
		region3.setLocation(councilsDim.width*745/1000, councilsDim.height*146/1000);
		councils.add(region3);
		
		regionButton = new ButtonGroup();
		regionButton.add(region1);
		regionButton.add(region2);
		regionButton.add(region3);
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
