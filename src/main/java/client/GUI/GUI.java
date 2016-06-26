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
import model.actions.EngageAssistant;
import model.actions.ExtraMainAction;
import model.actions.SkipAction;
import model.game.BuildingPermit;
import model.game.Game;
import model.game.Player;
import model.game.council.Councillor;
import model.game.topology.City;
import model.game.topology.Region;
import view.ActionRequest;
import view.LocalStorage;
import view.MarketSellingState;
import view.Request;
import view.StartState;
import java.awt.Color;
import java.awt.Component;
import javax.swing.border.MatteBorder;
import org.jdom2.JDOMException;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
	private Request request=null;
	private GUI thisObj=this;
	
	private JPanel contentPane;
	
	private String pathland="src/main/resources/Immagini/mareA.jpg";
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
	
	public static final Dimension monitorDimension=Toolkit.getDefaultToolkit().getScreenSize();
	public static final Dimension cardBoardDimension=new Dimension((monitorDimension.width/160*105), (monitorDimension.height));
	public static final Dimension regionPanelDimension=new Dimension(cardBoardDimension.width, cardBoardDimension.height/90*53);
	public static final Dimension singleRegionDimension=new Dimension(regionPanelDimension.width/3, regionPanelDimension.height);
	public static final Dimension nobilityPanelDimension=new Dimension(cardBoardDimension.width, cardBoardDimension.height/90*35);
	public static final Dimension permitsDeckDimension=new Dimension((nobilityPanelDimension.width*7/100), (nobilityPanelDimension.height*22/100));
	public static final Dimension rightPanelDimension=new Dimension((int) (monitorDimension.width-cardBoardDimension.getWidth()), monitorDimension.height);
	public static final Dimension colorTileDimension = new Dimension((nobilityPanelDimension.width*7/100), (nobilityPanelDimension.height*20/100));
	public static final Dimension actionDimension= new Dimension(rightPanelDimension.width,rightPanelDimension.width*766/1000);
	public static final Dimension actionButtonDimension= new Dimension(rightPanelDimension.width*48/100, rightPanelDimension.width*766/5000);
	public static final Dimension councilDimension = new Dimension(nobilityPanelDimension.width*109/1000, nobilityPanelDimension.height*875/10000);
	public static Dimension regionTileDimension = new Dimension(singleRegionDimension.width*182/1000, singleRegionDimension.height*71/1000);


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
		nobility.setName("nobility");
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
		
		JPanel seasideCouncil = new JPanel();
		seasideCouncil.setOpaque(false);
		seasideCouncil.setLayout(null);
		seasideCouncil.setName("landCouncil");
		seasideCouncil.setSize(councilDimension);
		seasideCouncil.setLocation(nobilityPanelDimension.width*136/1000, nobilityPanelDimension.height*35/100);
		nobility.add(seasideCouncil);
		
		JPanel hillCouncil = new JPanel();
		hillCouncil.setOpaque(false);
		hillCouncil.setLayout(null);
		hillCouncil.setName("hillCouncil");
		hillCouncil.setSize(councilDimension);
		hillCouncil.setLocation(nobilityPanelDimension.width*439/1000, nobilityPanelDimension.height*35/100);
		nobility.add(hillCouncil);
		
		JPanel mountainCouncil = new JPanel();
		mountainCouncil.setOpaque(false);
		mountainCouncil.setLayout(null);
		mountainCouncil.setName("mountainCouncil");
		mountainCouncil.setSize(councilDimension);
		mountainCouncil.setLocation(nobilityPanelDimension.width*773/1000, nobilityPanelDimension.height*35/100);
		nobility.add(mountainCouncil);
		
		JPanel kingCouncil = new JPanel();
		kingCouncil.setOpaque(false);
		kingCouncil.setLayout(null);
		kingCouncil.setName("king");
		kingCouncil.setSize(councilDimension);
		kingCouncil.setLocation(nobilityPanelDimension.width*630/1000, nobilityPanelDimension.height*457/1000);
		nobility.add(kingCouncil);
		
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
		playerName.setText("");
		playerName.setBounds(66, 11, 114, 20);
		playerName.setName("playerName");
		currentPlayer.add(playerName);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setLabelFor(playerName);
		lblName.setBounds(10, 11, 46, 14);
		currentPlayer.add(lblName);
		
		JTextPane txtpnVps = new JTextPane();
		txtpnVps.setOpaque(false);
		txtpnVps.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txtpnVps.setEditable(false);
		txtpnVps.setName("victory points");
		txtpnVps.setText("0");
		txtpnVps.setBounds(161, 49, 57, 20);
		currentPlayer.add(txtpnVps);
		
		JLabel victoryPoints = new JLabel("Victory Points:\r\n");
		victoryPoints.setLabelFor(txtpnVps);
		victoryPoints.setBounds(10, 55, 114, 14);
		currentPlayer.add(victoryPoints);
		
		JTextPane txtpnNps = new JTextPane();
		txtpnNps.setOpaque(false);
		txtpnNps.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txtpnNps.setName("nobility points");
		txtpnNps.setEditable(false);
		txtpnNps.setText("0");
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
		txtpnCoins.setText("0");
		txtpnCoins.setName("coins");
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
		txtpnAssistants.setText("0");
		txtpnAssistants.setName("assistants");
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
		txtpnRemainingEmporiums.setText("0");
		txtpnRemainingEmporiums.setName("remaining emporiums");
		txtpnRemainingEmporiums.setEditable(false);
		txtpnRemainingEmporiums.setBounds(161, 132, 57, 20);
		currentPlayer.add(txtpnRemainingEmporiums);
		
		JLabel lblEmporiums = new JLabel("Emporiums on:");
		lblEmporiums.setBounds(10, 175, 114, 14);
		currentPlayer.add(lblEmporiums);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setName("scroll pane");
		lblEmporiums.setLabelFor(scrollPane);
		scrollPane.setBounds(130, 175, 360, 82);
		currentPlayer.add(scrollPane);
		
		JTextArea txtrEmporiums = new JTextArea();
		txtrEmporiums.setName("emporiums");
		scrollPane.setViewportView(txtrEmporiums);
		txtrEmporiums.setEditable(false);
		
		JPanel colorPlayer = new JPanel();
		colorPlayer.setName("colorPlayer");
		colorPlayer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		colorPlayer.setBounds(tabbedPane.getWidth()/2, 0, 56, 51);
		currentPlayer.add(colorPlayer);
		
		JLabel turnIndicator=new JLabel("The game is not started yet");
		turnIndicator.setName("turnIndicator");
		turnIndicator.setBounds(colorPlayer.getX()+colorPlayer.getWidth()+20,0,150,38);
		turnIndicator.setBorder(new LineBorder(Color.BLACK));
		currentPlayer.add(turnIndicator);
		
		
		
		//ACTIONS		
		JPanel gamePanel = new JPanel();
		tabbedPane.addTab("Game", null, gamePanel, null);
		//gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
		gamePanel.setLayout(null);	
		
		JButton submitChat = new JButton("Submit");
		submitChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		gamePanel.add(submitChat);
		
		Double nobilityCellRelativeX=0.064209275;
		Double space=0.033333333;
		Dimension cellDim=new Dimension(12, 12);
		int cellYPosition=(int) (nobilityPanelDimension.getHeight()*705/1000);
		int dinstanceX=(int)(cardBoard.getWidth()*(nobilityCellRelativeX));
		for(int i=0;i<=20;i++){
			JPanel panel=new JPanel();
			panel.setName("nobilityPos"+i);
			panel.setBounds(dinstanceX, cellYPosition,cellDim.width, cellDim.height);
			dinstanceX+=space*cardBoard.getWidth();
			panel.setBackground(Color.white);
			nobility.add(panel);
		}
		
		JPanel otherPlayers = new JPanel();
		otherPlayers.setName("others");
		tabbedPane.addTab("Other Players", null, otherPlayers, null);
		otherPlayers.setLayout(null);
		
		JTable table = new JTable();
		table.setSize(rightPanelDimension);
		table.setLocation(0, 0);
		otherPlayers.add(table);
		
		JLabel otherName = new JLabel("Name");
		otherName.setBorder(new LineBorder(new Color(0, 0, 0)));
		otherName.setName("otherName");
		otherName.setBounds(0, 0, 75, 21);
		table.add(otherName);
		
		JLabel otherColor = new JLabel("Color");
		otherColor.setBorder(new LineBorder(new Color(0, 0, 0)));
		otherColor.setName("otherName");
		otherColor.setBounds(75, 0, 50, 21);
		table.add(otherColor);
		
		JLabel otherPoints = new JLabel("Victory Points");
		otherPoints.setBorder(new LineBorder(new Color(0, 0, 0)));
		otherPoints.setName("otherPoints");
		otherPoints.setBounds(125, 0, 75, 21);
		table.add(otherPoints);
		
		JLabel otherNobility = new JLabel("Nobility Points");
		otherNobility.setBorder(new LineBorder(new Color(0, 0, 0)));
		otherNobility.setName("otherPoints");
		otherNobility.setBounds(200, 0, 75, 21);
		table.add(otherNobility);
		
		JLabel otherCoins = new JLabel("Coins");
		otherCoins.setBorder(new LineBorder(new Color(0, 0, 0)));
		otherCoins.setName("otherPoints");
		otherCoins.setBounds(275, 0, 50, 21);
		table.add(otherCoins);
		
		JLabel otherAssistants = new JLabel("Assistants");
		otherAssistants.setBorder(new LineBorder(new Color(0, 0, 0)));
		otherAssistants.setName("otherPoints");
		otherAssistants.setBounds(325, 0, 60, 21);
		table.add(otherAssistants);
		
		JLabel otherEmporiums = new JLabel("Remaining Emporiums");
		otherEmporiums.setBorder(new LineBorder(new Color(0, 0, 0)));
		otherEmporiums.setName("otherPoints");
		otherEmporiums.setBounds(385, 0, 120, 21);
		table.add(otherEmporiums);
		//setVisible(true);
	}
	
	public void setRequest(Request request) {
		if(game.getCurrentPlayer().getPlayerID()!=this.ID)
			JOptionPane.showMessageDialog(null, "It's not your turn", "Wrong turn", JOptionPane.ERROR_MESSAGE);
		this.request = request;
	}
	
	public BufferedImage getImage(String path) throws IOException{
		File file=new File(path);
		return ImageIO.read(file);
	}
	
	private void createActionTab(JPanel gamePanel){
		gamePanel.removeAll();
		if(this.game.getGameState().getClass().equals(StartState.class)){
			
			ImagePanel actions = new ImagePanel(pathAction, actionDimension);
			actions.setSize(actionDimension);
			gamePanel.add(actions);
			actions.setLayout(null);

			JButton skipAction=new JButton("SKIP TO THE NEXT PLAYER");
			skipAction.setName("skipActionButton");
			skipAction.setBounds(0,actionDimension.height,actionDimension.width,50);
			gamePanel.add(skipAction);
			skipAction.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					if(JOptionPane.showConfirmDialog(null, "Are you sure you want to pass the turn?", "Passing Turn Confirmation", JOptionPane.YES_NO_OPTION)
							==0){
						request=new ActionRequest(new SkipAction(),ID);
					}
				}

			});


			JButton acquirePermit = new JButton("");
			acquirePermit.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			acquirePermit.setBackground(SystemColor.control);
			acquirePermit.setContentAreaFilled(false);
			acquirePermit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AcquirePermitFrame input = new AcquirePermitFrame(game, thisObj);
					input.setVisible(true);
					input.setAutoRequestFocus(true);
				}
			});
			acquirePermit.setSize(actionButtonDimension);
			acquirePermit.setLocation(actionDimension.width*19/1000, actionDimension.height*199/1000);
			actions.add(acquirePermit);

			JButton buildEmporiumByKing = new JButton("");
			buildEmporiumByKing.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			buildEmporiumByKing.setBackground(SystemColor.control);
			buildEmporiumByKing.setContentAreaFilled(false);
			buildEmporiumByKing.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					BuildEmporiumByKingFrame kingFrame=new BuildEmporiumByKingFrame(game, thisObj);
					kingFrame.setVisible(true);
				}
			});
			buildEmporiumByKing.setSize(actionButtonDimension);
			buildEmporiumByKing.setLocation(actionDimension.width*19/1000, 2*actionDimension.height*199/1000);
			actions.add(buildEmporiumByKing);

			JButton electCouncillor = new JButton("");
			electCouncillor.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			electCouncillor.setBackground(SystemColor.control);
			electCouncillor.setContentAreaFilled(false);
			electCouncillor.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ElectCouncillorFrame electCouncillor=new ElectCouncillorFrame(game, thisObj,false);
					electCouncillor.setVisible(true);
				}
			});
			electCouncillor.setSize(actionButtonDimension);
			electCouncillor.setLocation(actionDimension.width*19/1000, 3*actionDimension.height*199/1000);
			actions.add(electCouncillor);

			JButton buildEmporiumByPermit = new JButton("");
			buildEmporiumByPermit.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			buildEmporiumByPermit.setBackground(SystemColor.control);
			buildEmporiumByPermit.setContentAreaFilled(false);
			buildEmporiumByPermit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					BuildEmporiumByPermitFrame frame=new BuildEmporiumByPermitFrame(game, thisObj);
					frame.setVisible(true);
				}
			});
			buildEmporiumByPermit.setSize(actionButtonDimension);
			buildEmporiumByPermit.setLocation(actionDimension.width*19/1000, 4*actionDimension.height*199/1000);
			actions.add(buildEmporiumByPermit);

			JButton engageAssistant = new JButton("");
			engageAssistant.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(JOptionPane.showConfirmDialog(null, "Do you want to confirm your choise?", "Action", JOptionPane.YES_NO_OPTION)==0)
						setRequest(new ActionRequest(new EngageAssistant(), ID));
				}
			});
			engageAssistant.setContentAreaFilled(false);
			engageAssistant.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			engageAssistant.setBackground(SystemColor.menu);
			engageAssistant.setSize(actionButtonDimension);
			engageAssistant.setLocation(actionDimension.width*19/1000+actionButtonDimension.width, actionDimension.height*199/1000);
			actions.add(engageAssistant);

			JButton changeFaceUpPermits = new JButton("");
			changeFaceUpPermits.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ChangeFaceUpPermitsFrame frame=new ChangeFaceUpPermitsFrame(game, thisObj);
					frame.setVisible(true);
				}
			});
			changeFaceUpPermits.setContentAreaFilled(false);
			changeFaceUpPermits.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			changeFaceUpPermits.setBackground(SystemColor.menu);
			changeFaceUpPermits.setSize(actionButtonDimension);
			changeFaceUpPermits.setLocation(actionDimension.width*19/1000+actionButtonDimension.width, 2*actionDimension.height*199/1000);
			actions.add(changeFaceUpPermits);

			JButton electCouncillorByAssistant = new JButton("");
			electCouncillorByAssistant.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ElectCouncillorFrame frame=new ElectCouncillorFrame(game, thisObj, true);
					frame.setVisible(true);
				}
			});
			electCouncillorByAssistant.setContentAreaFilled(false);
			electCouncillorByAssistant.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			electCouncillorByAssistant.setBackground(SystemColor.menu);
			electCouncillorByAssistant.setSize(actionButtonDimension);
			electCouncillorByAssistant.setLocation(actionDimension.width*19/1000+actionButtonDimension.width, 3*actionDimension.height*199/1000);
			actions.add(electCouncillorByAssistant);

			JButton extraMainAction = new JButton("");
			extraMainAction.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(JOptionPane.showConfirmDialog(null, "Do you want to confirm your choise?", "Action", JOptionPane.YES_NO_OPTION)==0)
						setRequest(new ActionRequest(new ExtraMainAction(), ID));
				}
			});
			extraMainAction.setContentAreaFilled(false);
			extraMainAction.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			extraMainAction.setBackground(SystemColor.menu);
			extraMainAction.setSize(actionButtonDimension);
			extraMainAction.setLocation(actionDimension.width*19/1000+actionButtonDimension.width, 4*actionDimension.height*199/1000);
			actions.add(extraMainAction);
		}else{
			JButton addProduct=new JButton("Add a product to the marketplace");
			addProduct.setName("addProductButton");
			addProduct.setBounds(0,0,actionDimension.width,actionDimension.height/3);
			gamePanel.add(addProduct);
			addProduct.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(addProduct.isEnabled()){
						AddProductFrame frame=new AddProductFrame(game, thisObj);
						frame.setVisible(true);
					}
				}
			});
			
			JButton buyProduct=new JButton("Show the other players' product to buying");
			buyProduct.setName("buyProductButton");
			buyProduct.setBounds(0,actionDimension.height/3,actionDimension.width,actionDimension.height/3);
			gamePanel.add(buyProduct);
			buyProduct.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(buyProduct.isEnabled()){
						BuyProductFrame frame=new BuyProductFrame(game, thisObj);
						frame.setVisible(true);
					}
				}
			});
			
			if(game.getGameState().getClass().equals(MarketSellingState.class)){
				buyProduct.setEnabled(false);
			}else{
				addProduct.setEnabled(false);
			}
			
			JButton skipAction=new JButton("SKIP TO THE NEXT PLAYER");
			skipAction.setName("skipActionButton");
			skipAction.setBounds(0,actionDimension.height*2/3,actionDimension.width,actionDimension.height/3);
			gamePanel.add(skipAction);
			skipAction.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					if(JOptionPane.showConfirmDialog(null, "Are you sure you want to pass the turn?", "Passing Turn Confirmation", JOptionPane.YES_NO_OPTION)
							==0){
						request=new ActionRequest(new SkipAction(),ID);
					}
				}

			});
		}
	}
	
	public void setPlayerDefaultParams(String name){
		JTabbedPane x = (JTabbedPane)this.contentPane.getComponents()[1];
		JPanel playerTab=(JPanel)x.getComponent(0);
		JTextPane playerName=(JTextPane)(Arrays.asList(playerTab.getComponents()).stream().filter(e->e.getName()!=null&&e.getName().equals("playerName")).findFirst().get());
		playerName.setText(name);
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
		
		ImagePanel seasideTile = new ImagePanel(pathSeasideTile, regionTileDimension);
		seasideTile.setSize(regionTileDimension);
		seasideTile.setLocation(singleRegionDimension.width*754/1000, singleRegionDimension.height*910/1000);
		seasideTile.setName("landTile");
		seasideTile.setOpaque(false);
		seaside.add(seasideTile);
		
		ImagePanel hill=new ImagePanel(pathhill, singleRegionDimension);
		//hill.setLocation(448, 0);
		hill.setSize(singleRegionDimension);
		hill.setName("hill");
		regions.add(hill);
		hill.setLayout(null);
		hill.setBounds(singleRegionDimension.width, 0, singleRegionDimension.width, singleRegionDimension.height);
		
		ImagePanel hillTile = new ImagePanel(pathHillTile, regionTileDimension);
		hillTile.setSize(regionTileDimension);
		hillTile.setLocation(singleRegionDimension.width*657/1000, singleRegionDimension.height*915/1000);
		hillTile.setOpaque(false);
		hillTile.setName("hillTile");
		hill.add(hillTile);
		
		ImagePanel mountain=new ImagePanel(pathmountain, singleRegionDimension);
		//mountain.setLocation(896, 0);
		mountain.setSize(singleRegionDimension);
		mountain.setName("mountain");
		regions.add(mountain);
		mountain.setLayout(null);
		mountain.setBounds(2*singleRegionDimension.width, 0, singleRegionDimension.width, singleRegionDimension.height);
		
		ImagePanel mountainTile = new ImagePanel(pathMountainTile, regionTileDimension);
		mountainTile.setSize(regionTileDimension);
		mountainTile.setLocation(singleRegionDimension.width*653/1000, singleRegionDimension.height*915/1000);
		mountainTile.setOpaque(false);
		mountain.add(mountainTile);
		mountainTile.setName("mountainTile");
				
	}
	
	public void cityBonusLoader() throws JDOMException, IOException{
		XMLReaderForClient reader=new XMLReaderForClient();
		Map<Character,City> bonuses=new HashMap<>();
		for(City c:this.game.getAllCities()){
			bonuses.put(c.getFirstChar(), c);
		}
		
		
		JPanel regions=(JPanel)this.contentPane.getComponents()[0].getComponentAt(0, 0);
		reader.createCitiesFromRegionPanel(regions, bonuses, cardBoardDimension, game.getRegions());	

	}

	@Override
	public Request start() {
		request=null;
		System.out.println("Sono entrato in start e ho resettato la richiesta");
		if(!memoryContainer.getBonus().isEmpty()){
			BonusPermitChangeFrame frame=new BonusPermitChangeFrame(game, this, "Bonus Selection", 
					"Select the bonus you desire", false);
			frame.setVisible(true);
		}
		
		if(!memoryContainer.getPermits().isEmpty()){
			BonusPermitChangeFrame frame=new BonusPermitChangeFrame(game, this, "Permit Selection", 
					"Select the permit you wanto to acquire", true);
			frame.setVisible(true);
		}
		
		while(request==null){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Richiesta arrivata "+request);
		return request;
	}
	
	public Component getComponentByName(String name, JPanel targetPanel){
		for(Component c:targetPanel.getComponents()){
			if(c.getName()!=null&&c.getName().equalsIgnoreCase(name))
				return c;
		}
		return null;
	}
	
	private void updateCityPanel(String name, JPanel regions){
		for(Component regs:regions.getComponents()){
			JPanel region=(JPanel)regs;
			for(Component city:region.getComponents()){
				JPanel kingPanel=(JPanel)this.getComponentByName("kingPanel", (JPanel)city);
				if(kingPanel!=null){
					JPanel newKingPanel;
					if(city.getName().equalsIgnoreCase(name)){
						newKingPanel=new ImagePanel("src/main/resources/Immagini/corona.png",kingPanel.getSize());
					}else{
						newKingPanel=new JPanel();
					}
					newKingPanel.setBounds(kingPanel.getBounds());
					newKingPanel.setOpaque(false);
					newKingPanel.setName(kingPanel.getName());
					((JPanel)city).remove(kingPanel);
					((JPanel)city).add(newKingPanel);
				}
			}
		}
	}

	@Override
	public void updateModel(Game game) {
		this.game=game;
		if(this.ID==0 || this.game.getPlayerByID(ID) == null)
			return;
		
		JPanel regions=(JPanel)this.contentPane.getComponents()[0].getComponentAt(0, 0);
		this.updateCityPanel(String.valueOf(game.getKingsPosition().getFirstChar()), regions);
		
		
		Player player = this.game.getPlayerByID(ID);
		JTabbedPane tabbedPane = (JTabbedPane)this.contentPane.getComponents()[1];
		JPanel playerTab=(JPanel)tabbedPane.getComponent(0);
		JPanel gameTab=(JPanel)tabbedPane.getComponent(1);
		
		this.createActionTab(gameTab);
		
		Double politicRelX=0.0686275;
		Double politicRelY=0.39726;
		Dimension politicDim=new Dimension((int)(0.89*tabbedPane.getWidth()),(int)(0.2288*tabbedPane.getHeight()));
		Double buildingRelY=0.65;
		
		JPanel politicsCards=new JPanel();
		politicsCards.setBounds((int)(politicRelX*tabbedPane.getWidth()), (int)(politicRelY*tabbedPane.getHeight()), politicDim.width, politicDim.height);
		politicsCards.setLayout(new BoxLayout(politicsCards,BoxLayout.X_AXIS));
		politicsCards.setName("politicsCards");
		Dimension cardDim=new Dimension((int)(0.22026*politicDim.width),(int)(0.91018*politicDim.height));
		for(int i=0;i<player.getCardsOwned().size();i++){
			JLabel card=new ImageLabel(player.getCardsOwned().get(i).getImagePath(),cardDim);
			card.setName("politicsCard"+i);
			//card.setSize(cardDim);
			politicsCards.add(card);
		}
		playerTab.remove(playerTab.getComponentAt(politicsCards.getX(), politicsCards.getY()));
		playerTab.add(politicsCards);
		JScrollPane scrollCards=new JScrollPane(politicsCards);
		scrollCards.setBounds(politicsCards.getBounds());
		playerTab.add(scrollCards);
		
		JPanel buildingPermits=new JPanel();
		buildingPermits.setBounds((int)(politicRelX*tabbedPane.getWidth()),(int)(buildingRelY*tabbedPane.getHeight()), politicDim.width, politicDim.height);
		buildingPermits.setLayout(new BoxLayout(buildingPermits,BoxLayout.X_AXIS));
		buildingPermits.setName("buildingPermits");
		Dimension buildingDim=new Dimension((int)(0.22026*politicDim.width), (int)(0.22026*politicDim.width));
		for(int i=0;i<player.getBuildingPermits().size();i++){
			JLabel permit=new ImageLabel(player.getBuildingPermits().get(i).getImagePath(),buildingDim);
			permit.setName("buildingPermit"+i);
			//card.setSize(cardDim);
			buildingPermits.add(permit);
		}
		playerTab.remove(playerTab.getComponentAt(buildingPermits.getX(), buildingPermits.getY()));
		playerTab.add(buildingPermits);
		JScrollPane scrollPermits=new JScrollPane(buildingPermits,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPermits.setBounds(buildingPermits.getBounds());
		playerTab.add(scrollPermits);
		
		
		JPanel colorPlayer=(JPanel)(Arrays.asList(playerTab.getComponents()).stream().filter(e->e.getName()!=null&&e.getName().equals("colorPlayer")).findFirst().get());
		colorPlayer.setBackground(player.getChosenColor());
		
		JLabel turnIndicator=(JLabel)(Arrays.asList(playerTab.getComponents()).stream().filter(e->e.getName()!=null&&e.getName().equals("turnIndicator")).findFirst().get());
		if(game.getCurrentPlayer().getPlayerID()==this.ID){
			turnIndicator.setText("It's your turn");
		}else{
			turnIndicator.setText("It's "+game.getCurrentPlayer().getName()+" - "+game.getCurrentPlayer().getPlayerID()+"turn");
		}
		
		JTextPane textPane = (JTextPane)Arrays.asList(playerTab.getComponents()).stream().filter(e -> e.getName()!=null&&e.getName().equals("playerName")).findFirst().get();
		textPane.setText(player.getName());
		playerTab.remove(textPane);
		playerTab.add(textPane);
		
		textPane = (JTextPane)Arrays.asList(playerTab.getComponents()).stream().filter(e -> e.getName()!=null&&e.getName().equals("victory points")).findFirst().get();
		textPane.setText(Integer.toString(player.getPoints()));
		playerTab.remove(textPane);
		playerTab.add(textPane);
		
		textPane = (JTextPane)Arrays.asList(playerTab.getComponents()).stream().filter(e -> e.getName()!=null&&e.getName().equals("coins")).findFirst().get();
		textPane.setText(Integer.toString(player.getCoins()));
		playerTab.remove(textPane);
		playerTab.add(textPane);
		
		textPane = (JTextPane)Arrays.asList(playerTab.getComponents()).stream().filter(e -> e.getName()!=null&&e.getName().equals("nobility points")).findFirst().get();
		textPane.setText(Integer.toString(player.getNobilityPoints()));
		playerTab.remove(textPane);
		playerTab.add(textPane);
		
		textPane = (JTextPane)Arrays.asList(playerTab.getComponents()).stream().filter(e -> e.getName()!=null&&e.getName().equals("assistants")).findFirst().get();
		textPane.setText(Integer.toString(player.getAssistants()));
		playerTab.remove(textPane);
		playerTab.add(textPane);
		
		textPane = (JTextPane)Arrays.asList(playerTab.getComponents()).stream().filter(e -> e.getName()!=null&&e.getName().equals("remaining emporiums")).findFirst().get();
		textPane.setText(Integer.toString(player.getRemainingEmporiums()));
		playerTab.remove(textPane);
		playerTab.add(textPane);
		
		JScrollPane scrollPane = (JScrollPane)Arrays.asList(playerTab.getComponents()).stream().filter(e -> e.getName()!=null&&e.getName().equals("scroll pane")).findFirst().get();
		JTextArea textArea = (JTextArea)scrollPane.getComponent(0).getComponentAt(0, 0);
		if(!player.getEmporium().isEmpty()){
			String support = "";
			Iterator<String> it=player.getEmporium().stream().map(e -> e.getCity().getName()+"\r\n").iterator();
			while(it.hasNext()){
				support=support.concat(it.next());
			}
			textArea.setText(support);
		}
		else
			textArea.setText("No emporiums built yet");
		
		JPanel panel = (JPanel)this.contentPane.getComponent(0);
		ImagePanel imagePanel = (ImagePanel)panel.getComponentAt(0, singleRegionDimension.height);
				 
		Region region = this.game.getRegions().stream().filter(e->e.getName().equals("land")).findFirst().get();
		Iterator<BuildingPermit> permitIterator = region.getPermitsDeck().getFaceUpPermits().iterator();
		ImagePanel seasideFaceupPermit1 = new ImagePanel(permitIterator.next().getImagePath(), permitsDeckDimension);
		seasideFaceupPermit1.setSize(permitsDeckDimension);
		seasideFaceupPermit1.setLocation(nobilityPanelDimension.width*143/1000, nobilityPanelDimension.height*39/1000);
		seasideFaceupPermit1.setVisible(true);
		imagePanel.remove(imagePanel.getComponentAt(seasideFaceupPermit1.getX(), seasideFaceupPermit1.getY()));
		imagePanel.add(seasideFaceupPermit1);
		
		ImagePanel seasideFaceupPermit2 = new ImagePanel(permitIterator.next().getImagePath(), permitsDeckDimension);
		seasideFaceupPermit2.setSize(permitsDeckDimension);
		seasideFaceupPermit2.setLocation(nobilityPanelDimension.width*218/1000, nobilityPanelDimension.height*39/1000);
		seasideFaceupPermit2.setVisible(true);
		imagePanel.remove(imagePanel.getComponentAt(seasideFaceupPermit2.getX(), seasideFaceupPermit2.getY()));
		imagePanel.add(seasideFaceupPermit2);

		region = this.game.getRegions().stream().filter(e->e.getName().equals("hill")).findFirst().get();
		permitIterator = region.getPermitsDeck().getFaceUpPermits().iterator();
		ImagePanel hillFaceupPermit1 = new ImagePanel(permitIterator.next().getImagePath(), permitsDeckDimension);
		hillFaceupPermit1.setSize(permitsDeckDimension);
		hillFaceupPermit1.setLocation(nobilityPanelDimension.width*44/100, nobilityPanelDimension.height*42/1000);
		hillFaceupPermit1.setVisible(true);
		imagePanel.remove(imagePanel.getComponentAt(hillFaceupPermit1.getX(), hillFaceupPermit1.getY()));
		imagePanel.add(hillFaceupPermit1);
		
		ImagePanel hillFaceupPermit2 = new ImagePanel(permitIterator.next().getImagePath(), permitsDeckDimension);
		hillFaceupPermit2.setSize(permitsDeckDimension);
		hillFaceupPermit2.setLocation(nobilityPanelDimension.width*518/1000, nobilityPanelDimension.height*42/1000);
		hillFaceupPermit2.setVisible(true);
		imagePanel.remove(imagePanel.getComponentAt(hillFaceupPermit2.getX(), hillFaceupPermit2.getY()));
		imagePanel.add(hillFaceupPermit2);
		
		region = this.game.getRegions().stream().filter(e->e.getName().equals("mountain")).findFirst().get();
		permitIterator = region.getPermitsDeck().getFaceUpPermits().iterator();
		ImagePanel mountainFaceupPermit1 = new ImagePanel(permitIterator.next().getImagePath(), permitsDeckDimension);
		mountainFaceupPermit1.setSize(permitsDeckDimension);
		mountainFaceupPermit1.setLocation(nobilityPanelDimension.width*778/1000, nobilityPanelDimension.height*43/1000);
		mountainFaceupPermit1.setVisible(true);
		imagePanel.remove(imagePanel.getComponentAt(mountainFaceupPermit1.getX(), mountainFaceupPermit1.getY()));
		imagePanel.add(mountainFaceupPermit1);
		ImagePanel mountainFaceupPermit2 = new ImagePanel(permitIterator.next().getImagePath(), permitsDeckDimension);
		mountainFaceupPermit2.setSize(permitsDeckDimension);
		mountainFaceupPermit2.setLocation(nobilityPanelDimension.width*854/1000, nobilityPanelDimension.height*43/1000);
		mountainFaceupPermit2.setVisible(true);
		imagePanel.remove(imagePanel.getComponentAt(mountainFaceupPermit2.getX(), mountainFaceupPermit2.getY()));
		imagePanel.add(mountainFaceupPermit2);
		
		imagePanel = (ImagePanel)imagePanel.getComponentAt(nobilityPanelDimension.width*874/1000+colorTileDimension.width/2,
				nobilityPanelDimension.height*421/1000+colorTileDimension.height/2);
		if(!this.game.getKingTileList().isEmpty())
			imagePanel = new ImagePanel(pathKingTile+
					Integer.toString(5-this.game.getKingTileList().size()+1)+".jpg", 
					new Dimension(60, 56));
		
		JPanel map = (JPanel)contentPane.getComponents()[0];
		map = (JPanel)map.getComponents()[1];
		
		JPanel council = (JPanel)Arrays.
				asList(map.getComponents()).stream()
				.filter(e->e.getName()!=null&&e.getName().equals("landCouncil")).findFirst().get();
		this.paintCouncil(council, councilDimension);
		council = (JPanel)Arrays.
				asList(map.getComponents()).stream()
				.filter(e->e.getName()!=null&&e.getName().equals("hillCouncil")).findFirst().get();
		this.paintCouncil(council, councilDimension);
		council = (JPanel)Arrays.
				asList(map.getComponents()).stream()
				.filter(e->e.getName()!=null&&e.getName().equals("mountainCouncil")).findFirst().get();
		this.paintCouncil(council,councilDimension);
		council = (JPanel)Arrays.
				asList(map.getComponents()).stream()
				.filter(e->e.getName()!=null&&e.getName().equals("king")).findFirst().get();
		this.paintCouncil(council, councilDimension);
		
		JTabbedPane tabbed = (JTabbedPane)contentPane.getComponents()[1];
		JPanel tableOthers = (JPanel)tabbed.getComponent(2);
		this.updateOtherPlayers((JTable)tableOthers.getComponent(0));
		
		System.out.println("changed");
		this.repaint();
	}

	private void updateOtherPlayers(JTable tableOthers) {
		
		int i=0;
		for(Player p : this.game.getPlayers())
			if(p.getPlayerID() != this.ID){
				i++;
				JTextPane name = new JTextPane();
				name.setEditable(false);
				name.setText(p.getName());
				name.setBorder(new LineBorder(new Color(0, 0, 0)));
				name.setBounds(0, i*21, 75, 21);
				tableOthers.add(name);
				
				JTextPane color = new JTextPane();
				color.setEditable(false);
				color.setBackground(p.getChosenColor());
				color.setBorder(new LineBorder(new Color(0, 0, 0)));
				color.setBounds(75, i*21, 50, 21);
				tableOthers.add(color);
				
				JTextPane points = new JTextPane();
				points.setEditable(false);
				points.setText(Integer.toString(p.getPoints()));
				points.setBounds(125, i*21, 75, 21);
				points.setBorder(new LineBorder(new Color(0, 0, 0)));
				tableOthers.add(points);
				
				JTextPane nobility = new JTextPane();
				nobility.setEditable(false);
				nobility.setText(Integer.toString(p.getNobilityPoints()));
				nobility.setBounds(200, i*21, 75, 21);
				nobility.setBorder(new LineBorder(new Color(0, 0, 0)));
				tableOthers.add(nobility);
				
				JTextPane coins = new JTextPane();
				coins.setEditable(false);
				coins.setText(Integer.toString(p.getCoins()));
				coins.setBounds(275, i*21, 50, 21);
				coins.setBorder(new LineBorder(new Color(0, 0, 0)));
				tableOthers.add(coins);
				
				JTextPane assistants = new JTextPane();
				assistants.setEditable(false);
				assistants.setText(Integer.toString(p.getAssistants()));
				assistants.setBounds(325, i*21, 60, 21);
				assistants.setBorder(new LineBorder(new Color(0, 0, 0)));
				tableOthers.add(assistants);
				
				JTextPane emporiums = new JTextPane();
				emporiums.setEditable(false);
				emporiums.setText(Integer.toString(p.getRemainingEmporiums()));
				emporiums.setBounds(385, i*21, 120, 21);
				emporiums.setBorder(new LineBorder(new Color(0, 0, 0)));
				tableOthers.add(emporiums);
			}
	}



	private void paintCouncil(JPanel council, Dimension councilDimension) {
		Dimension councillorDimension =  new Dimension(councilDimension.width/4, councilDimension.height);
		council.removeAll();//è di prova!!
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

		JPanel councillor1 = new JPanel();
		councillor1.setSize(councillorDimension);
		councillor1.setLocation(councilDimension.width*3/4, 0);
		councillor1.setBackground(gameCouncillor.next().getColor());
		council.add(councillor1);
		JPanel councillor2 = new JPanel();
		councillor2.setSize(councillorDimension);
		councillor2.setLocation(councilDimension.width/2, 0);
		councillor2.setBackground(gameCouncillor.next().getColor());
		council.add(councillor2);
		JPanel councillor3 = new JPanel();
		councillor3.setSize(councillorDimension);
		councillor3.setLocation(councilDimension.width/4, 0);
		councillor3.setBackground(gameCouncillor.next().getColor());
		council.add(councillor3);
		JPanel councillor4 = new JPanel();
		councillor4.setSize(councillorDimension);
		councillor4.setLocation(0,0);
		councillor4.setBackground(gameCouncillor.next().getColor());
		council.add(councillor4);
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



	/**
	 * @return the memoryContainer
	 */
	public LocalStorage getMemoryContainer() {
		return memoryContainer;
	}
	
	
}
