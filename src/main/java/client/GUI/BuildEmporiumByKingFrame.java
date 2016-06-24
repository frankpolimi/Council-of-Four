package client.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import model.actions.BuildEmporiumByKing;
import model.game.Game;
import model.game.Player;
import model.game.council.Councillor;
import model.game.politics.PoliticsCard;
import model.game.topology.City;
import view.ActionRequest;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class BuildEmporiumByKingFrame extends JFrame {

	/**
	 * @author Ricciardelli Emanuele
	 */
	private static final long serialVersionUID = -5634262243332362516L;
	private final static double XREF=683;
	private final static double YREF=384;
	private JPanel contentPane;
	private Game game;
	private GUI view;
	private ArrayList<PoliticsCard> selectedCards;
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
					BuildEmporiumByKingFrame frame = new BuildEmporiumByKingFrame(game,gui);
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
	public BuildEmporiumByKingFrame(Game game, GUI view) {
		this.game=game;
		this.view=view;
		this.selectedCards=new ArrayList<>();
		Player thisPlayer=this.game.getPlayerByID(this.view.getId());
				
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, screenSize.width/2, screenSize.height/2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel cardsPanel = new JPanel();
		cardsPanel.setBounds((int)((10/XREF)*getWidth()),(int)((39/YREF)*getHeight()),(int)((647/XREF)*getWidth()),(int)((124/YREF)*getHeight()));
		contentPane.add(cardsPanel);
		cardsPanel.setName("cardsPanel");
		cardsPanel.setBorder(new LineBorder(Color.black));
		
		JLabel title = new JLabel("Build an emporium under the consence of the King ");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBounds((int)((97/XREF)*getWidth()),(int)((7/YREF)*getHeight()),(int)((489/XREF)*getWidth()),(int)((21/YREF)*getHeight()));
		contentPane.add(title);
		title.setName("title");
		
		
		JPanel kingCouncilPanel = new JPanel();
		kingCouncilPanel.setLayout(null);
		kingCouncilPanel.setName("king council");
		kingCouncilPanel.setBounds((int)((10/XREF)*getWidth()),(int)((174/YREF)*getHeight()),(int)((264/XREF)*getWidth()),(int)((88/YREF)*getHeight()));
		contentPane.add(kingCouncilPanel);
		kingCouncilPanel.setBorder(new LineBorder(Color.black));
		this.createCouncilPanel(kingCouncilPanel, 0, game.getKingsCouncil().getCouncillors());
		
		JButton btnSend = new JButton("SEND");
		btnSend.setBounds((int)((10/XREF)*getWidth()),(int)((278/YREF)*getHeight()),(int)((89/XREF)*getWidth()),(int)((23/YREF)*getHeight()));
		btnSend.setName("btnSend");
		contentPane.add(btnSend);
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(selectedCards.isEmpty()||selectedCity==null){
					JOptionPane.showMessageDialog(null, "You have to choise at least one and almost four cards and a city", "Error", JOptionPane.ERROR_MESSAGE);
				}else{
					if(JOptionPane.showConfirmDialog(null, "Do you want to confirm your choise?", "Action", JOptionPane.YES_NO_OPTION)==0){		
						view.setRequest(new ActionRequest(new BuildEmporiumByKing(game.getKingsCouncil(),selectedCards,selectedCity),view.getId()));
						setVisible(false);
					}		
				}
			}
		});
		
		
		
		JPanel cityNamePanel = new JPanel();
		cityNamePanel.setBounds((int)((311/XREF)*getWidth()),(int)((174/YREF)*getHeight()),(int)((346/XREF)*getWidth()),(int)((161/YREF)*getHeight()));
		contentPane.add(cityNamePanel);
		cityNamePanel.setBorder(new LineBorder(Color.black));
		this.createCityNameList(cityNamePanel, game.getAllCities());
		this.populatesCardPanel(cardsPanel, thisPlayer);
		JScrollPane scrollPane=new JScrollPane(cityNamePanel);
		scrollPane.setBounds(cityNamePanel.getBounds());
		contentPane.add(scrollPane);
		
		
	}
	
	
	private void populatesCardPanel(JPanel cardsPanel, Player player){
		Dimension cardDimension=new Dimension((int)((78/XREF)*getWidth()), (int)((120/YREF)*getHeight()));
		List<PoliticsCard> cardList=player.getCardsOwned();
		cardsPanel.setLayout(new BoxLayout(cardsPanel,BoxLayout.X_AXIS));
		for(PoliticsCard card:cardList){
			JLabel cardImage=new ImageLabel(card.getImagePath(),cardDimension);
			cardImage.setSize(cardDimension);
			cardsPanel.add(cardImage);
			cardsPanel.add(Box.createHorizontalStrut(5));
			cardImage.addMouseListener(new MouseAdapterImplementation(cardImage, card));
		}
	}
	
	private void createCouncilPanel(JPanel council, int x, ArrayBlockingQueue<Councillor> councillors){	
		//COUNCILLORS
		Dimension councillorInCouncilDim=new Dimension(council.getWidth()/4,council.getHeight());
				
		for(int i=0;i<councillors.size();i++){
			Councillor councillor=councillors.poll();
			JLabel panel=new ImageLabel(councillor.getImagePath(),councillorInCouncilDim);
			panel.setName("councillor"+0);
			panel.setBounds((council.getWidth()-(i+1)*councillorInCouncilDim.width), 0, councillorInCouncilDim.width, councillorInCouncilDim.height);
			panel.setBorder(new LineBorder(Color.black,1));
			try {
				councillors.put(councillor);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			council.add(panel);
		}
	}
	
	private void createCityNameList(JPanel c, List<City> cityList){
		c.setLayout(new BoxLayout(c,BoxLayout.Y_AXIS));
		for(City city:cityList){
			JLabel cityLabel=new JLabel(city.getName());
			c.add(cityLabel);
			cityLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					super.mouseClicked(e);
					for(Component component:c.getComponents()){
						((JLabel)component).setBorder(null);
					}
					cityLabel.setBorder(new LineBorder(Color.yellow,2));
					cityLabel.setBackground(Color.yellow);
					selectedCity=city;
				}
			});
		}
	}
	
	private class MouseAdapterImplementation extends MouseAdapter{
			
		private JLabel cardImage;
		private PoliticsCard card;
		public MouseAdapterImplementation(JLabel cardImage, PoliticsCard card) {
			this.cardImage=cardImage;
			this.card=card;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			if(cardImage.getBorder()==null){
				if(selectedCards.size()<4){
					cardImage.setBorder(new LineBorder(Color.yellow,2));
					selectedCards.add(card);
				}
			}else{
				cardImage.setBorder(null);
				selectedCards.remove(card);
			}
		}
		
	}
}


