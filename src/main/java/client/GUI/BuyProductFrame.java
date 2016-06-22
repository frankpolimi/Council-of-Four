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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import model.game.BuildingPermit;
import model.game.Game;
import model.game.Player;
import model.game.politics.PoliticsCard;
import model.market.Assistant;
import model.market.MarketObject;
import view.MarketBuyingState;
import view.MarketRequest;
import view.MarketSellingState;

public class BuyProductFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3572572155370251491L;
	private JPanel contentPane;
	private Game game;
	private GUI view;
	private MarketObject<?> objToBuy;
	private int value;
	private final static double XREF=683;
	private final static double YREF=384;

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
					players.add(new Player("fra",2));
					game.setPlayers(players);
					
					
					List<PoliticsCard> p1=game.getPlayerByID(1).getCardsOwned();
					List<PoliticsCard> p2=game.getPlayerByID(2).getCardsOwned();
					game.getMarket().addProduct(new MarketObject<>(p1.get(0), game.getPlayerByID(1), 10));
					game.getMarket().addProduct(new MarketObject<>(p1.get(3), game.getPlayerByID(1), 20));
					game.getMarket().addProduct(new MarketObject<>(new Assistant(1), game.getPlayerByID(1), 5));
					game.setCurrentPlayer(game.getPlayerByID(2));
					game.getMarket().addProduct(new MarketObject<>(p2.get(0), game.getPlayerByID(2), 3));
					game.getMarket().addProduct(new MarketObject<>(p2.get(2), game.getPlayerByID(2), 5));
					game.setGameState(new MarketBuyingState());
					GUI gui=new GUI();
					gui.setId(2);
					gui.setGame(game);
					BuyProductFrame frame = new BuyProductFrame(game,gui);
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
	public BuyProductFrame(Game game, GUI view) {
		this.value=-1;
		this.game=game;
		this.view=view;
		Player thisPlayer=game.getPlayerByID(view.getId());
				
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, screenSize.width/2, screenSize.height/2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel productPanel = new JPanel();
		productPanel.setBounds((int)((10/XREF)*getWidth()),(int)((45/YREF)*getHeight()),(int)((647/XREF)*getWidth()),(int)((145/YREF)*getHeight()));
		contentPane.add(productPanel);
		this.showProducts(thisPlayer, productPanel);
		JScrollPane scrollPane=new JScrollPane(productPanel);
		scrollPane.setBounds(productPanel.getBounds());
		contentPane.add(scrollPane);
		
		JLabel labelPrice = new JLabel("Price:");
		labelPrice.setBounds((int)((10/XREF)*getWidth()),(int)((224/YREF)*getHeight()),(int)((57/XREF)*getWidth()),(int)((35/YREF)*getHeight()));
		contentPane.add(labelPrice);
		
		JLabel priceValue=new JLabel();
		priceValue.setBounds((int)((55/XREF)*getWidth()),(int)((224/YREF)*getHeight()),(int)((57/XREF)*getWidth()),(int)((35/YREF)*getHeight()));
		contentPane.add(priceValue);
		priceValue.setName("priceValue");
				
		JButton btnSEND = new JButton("SEND");
		btnSEND.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {					
				
				if(objToBuy==null){
					JOptionPane.showMessageDialog(null, "You have to choise one item to sell", "Not Valid Insertion", JOptionPane.ERROR_MESSAGE);
				}else{
					if(JOptionPane.showConfirmDialog(null, "Do you confirm your buying?", "Confirmation", JOptionPane.YES_NO_OPTION)==0){
						view.setRequest(new MarketRequest<>(objToBuy, view.getId()));
						setVisible(false);
					}
				}

				
			}
		});
		btnSEND.setBounds((int)((10/XREF)*getWidth()),(int)((280/YREF)*getHeight()),(int)((89/XREF)*getWidth()),(int)((23/YREF)*getHeight()));
		contentPane.add(btnSEND);
		
		
		JLabel lblAddTheProduct = new JLabel("SELECT THE PRODUCT YOU WANT TO BUY");
		lblAddTheProduct.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddTheProduct.setBounds((int)((83/XREF)*getWidth()),(int)((11/YREF)*getHeight()),(int)((499/XREF)*getWidth()),(int)((20/YREF)*getHeight()));
		contentPane.add(lblAddTheProduct);
		
	}

	private void showProducts(Player thisPlayer, JPanel productPanel) {
		productPanel.setLayout(new BoxLayout(productPanel,BoxLayout.X_AXIS));
		Dimension cardDim=new Dimension((int)((90/XREF)*getWidth()),(int)((140/YREF)*getHeight()));
		Dimension permitDim=new Dimension((int)((124/XREF)*getWidth()),(int)((140/YREF)*getHeight()));
		Dimension assistantDim=new Dimension((int)((61/XREF)*getWidth()),(int)((140/YREF)*getHeight()));
		List<MarketObject<?>> objectList=this.game.getMarket().getAvailableProducts(thisPlayer);
		
		for(MarketObject<?> marketObject:objectList){
			JLabel objectLabel;
			Object object;
			if(marketObject.getObject().getClass().equals(Assistant.class)){
				objectLabel=new ImageLabel("src/main/resources/Immagini/Assistant.jpg",assistantDim);
				object=new Assistant(1);
			}else if(marketObject.getObject().getClass().getSuperclass().equals(PoliticsCard.class)){
				object=(PoliticsCard)marketObject.getObject();
				objectLabel=new ImageLabel(((PoliticsCard)object).getImagePath(), cardDim);
			}else{
				object=(BuildingPermit)marketObject.getObject();
				objectLabel=new ImageLabel(((BuildingPermit)object).getImagePath(), permitDim);
			}
			productPanel.add(objectLabel);
			objectLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					objToBuy=marketObject;
					for(Component c:productPanel.getComponents()){
						((JLabel)c).setBorder(null);
					}
					objectLabel.setBorder(new LineBorder(Color.YELLOW,2));
					value=marketObject.getPrice();
					((JLabel)(view.getComponentByName("priceValue", contentPane))).setText(String.valueOf(value));;
				}
			});
		}
	}

}
