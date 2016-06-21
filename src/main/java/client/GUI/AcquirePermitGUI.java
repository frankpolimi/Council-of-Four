package client.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import model.actions.AcquirePermit;
import model.actions.ElectCouncillor;
import model.game.BuildingPermit;
import model.game.Game;
import model.game.council.Council;
import model.game.council.Councillor;
import model.game.council.RegionalCouncil;
import model.game.politics.PoliticsCard;
import model.game.topology.Region;
import view.ActionRequest;
import view.View;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AcquirePermitGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 660009630551746911L;
	
	private JPanel contentPane;
	private Dimension monitorDimension=Toolkit.getDefaultToolkit().getScreenSize();
	private Dimension frameDimension=new Dimension(monitorDimension.width/2, monitorDimension.height/2); 
	private Dimension councilsDim = new Dimension(frameDimension.width*987/1000, frameDimension.height/3);
	private Dimension councilDimension = new Dimension(councilsDim.width*154/1000, councilsDim.height/2);
	
	private RegionalCouncil councilSelected;
	private BuildingPermit permitSelected;
	private ArrayList<PoliticsCard> cardsSelected;
	private Game game;
	private GUI gui;
	
	public AcquirePermitGUI(Game game, GUI gui) {
		this.game = game;
		this.gui = gui;
		
		this.setSize(frameDimension);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		cardsSelected = new ArrayList<PoliticsCard>();
		
		contentPane = new JPanel();
		contentPane.setMinimumSize(frameDimension);
		contentPane.setBorder(new LineBorder(Color.BLACK));
		contentPane.setSize(frameDimension);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.council();
		JButton btnSend = new JButton("Send");
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(permitSelected==null||councilSelected==null){
					JOptionPane.showMessageDialog(null, "You have to choise one council and one permit", "Error", JOptionPane.ERROR_MESSAGE);
				}else{
					if(JOptionPane.showConfirmDialog(null, "Do you want to confirm your choise?", "Action", JOptionPane.YES_NO_OPTION)==0){		
						gui.setRequest(new ActionRequest(new AcquirePermit(councilSelected, cardsSelected, permitSelected), gui.getId()));
						setVisible(false);
					}		
				}
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
		
		councils.setBounds(0, 0, councilsDim.width, councilsDim.height);
		contentPane.add(councils);
		councils.setLayout(null);
		
		this.createCouncilPanel(councils, new Point(councilsDim.width*54/1000, councilsDim.height*146/1000), "land");
		this.createCouncilPanel(councils, new Point(councilsDim.width*385/1000, councilsDim.height*146/1000), "hill");
		this.createCouncilPanel(councils, new Point(councilsDim.width*745/1000, councilsDim.height*146/1000), "mountain");
	}
	
	private void createCouncilPanel(JPanel panel, Point p, String name) {
		JPanel council = new JPanel();
		council.setSize(councilDimension);
		council.setLocation(p);
		council.setLayout(null);
		council.setVisible(true);
		council.setName(name);
		council.setVisible(true);
		council.setBorder(new LineBorder(Color.BLACK));
		gui.paintCouncil(council, councilDimension);
		panel.add(council);
		
		council.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for(Component c:contentPane.getComponents()){
					if(c.getName()!=null&&c.getName().contains("Council"))
						for(Component c2:((JPanel)c).getComponents()){
							((JPanel)c2).setBorder(new LineBorder(Color.black,1));
						}
				}

				for(Component c:council.getComponents()){
					((JPanel)c).setBorder(new LineBorder(Color.yellow,3));
				}
				councilSelected=game.getRegions().stream().filter(t->t.getName().equals(council.getName())).findFirst().get().getCouncil();
			}
		});
	}
}
