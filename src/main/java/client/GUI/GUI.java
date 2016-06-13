package client.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.GridLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.FlowLayout;
import java.awt.Dialog.ModalExclusionType;

public class GUI extends JFrame {

	private JPanel completeMap;
	private final String pathLand="src/main/resources/Immagini/mareA.jpg";
	private final String pathHil="src/main/resources/Immagini/collinaA.jpg";
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

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 925, 650);
		completeMap = new JPanel();
		completeMap.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(completeMap);
		completeMap.setLayout(null);
		
		JPanel map = new JPanel();
		map.setBounds(0, 0, 900, 400);
		completeMap.add(map);
		map.setLayout(null);
		ImagePanel land=new ImagePanel(pathLand);
		land.setBounds(0, 0, 300, 400);
		land.setAutoscrolls(true);
		//JLabel land = new JLabel("REGIONE");
		map.add(land);
	
		JPanel hill=new ImagePanel(pathHil);
		hill.setBounds(300, 0, 300, 400);
		map.add(hill);
		
		JPanel mountain=new ImagePanel(pathMountain);
		mountain.setBounds(600, 0, 300, 400);
		map.add(mountain);
		
		JPanel nobility = new JPanel();
		nobility.setSize(900, 250);
		nobility.setLocation(0, 400);
		completeMap.add(nobility);
		nobility.setLayout(null);
		
		JPanel nobility1 =new ImagePanelNobility(pathNobility1);
		nobility1.setBounds(0, 0, 450, 250);
		nobility.add(nobility1);
		nobility1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JPanel nobility2 =new ImagePanelNobility(pathNobility2);
		nobility2.setBounds(450, 0, 450, 250);
		nobility.add(nobility2);
		nobility2.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
	}
	
	public BufferedImage getImage(String path) throws IOException{
		File file=new File(path);
		return ImageIO.read(file);
	}


}
