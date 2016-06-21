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
	private Dimension sectionDimension = new Dimension(frameDimension.width*987/1000, frameDimension.height*26/100);
	private Dimension councilDimension = new Dimension(sectionDimension.width*154/1000, sectionDimension.height/2);
	private Dimension permitsDimension = new Dimension(sectionDimension.width/5, sectionDimension.height/2);
	
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
		btnSend.setLocation(frameDimension.width*82/100, frameDimension.height*823/1000);
		contentPane.add(btnSend);
		
		JPanel councils = new JPanel();
		councils.setBounds(0, 0, sectionDimension.width, sectionDimension.height);
		contentPane.add(councils);
		councils.setLayout(null);
		this.createCouncilPanel(councils, new Point(sectionDimension.width*54/1000, sectionDimension.height*146/1000), "land");
		this.createCouncilPanel(councils, new Point(sectionDimension.width*385/1000, sectionDimension.height*146/1000), "hill");
		this.createCouncilPanel(councils, new Point(sectionDimension.width*745/1000, sectionDimension.height*146/1000), "mountain");
		
		JPanel permits = new JPanel();
		permits.setBounds(0, sectionDimension.height, sectionDimension.width, sectionDimension.height);
		contentPane.add(permits);
		permits.setLayout(null);
		this.createPermitsPanel(permits, new Point(sectionDimension.width*54/1000, sectionDimension.height*146/1000), "land");
		this.createPermitsPanel(permits, new Point(sectionDimension.width*385/1000, sectionDimension.height*146/1000), "hill");
		this.createPermitsPanel(permits, new Point(sectionDimension.width*745/1000, sectionDimension.height*146/1000), "mountain");
		
		JPanel cards = new JPanel();
		cards.setBounds(0, 2*sectionDimension.height, sectionDimension.width, sectionDimension.height);
		contentPane.add(cards);
		cards.setLayout(null);
	}
	
	private void createPermitsPanel(JPanel permits, Point point, String string) {
		Dimension faceUpDimension = new Dimension(permitsDimension.width/2, permitsDimension.height);
		JPanel regionalPermits = new JPanel();
		regionalPermits.setSize(permitsDimension);
		regionalPermits.setLocation(point);
		regionalPermits.setLayout(null);
		regionalPermits.setVisible(true);
		regionalPermits.setName(string+"Permit");
		regionalPermits.setBorder(new LineBorder(Color.BLACK));
		permits.add(regionalPermits);
		
		Iterator<BuildingPermit> faceUps = game.getRegions().stream().filter(e->e.getName().equals(string))
											.findFirst().get().getPermitsDeck().getFaceUpPermits().iterator();
		
		JPanel faceUp2 = new ImagePanel(faceUps.next().getImagePath(), faceUpDimension);
		faceUp2.setVisible(true);
		faceUp2.setName("faceUp2"+string);
		faceUp2.setLocation(faceUpDimension.width, 0);
		faceUp2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for(Component c:contentPane.getComponents()){
					if(c.getName()!=null&&c.getName().contains("Permit"))
						for(Component c2:((JPanel)c).getComponents()){
							((JPanel)c2).setBorder(new LineBorder(Color.black,1));
						}
				}
				for(Component c:contentPane.getComponents())
					if(c.getName().equalsIgnoreCase("faceup2"+string))
						((JPanel)c).setBorder(new LineBorder(Color.yellow,3));
				permitSelected = game.getRegions().stream().filter(t->t.getName().equals(string))
							.findFirst().get().getPermitsDeck().getFaceUpPermits().iterator().next();
			}
		});
		
		JPanel faceUp1 = new ImagePanel(faceUps.next().getImagePath(), faceUpDimension);
		faceUp1.setVisible(true);
		faceUp1.setName("faceUp1"+string);
		faceUp1.setLocation(0,0);
		faceUp1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for(Component c:contentPane.getComponents()){
					if(c.getName()!=null&&c.getName().contains("Permit"))
						for(Component c2:((JPanel)c).getComponents()){
							((JPanel)c2).setBorder(new LineBorder(Color.black,1));
						}
				}
				for(Component c:contentPane.getComponents())
					if(c.getName().equalsIgnoreCase("faceUp1"+string))
						((JPanel)c).setBorder(new LineBorder(Color.yellow,3));
				Iterator<BuildingPermit> tmp =game.getRegions().stream().filter(t->t.getName().equals(string))
							.findFirst().get().getPermitsDeck().getFaceUpPermits().iterator();
				tmp.next();
				permitSelected = tmp.next();
			}
		});
		
		permits.add(faceUp1);
		permits.add(faceUp2);
		
	}

	private void createCouncilPanel(JPanel panel, Point p, String name) {
		JPanel council = new JPanel();
		council.setSize(councilDimension);
		council.setLocation(p);
		council.setLayout(null);
		council.setVisible(true);
		council.setName(name+"Council");
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
