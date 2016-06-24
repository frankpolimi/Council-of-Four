package client.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import model.actions.BuildEmporiumByPermit;
import model.game.BuildingPermit;
import model.game.Game;
import model.game.Player;
import model.game.topology.City;
import view.ActionRequest;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class BuildEmporiumByPermitFrame extends JFrame {

	/**
	 * @author Ricciardelli Emanuele
	 */
	private static final long serialVersionUID = 309794677806021420L;
	private JPanel contentPane;
	private Game game;
	private GUI view;
	private final static double XREF=683;
	private final static double YREF=384;
	private BuildingPermit selectedPermit;
	private City selectedCity;

	/**
	 * Launch the application.
	 */
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
					game.getPlayerByID(1).addBuildingPermit(game.getAllPermitsDecks().get(0).getBuildingPermitsDeck().get(2));
					game.getPlayerByID(1).addBuildingPermit(game.getAllPermitsDecks().get(1).getBuildingPermitsDeck().get(2));
					BuildEmporiumByPermitFrame frame = new BuildEmporiumByPermitFrame(game, gui);
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
	public BuildEmporiumByPermitFrame(Game game, GUI view) {
		this.game=game;
		this.view=view;
		Player thisPlayer=this.game.getPlayerByID(this.view.getId());
				
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, screenSize.width/2, screenSize.height/2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel buildingsPanel = new JPanel();
		buildingsPanel.setName("buildingsPanel");
		buildingsPanel.setBounds(10,89,647,212);
		buildingsPanel.setLayout(new BoxLayout(buildingsPanel,BoxLayout.Y_AXIS));
		contentPane.add(buildingsPanel);
		this.fillPermitPanel(buildingsPanel, thisPlayer);
		JScrollPane scroll=new JScrollPane(buildingsPanel);
		scroll.setBounds(buildingsPanel.getBounds());
		contentPane.add(scroll);
		
		JButton btnSEND = new JButton("SEND");
		btnSEND.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnSEND.setBounds((int)((568/XREF)*getWidth()),(int)((312/YREF)*getHeight()),(int)((89/XREF)*getWidth()),(int)((23/YREF)*getHeight()));
		contentPane.add(btnSEND);
		btnSEND.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				if(selectedCity==null){
					JOptionPane.showMessageDialog(null, "You have to choose the city in which you want to build", "No selection", JOptionPane.ERROR_MESSAGE);
				}else{
					if(JOptionPane.showConfirmDialog(null, "Do you want to confirm your choise?", "Confirmation", JOptionPane.YES_NO_OPTION)==0){
						view.setRequest(new ActionRequest(new BuildEmporiumByPermit(selectedPermit, selectedCity),view.getId()));
						setVisible(false);
					}
				}
			}
		});
		
		JLabel lblBuildAnEmporium = new JLabel("BUILD AN EMPORIUM USING A BUILDING PERMIT");
		lblBuildAnEmporium.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuildAnEmporium.setBounds(97,0,(int)((532/XREF)*getWidth()),(int)((23/YREF)*getHeight()));
		
		contentPane.add(lblBuildAnEmporium);
		
		JLabel label = new JLabel("Select the city in which you want to build it");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(164, 25, 417, 23);
		contentPane.add(label);
	}
	
	public void fillPermitPanel(JPanel permitPanel, Player player){
		permitPanel.setLayout(new BoxLayout(permitPanel,BoxLayout.X_AXIS));
		List<BuildingPermit> permits=player.getBuildingPermits();
		Dimension permitDim=new Dimension((int)((0.92)*((130/YREF)*getHeight())),(int)((130/YREF)*getHeight()));
		for(BuildingPermit permit:permits){
			JPanel container=new JPanel();
			container.setSize(new Dimension(permitDim.width,2*permitDim.height));
			container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
			JLabel permitLabel=new ImageLabel(permit.getImagePath(),permitDim);
			permitLabel.setSize(permitDim);
			container.add(permitLabel);
			JPanel cityPanel=new JPanel();
			cityPanel.setSize(permitDim);
			cityPanel.setName("cityPanel");
			cityPanel.setLayout(new BoxLayout(cityPanel,BoxLayout.Y_AXIS));
			container.add(cityPanel);
			for(City c:permit.getBuildingAvaliableCities()){
				JLabel cityLabel=new JLabel(c.getName());
				cityPanel.add(cityLabel);
				cityLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						super.mouseClicked(e);
						for(Component comp:permitPanel.getComponents()){
							JPanel otherContainer=(JPanel)comp;
							JPanel otherCityPanel=(JPanel)view.getComponentByName("cityPanel", otherContainer);
							for(Component comp2:otherCityPanel.getComponents()){
								((JLabel)comp2).setBorder(null);
							}
						}
						
						cityLabel.setBorder(new LineBorder(Color.red,2));
						selectedCity=c;
						selectedPermit=permit;
						
					}
				});
			}
			permitPanel.add(container);
		}
		
	}
}
	
	