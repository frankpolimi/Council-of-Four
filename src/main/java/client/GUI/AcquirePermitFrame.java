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
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import model.actions.AcquirePermit;
import model.game.BuildingPermit;
import model.game.Game;
import model.game.Player;
import model.game.council.Councillor;
import model.game.council.RegionalCouncil;
import model.game.politics.PoliticsCard;
import model.game.topology.Region;
import view.ActionRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SwingConstants;

public class AcquirePermitFrame extends JFrame {

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game game=new Game();
					List<Player> players=new ArrayList<>();
					players.add(new Player("ema",1));
					game.setPlayers(players);
					game.getPoliticsDeck().drawNCards(game.getPlayerByID(1));
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

	
	private final static double XREF=683;
	private final static double YREF=384;
	private final BuildEmporiumByKingFrame helper;
	private RegionalCouncil councilSelected;
	private BuildingPermit permitSelected;
	private ArrayList<PoliticsCard> cardsSelected;
	private Game game;
	private GUI gui;
	
	public AcquirePermitFrame(Game game, GUI gui) {
		this.game = game;
		this.gui = gui;
		Player current=game.getPlayerByID(gui.getId());
		helper=new BuildEmporiumByKingFrame(game, gui);
		setResizable(false);
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, screenSize.width/2, screenSize.height/2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		cardsSelected=new ArrayList<>();
		
		JButton btnSend = new JButton("Send");
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(permitSelected==null||councilSelected==null||cardsSelected.isEmpty()){
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
		
		JLabel lblAcquireAPermit = new JLabel("ACQUIRE A PERMIT");
		lblAcquireAPermit.setHorizontalAlignment(SwingConstants.CENTER);
		lblAcquireAPermit.setBounds((int)((54/XREF)*getWidth()),(int)((11/YREF)*getHeight()),(int)((564/XREF)*getWidth()),(int)((26/YREF)*getHeight()));
		contentPane.add(lblAcquireAPermit);
		
		JLabel lblSelectThePermit = new JLabel("Select the permit you want to acquire and select the cards you want to spend for corrumpting the council");
		lblSelectThePermit.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectThePermit.setBounds((int)((36/XREF)*getWidth()),(int)((34/YREF)*getHeight()),(int)((592/XREF)*getWidth()),(int)((26/YREF)*getHeight()));
		contentPane.add(lblSelectThePermit);
		
		Region land=game.getRegions().stream().filter(e->e.getName().equalsIgnoreCase("land")).findFirst().get();
		Region hill=game.getRegions().stream().filter(e->e.getName().equalsIgnoreCase("hill")).findFirst().get();
		Region mountain=game.getRegions().stream().filter(e->e.getName().equalsIgnoreCase("mountain")).findFirst().get();
		int space=(int)((204/XREF)*getWidth());
		this.createCouncilPanel(land, 0);
		this.createCouncilPanel(hill, space);
		this.createCouncilPanel(mountain, 2*space);
		
		JPanel cardsPanel = new JPanel();
		Dimension cardDimension=new Dimension((int)((46/XREF)*getWidth()), (int)((70/YREF)*getHeight()));
		cardsPanel.setBounds((int)((36/XREF)*getWidth()),(int)((243/YREF)*getHeight()),(int)((510/XREF)*getWidth()),(int)((92/YREF)*getHeight()));
		helper.populatesCardPanel(cardsPanel, current, cardDimension, this.cardsSelected);
		contentPane.add(cardsPanel);
		JScrollPane scroll=new JScrollPane(cardsPanel,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setBounds(cardsPanel.getBounds());
		contentPane.add(scroll);
		
	}

	private void createCouncilPanel(Region region, int space) {
			RegionalCouncil council=region.getCouncil();
			JPanel panel = new JPanel();
			panel.setName("panel"+region.getName());
			panel.setLayout(null);
			panel.setBounds((int)(((36+space)/XREF)*getWidth()),(int)((71/YREF)*getHeight()),(int)((160/XREF)*getWidth()),(int)((81/YREF)*getHeight()));
			helper.createCouncilPanel(panel, council.getCouncillors());
			for(Component c:panel.getComponents()){
				((JLabel)c).setBorder(null);
			}
			contentPane.add(panel);
			
			JPanel permitPanel = new JPanel();
			permitPanel.setName("permit"+region.getName());
			permitPanel.setLayout(new BoxLayout(permitPanel,BoxLayout.X_AXIS));
			permitPanel.setBounds((int)(((36+space)/XREF)*getWidth()),(int)((152/YREF)*getHeight()),(int)((160/XREF)*getWidth()),(int)((80/YREF)*getHeight()));
			Dimension permitDim=new Dimension(permitPanel.getWidth()/2,permitPanel.getHeight());
			for(BuildingPermit permit:region.getPermitsDeck().getFaceUpPermits()){
				JLabel permitLabel=new ImageLabel(permit.getImagePath(),permitDim);
				permitPanel.add(permitLabel);
				permitLabel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						super.mouseClicked(e);
						for(Region r:game.getRegions()){
							JPanel panel=(JPanel)gui.getComponentByName("panel"+r.getName(), contentPane);
							panel.setBorder(null);
							panel=(JPanel)gui.getComponentByName("permit"+r.getName(), contentPane);
							for(Component c:panel.getComponents()){
								((JLabel)c).setBorder(null);
							}
						}

						permitLabel.setBorder(new LineBorder(Color.red,2));
						panel.setBorder(new LineBorder(Color.red,2));
						councilSelected=council;
						permitSelected=permit;
					}
				});
			}
			contentPane.add(permitPanel);
	}
	
}
