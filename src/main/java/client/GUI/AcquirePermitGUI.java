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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cardsSelected = new ArrayList<PoliticsCard>();
		
		contentPane = new JPanel();
		contentPane.setMinimumSize(frameDimension);
		contentPane.setBorder(new LineBorder(Color.BLACK));
		contentPane.setSize(frameDimension);
		setContentPane(contentPane);
		contentPane.setLayout(null);
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
		
		Region region = game.getRegions().stream().filter(e->e.getName().equalsIgnoreCase("land")).findFirst().get();
		this.paintCouncil(councils, region, new Point(councilsDim.width*54/1000, councilsDim.height*146/1000));
		region = game.getRegions().stream().filter(e->e.getName().equalsIgnoreCase("hill")).findFirst().get();
		this.paintCouncil(councils, region, new Point(councilsDim.width*385/1000, councilsDim.height*146/1000));
		region = game.getRegions().stream().filter(e->e.getName().equalsIgnoreCase("mountain")).findFirst().get();
		this.paintCouncil(councils, region, new Point(councilsDim.width*745/1000, councilsDim.height*146/1000));
	}
	
	private void paintCouncil(JPanel panel, Region region, Point p) {
		Iterator<Councillor> gameCouncillor = region.getCouncil().getCouncillors().iterator();
		Dimension councillorDimension = new Dimension(councilDimension.width/4, councilDimension.height);
		
		JPanel council = new JPanel();
		council.setSize(councilDimension);
		council.setLocation(p);
		council.setLayout(null);
		council.setVisible(true);
		council.setName(region.getName()+"Council");
		council.setVisible(true);
		council.setBorder(new LineBorder(Color.BLACK));
		panel.add(council);
		
		ImagePanel councillor1 = new ImagePanel(gameCouncillor.next().getImagePath(), councillorDimension);
		councillor1.setSize(councillorDimension);
		councillor1.setLocation(panel.getWidth()*3/4,0);
		councillor1.setVisible(true);
		council.add(councillor1);
		ImagePanel councillor2 = new ImagePanel(gameCouncillor.next().getImagePath(), councillorDimension);
		councillor2.setSize(councillorDimension);
		councillor2.setLocation(panel.getWidth()/2, 0);
		councillor2.setVisible(true);
		council.add(councillor2);
		ImagePanel councillor3 = new ImagePanel(gameCouncillor.next().getImagePath(), councillorDimension);
		councillor3.setSize(councillorDimension);
		councillor3.setLocation(panel.getWidth()/4, 0);
		councillor3.setVisible(true);
		council.add(councillor3);
		ImagePanel councillor4 = new ImagePanel(gameCouncillor.next().getImagePath(), councillorDimension);
		councillor4.setSize(councillorDimension);
		councillor4.setLocation(0, 0);
		councillor4.setVisible(true);
		council.add(councillor4);
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
				councilSelected=region.getCouncil();
			}
		});
	}
}
