package client.GUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Toolkit;
import javax.swing.border.LineBorder;
import client.ClientViewInterface;
import client.XMLReaderForClient;
import model.game.BuildingPermit;
import model.game.Game;
import model.game.Player;
import model.game.topology.City;
import model.game.topology.Region;
import view.LocalStorage;
import view.Request;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import org.jdom2.JDOMException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.border.BevelBorder;

public class GUI extends JFrame implements ClientViewInterface {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5547965368598720974L;
	private Game game;
	private LocalStorage memoryContainer;
	private int ID;
	
	
	private JPanel contentPane;
	
	private String pathland="src/main/resources/Immagini/mareAridim.jpg";
	private String pathhill="src/main/resources/Immagini/collinaAridim.jpg";
	private String pathmountain="src/main/resources/Immagini/montagnaAridim.jpg";
	private final static String pathNobility="src/main/resources/Immagini/nobility.jpg";
	
	private final String pathAction="src/main/resources/Immagini/Rules.jpg";
	
	private final String pathSeasideDeck="src/main/resources/Immagini/seaside_deck.jpg";
	private final String pathHillDeck="src/main/resources/Immagini/hill_deck.jpg";
	private final String pathMountainDeck="src/main/resources/Immagini/mountain_deck.jpg";
	
	private final String pathSeasideTile="src/main/resources/Immagini/region_tile_land.jpg";
	private final String pathHillTile="src/main/resources/Immagini/region_tile_hill.jpg";
	private final String pathMountainTile="src/main/resources/Immagini/region_tile_mountain.jpg";
	
	private final String pathBlueTile="src/main/resources/Immagini/color_tile_blue.jpg";
	private final String pathOrangeTile="src/main/resources/Immagini/color_tile_orange.jpg";
	private final String pathGreyTile="src/main/resources/Immagini/color_tile_grey.jpg";
	private final String pathYellowTile="src/main/resources/Immagini/color_tile_yellow.jpg";
	private final String pathKingTile="src/main/resources/Immagini/king_tile_";
	
	Dimension monitorDimension=Toolkit.getDefaultToolkit().getScreenSize();
	Dimension cardBoardDimension=new Dimension((monitorDimension.width/160*105), (monitorDimension.height));
	Dimension regionPanelDimension=new Dimension(cardBoardDimension.width, cardBoardDimension.height/90*53);
	Dimension singleRegionDimension=new Dimension(regionPanelDimension.width/3, regionPanelDimension.height);
	Dimension nobilityPanelDimension=new Dimension(cardBoardDimension.width, cardBoardDimension.height/90*35);
	Dimension permitsDeckDimension=new Dimension((nobilityPanelDimension.width*7/100), (nobilityPanelDimension.height*22/100));
	Dimension rightPanelDimension=new Dimension((int) (monitorDimension.width-cardBoardDimension.getWidth()), monitorDimension.height);
	Dimension colorTileDimension = new Dimension((nobilityPanelDimension.width*7/100), (nobilityPanelDimension.height*20/100));
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	public GUI()
	{
			
		//setAlwaysOnTop(true);
		
		this.setSize(monitorDimension);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setMinimumSize(monitorDimension);
		contentPane.setBorder(new LineBorder(Color.BLACK));
		contentPane.setSize(monitorDimension);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel cardBoard = new JPanel();
		//cardBoard.setLocation(1, 1);
		cardBoard.setSize(cardBoardDimension);
		cardBoard.setBorder(new LineBorder(Color.BLACK));
		contentPane.add(cardBoard);
		cardBoard.setLayout(null);
		cardBoard.setBounds(0, 0, cardBoardDimension.width, cardBoardDimension.height);
		
		JPanel regions=new JPanel();
		regions.setName("regions");
		regions.setLocation(1, 1);
		regions.setSize(regionPanelDimension);
		regions.setAutoscrolls(true);
		cardBoard.add(regions);
		regions.setBounds(0, 0, regionPanelDimension.width, regionPanelDimension.height);
		regions.setLayout(null);
		/*
		ImagePanel seaside=new ImagePanel(pathland, singleRegionDimension);
		//seaside.setLocation(0, 0);
		seaside.setSize(singleRegionDimension);
		regions.add(seaside);
		seaside.setLayout(null);
		seaside.setBounds(0, 0, singleRegionDimension.width, singleRegionDimension.height);
		
		
		
		ImagePanel hill=new ImagePanel(pathhill, singleRegionDimension);
		//hill.setLocation(448, 0);
		hill.setSize(singleRegionDimension);
		regions.add(hill);
		hill.setLayout(null);
		hill.setBounds(singleRegionDimension.width, 0, singleRegionDimension.width, singleRegionDimension.height);
		
		
		
		ImagePanel mountain=new ImagePanel(pathmountain, singleRegionDimension);
		//mountain.setLocation(896, 0);
		mountain.setSize(singleRegionDimension);
		regions.add(mountain);
		mountain.setLayout(null);
		mountain.setBounds(2*singleRegionDimension.width, 0, singleRegionDimension.width, singleRegionDimension.height);
		
		
		*/
		
		ImagePanel nobility = new ImagePanel(pathNobility, nobilityPanelDimension);
		//nobility.setLocation(1, 368);
		nobility.setSize(nobilityPanelDimension);
		cardBoard.add(nobility);
		nobility.setLayout(null);
		nobility.setBounds(0, singleRegionDimension.height, nobilityPanelDimension.width, nobilityPanelDimension.height);
				
		ImagePanel seasideDeck = new ImagePanel(pathSeasideDeck, permitsDeckDimension);
		seasideDeck.setSize(permitsDeckDimension);
		seasideDeck.setLocation(nobilityPanelDimension.width*65/1000, nobilityPanelDimension.height*39/1000);
		nobility.add(seasideDeck);
		
		ImagePanel hillDeck = new ImagePanel(pathHillDeck, permitsDeckDimension);
		hillDeck.setSize(permitsDeckDimension);
		hillDeck.setLocation(nobilityPanelDimension.width*368/1000, nobilityPanelDimension.height*42/1000);
		nobility.add(hillDeck);
		
		ImagePanel mountainDeck = new ImagePanel(pathMountainDeck, permitsDeckDimension);
		mountainDeck.setSize(permitsDeckDimension);
		mountainDeck.setLocation(nobilityPanelDimension.width*70/100, nobilityPanelDimension.height*43/1000);
		nobility.add(mountainDeck);
		
		ImagePanel blueTile = new ImagePanel(pathBlueTile, colorTileDimension);
		blueTile.setOpaque(false);	
		blueTile.setSize(colorTileDimension);
		blueTile.setLocation(nobilityPanelDimension.width*739/1000, nobilityPanelDimension.height*660/1000);
		nobility.add(blueTile);
		
		ImagePanel orangeTile = new ImagePanel(pathOrangeTile, colorTileDimension);
		orangeTile.setOpaque(false);
		orangeTile.setSize(colorTileDimension);
		orangeTile.setLocation(nobilityPanelDimension.width*790/1000, nobilityPanelDimension.height*650/1000);
		nobility.add(orangeTile);
		
		ImagePanel greyTile = new ImagePanel(pathGreyTile, colorTileDimension);
		greyTile.setOpaque(false);
		greyTile.setSize(colorTileDimension);
		greyTile.setLocation(nobilityPanelDimension.width*837/1000, nobilityPanelDimension.height*628/1000);
		nobility.add(greyTile);
		
		ImagePanel yellowTile = new ImagePanel(pathYellowTile, colorTileDimension);
		yellowTile.setOpaque(false);
		yellowTile.setSize(colorTileDimension);
		yellowTile.setLocation(nobilityPanelDimension.width*886/1000, nobilityPanelDimension.height*611/1000);
		nobility.add(yellowTile);
		
		ImagePanel kingTile = new ImagePanel(pathKingTile.concat("1.jpg"), colorTileDimension);
		kingTile.setOpaque(false);
		kingTile.setSize(colorTileDimension);
		kingTile.setLocation(nobilityPanelDimension.width*874/1000, nobilityPanelDimension.height*421/1000);
		nobility.add(kingTile);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setSize(rightPanelDimension);
		tabbedPane.setLocation(cardBoard.getWidth(), 0);
		contentPane.add(tabbedPane);
		
		JPanel currentPlayer = new JPanel();
		tabbedPane.addTab("Player", null, currentPlayer, null);
		currentPlayer.setLayout(null);
		
		JTextPane playerName = new JTextPane();
		playerName.setOpaque(false);
		playerName.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		playerName.setEditable(false);
		playerName.setText("currentPlayerName");
		playerName.setBounds(66, 11, 114, 20);
		currentPlayer.add(playerName);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setLabelFor(playerName);
		lblName.setBounds(10, 11, 46, 14);
		currentPlayer.add(lblName);
		
		JTextPane txtpnVps = new JTextPane();
		txtpnVps.setOpaque(false);
		txtpnVps.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txtpnVps.setEditable(false);
		txtpnVps.setName("txtpnVps\r\n");
		txtpnVps.setText("VPs");
		txtpnVps.setBounds(161, 49, 57, 20);
		currentPlayer.add(txtpnVps);
		
		JLabel victoryPoints = new JLabel("Victory Points:\r\n");
		victoryPoints.setLabelFor(txtpnVps);
		victoryPoints.setBounds(10, 55, 114, 14);
		currentPlayer.add(victoryPoints);
		
		JTextPane txtpnNps = new JTextPane();
		txtpnNps.setOpaque(false);
		txtpnNps.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txtpnNps.setEditable(false);
		txtpnNps.setText("NPs");
		txtpnNps.setBounds(161, 90, 57, 20);
		currentPlayer.add(txtpnNps);
		
		JLabel nobilityPoints = new JLabel("Nobility Points:");
		nobilityPoints.setLabelFor(txtpnNps);
		nobilityPoints.setBounds(10, 90, 114, 14);
		currentPlayer.add(nobilityPoints);
		
		JLabel lblCoins = new JLabel("Coins:");
		lblCoins.setBounds(275, 55, 72, 14);
		currentPlayer.add(lblCoins);
		
		JTextPane txtpnCoins = new JTextPane();
		txtpnCoins.setOpaque(false);
		txtpnCoins.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txtpnCoins.setText("Coins");
		txtpnCoins.setEditable(false);
		txtpnCoins.setBounds(399, 49, 57, 20);
		currentPlayer.add(txtpnCoins);
		
		JLabel lblAssistants = new JLabel("Assistants:");
		lblAssistants.setBounds(275, 90, 114, 14);
		currentPlayer.add(lblAssistants);
		
		JTextPane txtpnAssistants = new JTextPane();
		txtpnAssistants.setOpaque(false);
		txtpnAssistants.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		lblAssistants.setLabelFor(txtpnAssistants);
		txtpnAssistants.setText("assistants");
		txtpnAssistants.setName("txtpnVps\r\n");
		txtpnAssistants.setEditable(false);
		txtpnAssistants.setBounds(399, 90, 57, 20);
		currentPlayer.add(txtpnAssistants);
		
		JLabel lblRemainingEmporiums = new JLabel("Remaining Emporiums:");
		lblRemainingEmporiums.setBounds(10, 132, 141, 14);
		currentPlayer.add(lblRemainingEmporiums);
		
		JTextPane txtpnRemainingEmporiums = new JTextPane();
		txtpnRemainingEmporiums.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txtpnRemainingEmporiums.setOpaque(false);
		lblRemainingEmporiums.setLabelFor(txtpnRemainingEmporiums);
		txtpnRemainingEmporiums.setText("Emporiums Left");
		txtpnRemainingEmporiums.setEditable(false);
		txtpnRemainingEmporiums.setBounds(161, 132, 57, 20);
		currentPlayer.add(txtpnRemainingEmporiums);
		
		JLabel lblEmporiums = new JLabel("Emporiums on:");
		lblEmporiums.setBounds(10, 175, 114, 14);
		currentPlayer.add(lblEmporiums);
		
		JScrollPane scrollPane = new JScrollPane();
		lblEmporiums.setLabelFor(scrollPane);
		scrollPane.setBounds(130, 175, 360, 82);
		currentPlayer.add(scrollPane);
		
		JTextArea txtrCiaoSonoFrancesco = new JTextArea();
		scrollPane.setViewportView(txtrCiaoSonoFrancesco);
		txtrCiaoSonoFrancesco.setEditable(false);
		
		JPanel colorPlayer = new JPanel();
		colorPlayer.setName("colorPlayer");
		colorPlayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		colorPlayer.setBounds(tabbedPane.getWidth()/2, 0, 56, 51);
		currentPlayer.add(colorPlayer);
				
		JPanel Game = new JPanel();
		tabbedPane.addTab("Game", null, Game, null);
		Game.setLayout(null);
			
		ImagePanel actions = new ImagePanel(pathAction, new Dimension(510, 281));
		Game.add(actions);
		actions.setBounds(0, 395, 510, 281);
		actions.setMinimumSize(new Dimension(510, 281));
		actions.setPreferredSize(new Dimension(510, 281));
		actions.setLayout(null);
		
		JButton acquirePermit = new JButton("");
		acquirePermit.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		acquirePermit.setBackground(SystemColor.control);
		acquirePermit.setContentAreaFilled(false);
		acquirePermit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "acquirePermit");
			}
		});
		acquirePermit.setBounds(10, 66, 245, 57);
		actions.add(acquirePermit);
		
		JButton buildEmporiumByKing = new JButton("");
		buildEmporiumByKing.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		buildEmporiumByKing.setBackground(SystemColor.control);
		buildEmporiumByKing.setContentAreaFilled(false);
		buildEmporiumByKing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "buildEmporiumByKing");
			}
		});
		buildEmporiumByKing.setBounds(10, 122, 245, 57);
		actions.add(buildEmporiumByKing);
		
		JButton electCouncillor = new JButton("");
		electCouncillor.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		electCouncillor.setBackground(SystemColor.control);
		electCouncillor.setContentAreaFilled(false);
		electCouncillor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "electCouncillor");
			}
		});
		electCouncillor.setBounds(10, 180, 245, 47);
		actions.add(electCouncillor);
		
		JButton buildEmporiumByPermit = new JButton("");
		buildEmporiumByPermit.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		buildEmporiumByPermit.setBackground(SystemColor.control);
		buildEmporiumByPermit.setContentAreaFilled(false);
		buildEmporiumByPermit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "buildEmporiumByPermit");
			}
		});
		buildEmporiumByPermit.setBounds(10, 231, 245, 47);
		actions.add(buildEmporiumByPermit);
		
		JButton engageAssistant = new JButton("");
		engageAssistant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "engageAssistant");
			}
		});
		engageAssistant.setContentAreaFilled(false);
		engageAssistant.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		engageAssistant.setBackground(SystemColor.menu);
		engageAssistant.setBounds(265, 66, 245, 57);
		actions.add(engageAssistant);
		
		JButton changeFaceUpPermits = new JButton("");
		changeFaceUpPermits.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "changeFaceUpPermits");
			}
		});
		changeFaceUpPermits.setContentAreaFilled(false);
		changeFaceUpPermits.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		changeFaceUpPermits.setBackground(SystemColor.menu);
		changeFaceUpPermits.setBounds(265, 122, 245, 57);
		actions.add(changeFaceUpPermits);
		
		JButton electCouncillorByAssistant = new JButton("");
		electCouncillorByAssistant.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "electCouncillorByAssistant");
			}
		});
		electCouncillorByAssistant.setContentAreaFilled(false);
		electCouncillorByAssistant.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		electCouncillorByAssistant.setBackground(SystemColor.menu);
		electCouncillorByAssistant.setBounds(265, 180, 245, 47);
		actions.add(electCouncillorByAssistant);
		
		JButton extraMainAction = new JButton("");
		extraMainAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "extraMainAction");
			}
		});
		extraMainAction.setContentAreaFilled(false);
		extraMainAction.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		extraMainAction.setBackground(SystemColor.menu);
		extraMainAction.setBounds(265, 231, 245, 47);
		actions.add(extraMainAction);
		
		JTextArea chatOutputMessges = new JTextArea();
		chatOutputMessges.setText("chat messages zone");
		chatOutputMessges.setBounds(0, 0, 506, 362);
		Game.add(chatOutputMessges);
		
		JTextField chatInputMessage = new JTextField();
		chatInputMessage.setText("chat input message");
		chatInputMessage.setBounds(0, 362, 426, 33);
		Game.add(chatInputMessage);
		chatInputMessage.setColumns(10);
		
		JButton submitChat = new JButton("Submit");
		submitChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		submitChat.setBounds(428, 362, 80, 33);
		Game.add(submitChat);
		
		//setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	
	/*public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 925, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel map = new JPanel();
		contentPane.add(map);
		map.setLayout(new BoxLayout(map, BoxLayout.X_AXIS));
		ImagePanel land=new ImagePanel(pathLand);
		land.setAutoscrolls(true);
		//JLabel land = new JLabel("REGIONE");
		map.add(land);
	
		JPanel hill=new ImagePanel(pathHil);
		map.add(hill);
		
		JPanel mountain=new ImagePanel(pathMountain);
		map.add(mountain);
		
		JPanel nobility = new JPanel();
		contentPane.add(nobility);
		nobility.setLayout(new BoxLayout(nobility, BoxLayout.X_AXIS));
		
		JPanel nobility1 =new ImagePanelNobility(pathNobility1);
		nobility.add(nobility1);
		nobility1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JPanel nobility2 =new ImagePanelNobility(pathNobility2);
		nobility.add(nobility2);
		nobility2.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
	}*/
	
	public BufferedImage getImage(String path) throws IOException{
		File file=new File(path);
		return ImageIO.read(file);
	}
	
	public void setRegionsBackground(){
		Set<Region> regionSet=this.game.getRegions();
		this.pathhill=regionSet.stream().filter(e->e.getName().equals("hill")).map(e->e.getImagePath()).findFirst().get();
		this.pathmountain=regionSet.stream().filter(e->e.getName().equals("mountain")).map(e->e.getImagePath()).findFirst().get();
		this.pathland=regionSet.stream().filter(e->e.getName().equals("land")).map(e->e.getImagePath()).findFirst().get();
		
		JPanel regions=(JPanel)this.contentPane.getComponents()[0].getComponentAt(0, 0);
		//regions.removeAll();
		ImagePanel seaside=new ImagePanel(pathland, singleRegionDimension);
		//seaside.setLocation(0, 0);
		seaside.setSize(singleRegionDimension);
		seaside.setName("land");
		regions.add(seaside);
		seaside.setLayout(null);
		seaside.setBounds(0, 0, singleRegionDimension.width, singleRegionDimension.height);
		
		ImagePanel seasideTile = new ImagePanel(pathSeasideTile, new Dimension(51, 30));
		seasideTile.setOpaque(false);
		seasideTile.setBounds(211, 383, 51, 30);
		seaside.add(seasideTile);
		
		ImagePanel hill=new ImagePanel(pathhill, singleRegionDimension);
		//hill.setLocation(448, 0);
		hill.setSize(singleRegionDimension);
		hill.setName("hill");
		regions.add(hill);
		hill.setLayout(null);
		hill.setBounds(singleRegionDimension.width, 0, singleRegionDimension.width, singleRegionDimension.height);
		
		ImagePanel hillTile = new ImagePanel(pathHillTile, new Dimension(51, 27));
		hillTile.setOpaque(false);
		hillTile.setBounds(184, 383, 51, 27);
		hill.add(hillTile);
		
		ImagePanel mountain=new ImagePanel(pathmountain, singleRegionDimension);
		//mountain.setLocation(896, 0);
		mountain.setSize(singleRegionDimension);
		mountain.setName("mountain");
		regions.add(mountain);
		mountain.setLayout(null);
		mountain.setBounds(2*singleRegionDimension.width, 0, singleRegionDimension.width, singleRegionDimension.height);
		
		ImagePanel mountainTile = new ImagePanel(pathMountainTile, new Dimension(51, 30));
		mountainTile.setOpaque(false);
		mountainTile.setBounds(183, 383, 51, 30);
		mountain.add(mountainTile);
				
	}
	
	public void cityBonusLoader() throws JDOMException, IOException{
		XMLReaderForClient reader=new XMLReaderForClient();
		Map<Character,City> bonuses=new HashMap<>();
		for(City c:this.game.getAllCities()){
			bonuses.put(c.getFirstChar(), c);
		}
		
		JPanel regions=(JPanel)this.contentPane.getComponents()[0].getComponentAt(0, 0);
		reader.createCitiesFromRegionPanel(regions, bonuses, cardBoardDimension);	
		
		
	}

	@Override
	public Request start() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateModel(Game game) {
		this.game=game;
		if(this.ID==0 || this.game.getPlayerByID(ID) == null)
			return;
		
		Player player = this.game.getPlayerByID(ID);
		JTabbedPane x = (JTabbedPane)this.contentPane.getComponents()[1];
		JPanel playerTab=(JPanel)x.getComponent(0);
		
		JPanel colorPlayer=(JPanel)(Arrays.asList(playerTab.getComponents()).stream().filter(e->e.getName()!=null&&e.getName().equals("colorPlayer")).findFirst().get());
		colorPlayer.setBackground(player.getChosenColor());
		
		JTextPane t = (JTextPane)x.getComponentAt(0).getComponentAt(66, 11);
		t.setText(player.getName());
		
		t = (JTextPane)x.getComponentAt(0).getComponentAt(161, 49);
		t.setText(Integer.toString(player.getPoints()));
		
		t = (JTextPane)x.getComponentAt(0).getComponentAt(399, 49);
		t.setText(Integer.toString(player.getCoins()));
		
		t = (JTextPane)x.getComponentAt(0).getComponentAt(161, 90);
		t.setText(Integer.toString(player.getNobilityPoints()));
		
		t = (JTextPane)x.getComponentAt(0).getComponentAt(399, 90);
		t.setText(Integer.toString(player.getAssistants()));
		
		t = (JTextPane)x.getComponentAt(0).getComponentAt(161, 132);
		t.setText(Integer.toString(player.getRemainingEmporiums()));
		
		JScrollPane s = (JScrollPane)x.getComponentAt(0).getComponentAt(130, 175);
		JTextArea a = (JTextArea)s.getComponent(0).getComponentAt(0, 0);
		if(!player.getEmporium().isEmpty()){
			String support = "";
			player.getEmporium().stream().map(e -> support.concat(e.getCity().getName()+"\r\n"));
			a.setText(support);
		}
		else
			a.setText("No emporiums built yet");
		
		JPanel p = (JPanel)this.contentPane.getComponent(0);
		ImagePanel i = (ImagePanel)p.getComponentAt(1, 425);
		
		Iterator<Region> it = this.game.getRegions().iterator();
		Region r = null;
		while(it.hasNext()){
			r = it.next();
			if("land".equals(r.getName()))
				break;
		}
				
		Iterator<BuildingPermit> it2 = r.getPermitsDeck().getFaceUpPermits().iterator();
		
		ImagePanel seasideFaceupPermit1 = new ImagePanel(it2.next().getImagePath(), permitsDeckDimension);
		seasideFaceupPermit1.setSize(permitsDeckDimension);
		seasideFaceupPermit1.setBounds(120, 11, 53, 62);
		i.add(seasideFaceupPermit1);
		
		ImagePanel seasideFaceupPermit2 = new ImagePanel(it2.next().getImagePath(), permitsDeckDimension);
		seasideFaceupPermit2.setSize(permitsDeckDimension);
		seasideFaceupPermit2.setBounds(183, 11, 53, 62);
		i.add(seasideFaceupPermit2);

		i = (ImagePanel)i.getComponentAt(764, 146);
		if(!this.game.getKingTileList().isEmpty())
			i = new ImagePanel(pathKingTile+
					Integer.toString(5-this.game.getKingTileList().size()+1)+".jpg", 
					new Dimension(60, 56));
		
		
		
		System.out.println("changed");
	}

	@Override
	public int getId() {
		return this.ID;
	}

	@Override
	public void setId(int id) {
		this.ID=id;
	}

	@Override
	public void stampMessage(String message) {
		if(!message.equals(""))
			JOptionPane.showMessageDialog(null, message);
	}

	@Override
	public void setGame(Game game) {
		this.game=game;
	}

	@Override
	public void setMemoryContainer(LocalStorage memoryLocator) {
		this.memoryContainer=memoryLocator;
	}
}
