package client.GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import java.awt.Graphics;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.GridBagLayout;

public class GUI extends JFrame {
	

	private JPanel contentPane;
	private final String pathLand="src/main/resources/Immagini/mareA.jpg";
	private final String pathHill="src/main/resources/Immagini/collinaA.jpg";
	private final String pathMountain="src/main/resources/Immagini/montagnaA.jpg";
	private final String pathNobility1="src/main/resources/Immagini/nobiltaA.jpg";
	private final String pathNobility2="src/main/resources/Immagini/nobiltaB.jpg";

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
		Dimension monitorDimension=Toolkit.getDefaultToolkit().getScreenSize();
		Dimension cardBoardDimension=new Dimension((monitorDimension.width/160*105), (monitorDimension.height));
		Dimension regionPanelDimension=new Dimension(cardBoardDimension.width, cardBoardDimension.height/90*63);
		Dimension singleRegionDimension=new Dimension(regionPanelDimension.width/3, regionPanelDimension.height);
		Dimension nobilityPanelDimension=new Dimension(cardBoardDimension.width, cardBoardDimension.height/90*37);
		
		this.setSize(monitorDimension);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setBorder(new LineBorder(Color.BLACK));
		contentPane.setSize(monitorDimension);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel cardBoard = new JPanel();
		cardBoard.setLocation(1, 1);
		cardBoard.setSize(new Dimension(1348, 728));
		cardBoard.setBorder(new LineBorder(Color.BLACK));
		contentPane.add(cardBoard);
		cardBoard.setLayout(null);
		
		JPanel regions=new JPanel();
		regions.setLocation(1, 1);
		regions.setSize(new Dimension(1346, 367));
		regions.setAutoscrolls(true);
		cardBoard.add(regions);
		regions.setLayout(null);
	
		ImagePanel seaside=new ImagePanel(pathLand, singleRegionDimension);
		seaside.setLocation(0, 0);
		seaside.setSize(new Dimension(448, 367));
		regions.add(seaside);
		seaside.setLayout(null);
		
		ImagePanel hill=new ImagePanel(pathHill, singleRegionDimension);
		hill.setLocation(448, 0);
		hill.setSize(new Dimension(448, 367));
		regions.add(hill);
		hill.setLayout(null);
		
		ImagePanel mountain=new ImagePanel(pathMountain, singleRegionDimension);
		mountain.setLocation(896, 0);
		mountain.setSize(new Dimension(448, 367));
		regions.add(mountain);
		mountain.setLayout(null);
		
		ImagePanel nobility = new ImagePanel(pathNobility1, nobilityPanelDimension);
		nobility.setLocation(1, 368);
		nobility.setSize(new Dimension(1346, 358));
		cardBoard.add(nobility);		
		nobility.setLayout(new BoxLayout(nobility, BoxLayout.X_AXIS));
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


}
