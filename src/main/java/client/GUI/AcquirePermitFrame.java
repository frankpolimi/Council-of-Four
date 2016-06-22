package client.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import model.actions.AcquirePermit;
import model.actions.ElectCouncillor;
import model.game.BuildingPermit;
import model.game.Game;
import model.game.Player;
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

public class AcquirePermitFrame extends JFrame {

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game game=new Game();
					List<Player> players=new ArrayList<>();
					players.add(new Player("ema",1));
					game.setPlayers(players);
					GUI gui=new GUI();
					gui.setId(1);
					gui.setGame(game);
					AcquirePermitFrame frame=new AcquirePermitFrame(game, gui);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 660009630551746911L;
	
	private JPanel contentPane;
	private Dimension monitorDimension=Toolkit.getDefaultToolkit().getScreenSize();
	private Dimension frameDimension=new Dimension(monitorDimension.width/2, monitorDimension.height/2); 
	private Dimension sectionDimension = new Dimension(frameDimension.width*987/1000, frameDimension.height*26/100);
	private Dimension councilDimension = new Dimension(sectionDimension.width*154/1000, sectionDimension.height);
	private Dimension permitsDimension = new Dimension(sectionDimension.width/4, sectionDimension.height);
	
	private RegionalCouncil councilSelected;
	private BuildingPermit permitSelected;
	private ArrayList<PoliticsCard> cardsSelected;
	private Game game;
	private GUI gui;
	
	public AcquirePermitFrame(Game game, GUI gui) {
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
		btnSend.setSize(frameDimension.width*100/1000, frameDimension.height*700/10000);
		btnSend.setLocation(frameDimension.width*82/100, frameDimension.height*800/1000);
		contentPane.add(btnSend);
		
		String region=game.getRegions().stream().filter(e->e.getName().equalsIgnoreCase("land")).findFirst().get().getName();
		this.createCouncilPanel(contentPane, new Point(sectionDimension.width*54/1000, sectionDimension.height*146/1000), region);
		this.createPermitsPanel(contentPane, new Point(sectionDimension.width*54/1000, sectionDimension.height+sectionDimension.height*146/1000), region);
		region=game.getRegions().stream().filter(e->e.getName().equalsIgnoreCase("hill")).findFirst().get().getName();;
		this.createCouncilPanel(contentPane, new Point(sectionDimension.width*385/1000, sectionDimension.height*146/1000), region);
		this.createPermitsPanel(contentPane, new Point(sectionDimension.width*385/1000,sectionDimension.height+sectionDimension.height*146/1000), region);
		region=game.getRegions().stream().filter(e->e.getName().equalsIgnoreCase("mountain")).findFirst().get().getName();
		this.createCouncilPanel(contentPane, new Point(sectionDimension.width*745/1000, sectionDimension.height*146/1000), region);
		this.createPermitsPanel(contentPane, new Point(sectionDimension.width*745/1000,sectionDimension.height+sectionDimension.height*146/1000), region);
		
		this.createCardPanel(contentPane, new Point(sectionDimension.width*54/1000, 2*sectionDimension.height+sectionDimension.height*146/1000));
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
		faceUp2.setSize(faceUpDimension);
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
				for(Component c:regionalPermits.getComponents())
					if(c.getName()!=null&&c.getName().equalsIgnoreCase("faceup2"+string))
						((JPanel)c).setBorder(new LineBorder(Color.yellow,3));
				permitSelected = game.getRegions().stream().filter(t->t.getName().equals(string))
							.findFirst().get().getPermitsDeck().getFaceUpPermits().iterator().next();
			}
		});
		
		JPanel faceUp1 = new ImagePanel(faceUps.next().getImagePath(), faceUpDimension);
		faceUp1.setVisible(true);
		faceUp1.setName("faceUp1"+string);
		faceUp1.setSize(faceUpDimension);
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
				for(Component c:regionalPermits.getComponents())
					if(c.getName()!=null&&c.getName().equalsIgnoreCase("faceup1"+string))
						((JPanel)c).setBorder(new LineBorder(Color.yellow,3));
				
				Iterator<BuildingPermit> tmp =game.getRegions().stream().filter(t->t.getName().equals(string))
							.findFirst().get().getPermitsDeck().getFaceUpPermits().iterator();
				tmp.next();
				permitSelected = tmp.next();
			}
		});
		
		regionalPermits.add(faceUp1);
		regionalPermits.add(faceUp2);	
	}

	private void createCouncilPanel(JPanel panel, Point p, String name) {
		JPanel council = new JPanel();
		council.setSize(councilDimension);
		council.setLocation(p);
		council.setLayout(null);
		council.setVisible(true);
		council.setName(name+"Council");
		council.setBorder(new LineBorder(Color.BLACK));
		this.paintCouncil(council, councilDimension);
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
				councilSelected=game.getRegions().stream().filter(t->t.getName().contains(name)).findFirst().get().getCouncil();
			}
		});
	}
	
	private void paintCouncil(JPanel council, Dimension councilDimension) {
		Dimension councillorDimension =  new Dimension(councilDimension.width/4, councilDimension.height);
		council.removeAll();
		Region r = null;
		Iterator<Councillor> gameCouncillor = null;
		switch(council.getName()){
			case "landCouncil":{
				r = game.getRegions().stream().filter(e->e.getName().equals("land")).findFirst().get();
				gameCouncillor = r.getCouncil().getCouncillors().iterator();
				break;
			}
			case "hillCouncil":{
				r = game.getRegions().stream().filter(e->e.getName().equals("hill")).findFirst().get();
				gameCouncillor = r.getCouncil().getCouncillors().iterator();
				break;
			}
			case "mountainCouncil":{
				r = game.getRegions().stream().filter(e->e.getName().equals("mountain")).findFirst().get();
				gameCouncillor = r.getCouncil().getCouncillors().iterator();
				break;
			}
			default:{
				gameCouncillor = game.getKingsCouncil().getCouncillors().iterator();
			}
		}

		JPanel councillor1 = new ImagePanel(gameCouncillor.next().getImagePath(), councillorDimension);
		councillor1.setSize(councillorDimension);
		councillor1.setLocation(councilDimension.width*3/4, 0);
		councillor1.setOpaque(false);
		council.add(councillor1);
		JPanel councillor2 = new ImagePanel(gameCouncillor.next().getImagePath(), councillorDimension);
		councillor2.setSize(councillorDimension);
		councillor2.setLocation(councilDimension.width/2, 0);
		councillor2.setOpaque(false);
		council.add(councillor2);
		JPanel councillor3 = new ImagePanel(gameCouncillor.next().getImagePath(), councillorDimension);
		councillor3.setSize(councillorDimension);
		councillor3.setLocation(councilDimension.width/4, 0);
		councillor3.setOpaque(false);
		council.add(councillor3);
		JPanel councillor4 = new ImagePanel(gameCouncillor.next().getImagePath(), councillorDimension);
		councillor4.setSize(councillorDimension);
		councillor4.setLocation(0,0);
		councillor4.setOpaque(false);
		council.add(councillor4);
	}
	
	private void createCardPanel(JPanel zone, Point point){
		
		List<PoliticsCard> cardList=game.getPlayerByID(gui.getId()).getCardsOwned();
		JPanel cardsPanel = new JPanel();
		cardsPanel.setLayout(new BoxLayout(cardsPanel,BoxLayout.X_AXIS));
		cardsPanel.setVisible(true);
		cardsPanel.setSize(sectionDimension.width*78/100, sectionDimension.height);
		Dimension cardDimension = new Dimension(cardsPanel.getWidth()/15, cardsPanel.getHeight());
		cardsPanel.setLocation(point);
		zone.add(cardsPanel);
		for(PoliticsCard card:cardList){
			JLabel cardImage=new ImageLabel(card.getImagePath(),cardDimension);
			cardImage.setSize(cardDimension);
			cardImage.setVisible(true);
			cardsPanel.add(cardImage);
			cardsPanel.add(Box.createHorizontalStrut(5));
			cardImage.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					if(cardImage.getBorder()==null){
						if(cardsSelected.size()<4){
							cardImage.setBorder(new LineBorder(Color.yellow,2));
							cardsSelected.add(card);
						}
					}
					else{
						cardImage.setBorder(null);
						cardsSelected.remove(card);
					}
				}
			});
		}
	}
}
