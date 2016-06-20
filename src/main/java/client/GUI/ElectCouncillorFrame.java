package client.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import model.actions.ElectCouncillor;
import model.game.Game;
import model.game.council.Council;
import model.game.council.Councillor;
import model.game.council.KingsCouncil;
import model.game.council.RegionalCouncil;
import model.game.topology.Region;
import view.ActionRequest;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class ElectCouncillorFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7535216058942284080L;
	/**
	 * 
	 */
	
	private JPanel contentPane;
	private Game game;
	private GUI view;
	private Councillor councillorSelected;
	private Council councilSelected;
	private final static double XREF=683;
	private final static double YREF=384;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ElectCouncillorFrame frame = new ElectCouncillorFrame(new Game(), new GUI());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ElectCouncillorFrame(Game game, GUI view) {
		this.game=game;
		this.view=view;
		
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, screenSize.width/2, screenSize.height/2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Region land=game.getRegions().stream().filter(e->e.getName().equalsIgnoreCase("land")).findFirst().get();
		Region hill=game.getRegions().stream().filter(e->e.getName().equalsIgnoreCase("hill")).findFirst().get();
		Region mountain=game.getRegions().stream().filter(e->e.getName().equalsIgnoreCase("mountain")).findFirst().get();
		Dimension regionDimension=new Dimension((int)((119/XREF)*getWidth()), (int)((39/YREF)*getHeight()));
		int x=(int)((23/XREF)*getWidth());
		this.createCouncilPanel(land.getName(),land.getCouncil(), x, regionDimension);
		x+=(int)((160/XREF)*getWidth());
		this.createCouncilPanel(hill.getName(),hill.getCouncil(), x, regionDimension);
		x+=(int)((160/XREF)*getWidth());
		this.createCouncilPanel(mountain.getName(),mountain.getCouncil(), x, regionDimension);
		x+=(int)((160/XREF)*getWidth());
		this.createCouncilPanel("king", game.getKingsCouncil(), x, regionDimension);
		
		
		JPanel councillors = new JPanel();
		councillors.setBounds((int)((10/XREF)*getWidth()),(int)((48/YREF)*getHeight()),(int)((647/XREF)*getWidth()),(int)((110/YREF)*getHeight()));
		contentPane.add(councillors);
		councillors.setLayout(null);
		councillors.setName("councillors");
		councillors.setBorder(new LineBorder(Color.BLACK));
		
		this.createAvaliableCouncillors();
		
		JButton btnSend = new JButton("SEND");
		btnSend.setBounds((int)((23/XREF)*getWidth()),(int)((293/YREF)*getHeight()),(int)((89/XREF)*getWidth()),(int)((23/YREF)*getHeight()));
		contentPane.add(btnSend);
		btnSend.setText("btnSend");
		btnSend.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(councillorSelected==null||councilSelected==null){
					JOptionPane.showMessageDialog(null, "You have to choise one council and one councillor", "Error", JOptionPane.ERROR_MESSAGE);
				}else{
					if(JOptionPane.showConfirmDialog(null, "Do you want to confirm your choise?", "Action", JOptionPane.YES_NO_OPTION)==0){		
						view.setRequest(new ActionRequest(new ElectCouncillor(councillorSelected, councilSelected),view.getId()));
						setVisible(false);
					}		
				}
			}
		});
		
		JLabel lblElectANew = new JLabel("ELECT A NEW COUNCILLOR");
		lblElectANew.setBounds(258, 11, 139, 14);
		contentPane.add(lblElectANew);
		
		this.setVisible(true);
	}
	
	private void createCouncilPanel(String name,Council c, int x, Dimension regionDim){
		JPanel council1 = new JPanel();
		council1.setBounds((int)((x/XREF)*getWidth()), (int)((227/YREF)*getHeight()), regionDim.width, regionDim.height);
		contentPane.add(council1);
		council1.setName(name+"Council");
		council1.setBorder(new LineBorder(Color.BLACK));
		council1.setLayout(null);
		council1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				for(Component c:contentPane.getComponents()){
					if(c.getName()!=null&&c.getName().contains("Council"))
						for(Component c2:((JPanel)c).getComponents()){
							((JPanel)c2).setBorder(new LineBorder(Color.black,1));
						}
				}
					
				for(Component c:council1.getComponents()){
					((JPanel)c).setBorder(new LineBorder(Color.yellow,3));
				}
				
				councilSelected=c;
			}
			
		});
		
		//LABEL
		JLabel lblNewLabel = new JLabel(name+" Council");
		lblNewLabel.setBounds(x, (int)((202/YREF)*getHeight()), regionDim.width,(int)((14/YREF)*getHeight()));
		contentPane.add(lblNewLabel);
		
		//COUNCILLORS
		Dimension councillorInCouncilDim=new Dimension(council1.getWidth()/4,council1.getHeight());
		ArrayBlockingQueue<Councillor> councillors=c.getCouncillors();
		
		for(int i=0;i<councillors.size();i++){
			Councillor councillor=councillors.poll();
			JPanel panel=new JPanel();
			panel.setName("councillor"+0);
			panel.setBounds((regionDim.width-(i+1)*councillorInCouncilDim.width), 0, councillorInCouncilDim.width, councillorInCouncilDim.height);
			panel.setBorder(new LineBorder(Color.black,1));
			panel.setBackground(councillor.getColor());
			try {
				councillors.put(councillor);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			council1.add(panel);
		}
		
	}
	
	private void createAvaliableCouncillors(){
		List<Councillor> councillors=game.getAvaliableCouncillor();
		Dimension councillorDim=new Dimension((int)((30/XREF)*getWidth()),(int)((100/YREF)*getHeight()));
		JPanel councillorPanel=(JPanel)(Arrays.asList(contentPane.getComponents()).stream().filter(e->e.getName()!=null&&e.getName().equals("councillors")).findFirst().get());
		
		int spaceX=(int)((5/XREF)*getWidth());
		int spaceY=(int)((5/YREF)*getHeight());
		int distanceX=spaceX;
		for(Councillor c:councillors){
			JPanel councillor=new ImagePanel(c.getImagePath(),councillorDim);
			councillor.setBounds(distanceX,spaceY,councillorDim.width, councillorDim.height);
			distanceX+=spaceX+councillorDim.width;
			councillor.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mouseClicked(MouseEvent e) {
					for(Component c:councillorPanel.getComponents())
						((JPanel)c).setBorder(null);
					councillor.setBorder(new LineBorder(Color.yellow,2));
					councillorSelected=c;
				}
			});
			councillorPanel.add(councillor);
		}
	}
}
