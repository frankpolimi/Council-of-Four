package client.GUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Scrollbar;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
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
import model.actions.SkipAction;
import model.game.BuildingPermit;
import model.game.Game;
import model.game.Player;
import model.game.council.Councillor;
import model.game.politics.PoliticsCard;
import model.game.topology.City;
import model.game.topology.Region;
import view.ActionRequest;
import view.LocalStorage;
import view.Request;

import java.awt.BorderLayout;
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
	Dimension actionDimension= new Dimension(rightPanelDimension.width,rightPanelDimension.width*766/1000);
	Dimension actionButtonDimension= new Dimension(rightPanelDimension.width*48/100, rightPanelDimension.width*766/5000);
	Dimension councilDimension = new Dimension(nobilityPanelDimension.width*109/1000, nobilityPanelDimension.height*875/10000);
	

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
		seasideCouncil.setName("seaside council");
		seasideCouncil.setSize(councilDimension);
		seasideCouncil.setLocation(nobilityPanelDimension.width*136/1000, nobilityPanelDimension.height*35/100);
		nobility.add(seasideCouncil);
		
		JPanel hillCouncil = new JPanel();
		hillCouncil.setOpaque(false);
		hillCouncil.setLayout(null);
		hillCouncil.setName("hill council");
		hillCouncil.setSize(councilDimension);
		hillCouncil.setLocation(nobilityPanelDimension.width*439/1000, nobilityPanelDimension.height*35/100);
		nobility.add(hillCouncil);
		
		JPanel mountainCouncil = new JPanel();
		mountainCouncil.setOpaque(false);
		mountainCouncil.setLayout(null);
		mountainCouncil.setName("mountain council");
		mountainCouncil.setSize(councilDimension);
		mountainCouncil.setLocation(nobilityPanelDimension.width*773/1000, nobilityPanelDimension.height*35/100);
		nobility.add(mountainCouncil);
		
		JPanel kingCouncil = new JPanel();
		kingCouncil.setOpaque(false);
		kingCouncil.setLayout(null);
		kingCouncil.setName("king council");
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
				// TODO Auto-generated method stub
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
				//JOptionPane.showMessageDialog(contentPane, "acquirePermit");
				//TODO wip
				AcquirePermitGUI input = new AcquirePermitGUI();
				input.setVisible(true);
				input.setAutoRequestFocus(true);
				input.council(game.getRegions());
				/*
				input.permits();
				input.politicsCard();
				*/
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
				JOptionPane.showMessageDialog(contentPane, "buildEmporiumByKing");
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
				ElectCouncillorFrame electCouncillor=new ElectCouncillorFrame(game, thisObj);
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
				JOptionPane.showMessageDialog(contentPane, "buildEmporiumByPermit");
			}
		});
		buildEmporiumByPermit.setSize(actionButtonDimension);
		buildEmporiumByPermit.setLocation(actionDimension.width*19/1000, 4*actionDimension.height*199/1000);
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
		engageAssistant.setSize(actionButtonDimension);
		engageAssistant.setLocation(actionDimension.width*19/1000+actionButtonDimension.width, actionDimension.height*199/1000);
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
		changeFaceUpPermits.setSize(actionButtonDimension);
		changeFaceUpPermits.setLocation(actionDimension.width*19/1000+actionButtonDimension.width, 2*actionDimension.height*199/1000);
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
		electCouncillorByAssistant.setSize(actionButtonDimension);
		electCouncillorByAssistant.setLocation(actionDimension.width*19/1000+actionButtonDimension.width, 3*actionDimension.height*199/1000);
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
		extraMainAction.setSize(actionButtonDimension);
		extraMainAction.setLocation(actionDimension.width*19/1000+actionButtonDimension.width, 4*actionDimension.height*199/1000);
		actions.add(extraMainAction);
		
		JTextArea chatOutputMessges = new JTextArea();
		chatOutputMessges.setText("chat messages zone");
		gamePanel.add(chatOutputMessges);
		chatOutputMessges.setVisible(false);
		
		JTextField chatInputMessage = new JTextField();
		chatInputMessage.setText("chat input message");
		gamePanel.add(chatInputMessage);
		chatInputMessage.setColumns(10);
		chatInputMessage.setVisible(false);
		
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
	
	public void setRequest(Request request) {
		this.request = request;
	}
	
	public BufferedImage getImage(String path) throws IOException{
		File file=new File(path);
		return ImageIO.read(file);
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
		
		ImagePanel seasideTile = new ImagePanel(pathSeasideTile, new Dimension(51, 30));
		seasideTile.setName("landTile");
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
		hillTile.setName("hillTile");
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
		mountainTile.setName("mountainTile");
				
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
		request=null;
		System.out.println("Sono entrato in start e ho resettato la richiesta");
		while(request==null){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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
				
		Double politicRelX=0.0686275;
		Double politicRelY=0.39726;
		Dimension politicDim=new Dimension((int)(0.89*tabbedPane.getWidth()),(int)(0.2288*tabbedPane.getHeight()));
		/*
		Double buildingRelY=0.6849315;
		JScrollPane buildingPermits = new JScrollPane();
		buildingPermits.setBounds((int)(politicRelX*tabbedPane.getWidth()),(int)(buildingRelY*tabbedPane.getHeight()), politicDim.width, politicDim.height);
		playerTab.add(buildingPermits);
		buildingPermits.setName("buildingPermits");*/
		
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
		playerTab.add(politicsCards);
		JScrollPane scrollCards=new JScrollPane(politicsCards);
		scrollCards.setBounds(politicsCards.getBounds());
		playerTab.add(scrollCards);
		
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
		
		textPane = (JTextPane)Arrays.asList(playerTab.getComponents()).stream().filter(e -> e.getName()!=null&&e.getName().equals("victory points")).findFirst().get();
		textPane.setText(Integer.toString(player.getPoints()));
		
		textPane = (JTextPane)Arrays.asList(playerTab.getComponents()).stream().filter(e -> e.getName()!=null&&e.getName().equals("coins")).findFirst().get();
		textPane.setText(Integer.toString(player.getCoins()));
		
		textPane = (JTextPane)Arrays.asList(playerTab.getComponents()).stream().filter(e -> e.getName()!=null&&e.getName().equals("nobility points")).findFirst().get();
		textPane.setText(Integer.toString(player.getNobilityPoints()));
		
		textPane = (JTextPane)Arrays.asList(playerTab.getComponents()).stream().filter(e -> e.getName()!=null&&e.getName().equals("assistants")).findFirst().get();
		textPane.setText(Integer.toString(player.getAssistants()));
		
		textPane = (JTextPane)Arrays.asList(playerTab.getComponents()).stream().filter(e -> e.getName()!=null&&e.getName().equals("remaining emporiums")).findFirst().get();
		textPane.setText(Integer.toString(player.getRemainingEmporiums()));
		
		JScrollPane scrollPane = (JScrollPane)Arrays.asList(playerTab.getComponents()).stream().filter(e -> e.getName()!=null&&e.getName().equals("scroll pane")).findFirst().get();
		JTextArea textArea = (JTextArea)scrollPane.getComponent(0).getComponentAt(0, 0);
		if(!player.getEmporium().isEmpty()){
			String support = "";
			player.getEmporium().stream().map(e -> support.concat(e.getCity().getName()+"\r\n"));
			textArea.setText(support);
		}
		else
			textArea.setText("No emporiums built yet");
		
		JPanel panel = (JPanel)this.contentPane.getComponent(0);
		ImagePanel imagePanel = (ImagePanel)panel.getComponentAt(0, singleRegionDimension.height);
		
		Iterator<Region> regionIterator = this.game.getRegions().iterator();
		Region region = null;
		while(regionIterator.hasNext()){
			region = regionIterator.next();
			if("land".equals(region.getName()))
				break;
		}
		Iterator<BuildingPermit> permitIterator = region.getPermitsDeck().getFaceUpPermits().iterator();
		ImagePanel seasideFaceupPermit1 = new ImagePanel(permitIterator.next().getImagePath(), permitsDeckDimension);
		seasideFaceupPermit1.setSize(permitsDeckDimension);
		seasideFaceupPermit1.setLocation(nobilityPanelDimension.width*143/1000, nobilityPanelDimension.height*39/1000);
		seasideFaceupPermit1.setVisible(true);
		imagePanel.add(seasideFaceupPermit1);
		ImagePanel seasideFaceupPermit2 = new ImagePanel(permitIterator.next().getImagePath(), permitsDeckDimension);
		seasideFaceupPermit2.setSize(permitsDeckDimension);
		seasideFaceupPermit2.setLocation(nobilityPanelDimension.width*218/1000, nobilityPanelDimension.height*39/1000);
		seasideFaceupPermit2.setVisible(true);
		imagePanel.add(seasideFaceupPermit2);

		regionIterator = this.game.getRegions().iterator();
		while(regionIterator.hasNext()){
			region = regionIterator.next();
			if("hill".equals(region.getName()))
				break;
		}
		permitIterator = region.getPermitsDeck().getFaceUpPermits().iterator();
		ImagePanel hillFaceupPermit1 = new ImagePanel(permitIterator.next().getImagePath(), permitsDeckDimension);
		hillFaceupPermit1.setSize(permitsDeckDimension);
		hillFaceupPermit1.setLocation(nobilityPanelDimension.width*44/100, nobilityPanelDimension.height*42/1000);
		hillFaceupPermit1.setVisible(true);
		imagePanel.add(hillFaceupPermit1);
		ImagePanel hillFaceupPermit2 = new ImagePanel(permitIterator.next().getImagePath(), permitsDeckDimension);
		hillFaceupPermit2.setSize(permitsDeckDimension);
		hillFaceupPermit2.setLocation(nobilityPanelDimension.width*518/1000, nobilityPanelDimension.height*42/1000);
		hillFaceupPermit2.setVisible(true);
		imagePanel.add(hillFaceupPermit2);
		
		regionIterator = this.game.getRegions().iterator();
		while(regionIterator.hasNext()){
			region = regionIterator.next();
			if("mountain".equals(region.getName()))
				break;
		}
		permitIterator = region.getPermitsDeck().getFaceUpPermits().iterator();
		ImagePanel mountainFaceupPermit1 = new ImagePanel(permitIterator.next().getImagePath(), permitsDeckDimension);
		mountainFaceupPermit1.setSize(permitsDeckDimension);
		mountainFaceupPermit1.setLocation(nobilityPanelDimension.width*778/1000, nobilityPanelDimension.height*43/1000);
		mountainFaceupPermit1.setVisible(true);
		imagePanel.add(mountainFaceupPermit1);
		ImagePanel mountainFaceupPermit2 = new ImagePanel(permitIterator.next().getImagePath(), permitsDeckDimension);
		mountainFaceupPermit2.setSize(permitsDeckDimension);
		mountainFaceupPermit2.setLocation(nobilityPanelDimension.width*854/1000, nobilityPanelDimension.height*43/1000);
		mountainFaceupPermit2.setVisible(true);
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
				.filter(e->e.getName()!=null&&e.getName().equals("seaside council")).findFirst().get();
		this.paintCouncil(council);
		council = (JPanel)Arrays.
				asList(map.getComponents()).stream()
				.filter(e->e.getName()!=null&&e.getName().equals("hill council")).findFirst().get();
		this.paintCouncil(council);
		council = (JPanel)Arrays.
				asList(map.getComponents()).stream()
				.filter(e->e.getName()!=null&&e.getName().equals("mountain council")).findFirst().get();
		this.paintCouncil(council);
		council = (JPanel)Arrays.
				asList(map.getComponents()).stream()
				.filter(e->e.getName()!=null&&e.getName().equals("king council")).findFirst().get();
		this.paintCouncil(council);
		System.out.println("changed");
		this.repaint();
	}

	private void paintCouncil(JPanel council) {
		council.removeAll();//è di prova!!
		Region r = null;
		Iterator<Councillor> gameCouncillor = null;
		switch(council.getName()){
			case "seaside council":{
				r = game.getRegions().stream().filter(e->e.getName().equals("land")).findFirst().get();
				gameCouncillor = r.getCouncil().getCouncillors().iterator();
				break;
			}
			case "hill council":{
				r = game.getRegions().stream().filter(e->e.getName().equals("hill")).findFirst().get();
				gameCouncillor = r.getCouncil().getCouncillors().iterator();
				break;
			}
			case "mountain council":{
				r = game.getRegions().stream().filter(e->e.getName().equals("mountain")).findFirst().get();
				gameCouncillor = r.getCouncil().getCouncillors().iterator();
				break;
			}
			default:{
				gameCouncillor = game.getKingsCouncil().getCouncillors().iterator();
			}
		}

		JPanel councillor1 = new JPanel();
		councillor1.setSize(councilDimension.width/4, councilDimension.height);
		councillor1.setLocation(councilDimension.width*3/4, 0);
		councillor1.setBackground(gameCouncillor.next().getColor());
		council.add(councillor1);
		JPanel councillor2 = new JPanel();
		councillor2.setSize(councilDimension.width/4, councilDimension.height);
		councillor2.setLocation(councilDimension.width/2, 0);
		councillor2.setBackground(gameCouncillor.next().getColor());
		council.add(councillor2);
		JPanel councillor3 = new JPanel();
		councillor3.setSize(councilDimension.width/4, councilDimension.height);
		councillor3.setLocation(councilDimension.width/4, 0);
		councillor3.setBackground(gameCouncillor.next().getColor());
		council.add(councillor3);
		JPanel councillor4 = new JPanel();
		councillor4.setSize(councilDimension.width/4, councilDimension.height);
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
}
