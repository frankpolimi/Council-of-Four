package client.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import model.actions.ChangeFaceUpPermits;
import model.game.BuildingPermit;
import model.game.Game;
import model.game.PermitsDeck;
import model.game.Player;
import model.game.topology.Region;
import view.ActionRequest;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class ChangeFaceUpPermitsFrame extends JFrame {

	private JPanel contentPane;
	private Game game;
	private GUI view;
	private final static double XREF=683;
	private final static double YREF=384;
	private PermitsDeck selectedDeck;

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
					ChangeFaceUpPermitsFrame frame = new ChangeFaceUpPermitsFrame(game,gui);
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
	public ChangeFaceUpPermitsFrame(Game game, GUI view) {
		this.game=game;
		this.view=view;
					
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, screenSize.width/2, screenSize.height/2);
		setMaximumSize(new Dimension(screenSize.width/2,screenSize.height/2));
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Region land=game.getRegions().stream().filter(e->e.getName().equalsIgnoreCase("land")).findFirst().get();
		Region hill=game.getRegions().stream().filter(e->e.getName().equalsIgnoreCase("hill")).findFirst().get();
		Region mountain=game.getRegions().stream().filter(e->e.getName().equalsIgnoreCase("mountain")).findFirst().get();
		int space=((int)((222/XREF)*getWidth()));
		Dimension permitDim=new Dimension((int)((100/YREF)*getHeight()),(int)((100/YREF)*getHeight()));
		
		this.fillPermitsPanel(land, 0, permitDim);
		this.fillPermitsPanel(hill, space, permitDim);
		this.fillPermitsPanel(mountain, 2*space, permitDim);
		
		JLabel lblChangeFaceupspermits = new JLabel("Change the face up permits of one of the region showed below");
		lblChangeFaceupspermits.setHorizontalAlignment(SwingConstants.CENTER);
		lblChangeFaceupspermits.setBounds((int)((69/XREF)*getWidth()),(int)((11/YREF)*getHeight()),(int)((553/XREF)*getWidth()),(int)((21/YREF)*getHeight()));
		contentPane.add(lblChangeFaceupspermits);
		
		JLabel lblSelectOneOf = new JLabel("SELECT ONE OF THE PERMITS DECK TO APPLY THE CHANGE");
		lblSelectOneOf.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectOneOf.setBounds((int)((60/XREF)*getWidth()),(int)((38/YREF)*getHeight()),(int)((574/XREF)*getWidth()),(int)((44/YREF)*getHeight()));
		contentPane.add(lblSelectOneOf);
		
		JButton btnSend = new JButton("SEND");
		btnSend.setBounds((int)((22/XREF)*getWidth()),(int)((264/YREF)*getHeight()),(int)((89/XREF)*getWidth()),(int)((23/YREF)*getHeight()));
		contentPane.add(btnSend);
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				if(selectedDeck==null){
					JOptionPane.showMessageDialog(null, "You have to choise at least one deck", "Invalid value", JOptionPane.ERROR_MESSAGE);
				}else{
					if(JOptionPane.showConfirmDialog(null, "Do you confirm your choise?", "Confirmation", JOptionPane.YES_NO_OPTION)==0){
						view.setRequest(new ActionRequest(new ChangeFaceUpPermits(selectedDeck),view.getId()));
						setVisible(false);
					}
				}
			}
		});
		
	}
	
	public void fillPermitsPanel(Region region, int space, Dimension permitDim){

		JPanel panel = new JPanel();
		panel.setBounds((int)((22/XREF)*getWidth()+space),(int)((93/YREF)*getHeight()),(int)((200/XREF)*getWidth()),(int)((100/YREF)*getHeight()));
		contentPane.add(panel);
		
		panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
		for(BuildingPermit permit:region.getPermitsDeck().getFaceUpPermits()){
			JLabel permitLabel=new ImageLabel(permit.getImagePath(),permitDim);
			panel.add(permitLabel);
		}

		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				selectedDeck=region.getPermitsDeck();
				for(Component c:contentPane.getComponents()){
					if(c.getClass().equals(JLabel.class))
						((JLabel)c).setBorder(null);
					if(c.getClass().equals(JPanel.class))
						((JPanel)c).setBorder(null);
				}
				((JPanel)panel).setBorder(new LineBorder(Color.red,2));
			}
		});
		
		JLabel landLabel = new JLabel("Land's Permits");
		landLabel.setBounds((int)((22/XREF)*getWidth()+space),(int)((204/YREF)*getHeight()),(int)((200/XREF)*getWidth()),(int)((21/YREF)*getHeight()));
		contentPane.add(landLabel);
	}
}
