package client.GUI;

import java.awt.Dimension;
import java.awt.EventQueue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.Toolkit;

import javax.swing.border.LineBorder;

import client.ClientViewInterface;
import model.game.Game;
import view.LocalStorage;
import view.Request;

import java.awt.Color;
import javax.swing.border.MatteBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class GUI extends JFrame implements ClientViewInterface {
	
	private Game game;
	private LocalStorage memoryContainer;
	private int ID;
	
	
	private JPanel contentPane;
	private final String pathLand="src/main/resources/Immagini/mareA.jpg";
	private final String pathHill="src/main/resources/Immagini/collinaA.jpg";
	private final String pathMountain="src/main/resources/Immagini/montagnaA.jpg";
	private final String pathNobility="src/main/resources/Immagini/nobility.jpg";
	private final String pathAction="src/main/resources/Immagini/action_table.jpg";

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
		setAlwaysOnTop(true);
		
		Dimension monitorDimension=Toolkit.getDefaultToolkit().getScreenSize();
		Dimension cardBoardDimension=new Dimension((monitorDimension.width/160*105), (monitorDimension.height));
		Dimension regionPanelDimension=new Dimension(cardBoardDimension.width, cardBoardDimension.height/90*53);
		Dimension singleRegionDimension=new Dimension(regionPanelDimension.width/3, regionPanelDimension.height);
		Dimension nobilityPanelDimension=new Dimension(cardBoardDimension.width, cardBoardDimension.height/90*35);
		
		this.setSize(monitorDimension);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setMinimumSize(new Dimension(1366, 768));
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
		regions.setLocation(1, 1);
		regions.setSize(regionPanelDimension);
		regions.setAutoscrolls(true);
		cardBoard.add(regions);
		regions.setBounds(0, 0, regionPanelDimension.width, regionPanelDimension.height);
		regions.setLayout(null);
	
		ImagePanel seaside=new ImagePanel(pathLand, singleRegionDimension);
		//seaside.setLocation(0, 0);
		seaside.setSize(singleRegionDimension);
		regions.add(seaside);
		seaside.setLayout(null);
		seaside.setBounds(0, 0, singleRegionDimension.width, singleRegionDimension.height);
		
		ImagePanel hill=new ImagePanel(pathHill, singleRegionDimension);
		//hill.setLocation(448, 0);
		hill.setSize(singleRegionDimension);
		regions.add(hill);
		hill.setLayout(null);
		hill.setBounds(singleRegionDimension.width, 0, singleRegionDimension.width, singleRegionDimension.height);
		
		ImagePanel mountain=new ImagePanel(pathMountain, singleRegionDimension);
		//mountain.setLocation(896, 0);
		mountain.setSize(singleRegionDimension);
		regions.add(mountain);
		mountain.setLayout(null);
		mountain.setBounds(2*singleRegionDimension.width, 0, singleRegionDimension.width, singleRegionDimension.height);
		
		ImagePanel nobility = new ImagePanel(pathNobility, nobilityPanelDimension);
		//nobility.setLocation(1, 368);
		nobility.setSize(nobilityPanelDimension);
		cardBoard.add(nobility);
		nobility.setLayout(null);
		nobility.setBounds(0, singleRegionDimension.height, nobilityPanelDimension.width, nobilityPanelDimension.height);
		
		ImagePanel actions = new ImagePanel(pathAction, new Dimension(510, 281));
		actions.setBounds(840, 425, 510, 281);
		contentPane.add(actions);
		actions.setPreferredSize(new Dimension(510, 281));
		actions.setLayout(null);
		
		JButton acquirePermit = new JButton("");
		acquirePermit.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
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
		buildEmporiumByKing.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
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
		electCouncillor.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
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
		buildEmporiumByPermit.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
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
		engageAssistant.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
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
		changeFaceUpPermits.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
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
		electCouncillorByAssistant.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
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
		extraMainAction.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		extraMainAction.setBackground(SystemColor.menu);
		extraMainAction.setBounds(265, 231, 245, 47);
		actions.add(extraMainAction);
		
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

	@Override
	public Request start() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateModel(Game game) {
		// TODO Auto-generated method stub
		
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
		JOptionPane.showInputDialog(message);
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
