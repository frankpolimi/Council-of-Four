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

import model.game.BuildingPermit;
import model.game.Game;
import model.game.Player;
import model.game.politics.PoliticsCard;
import model.market.Assistant;
import model.market.MarketObject;
import view.MarketRequest;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;

public class AddProductFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2423042084984267944L;
	private JPanel contentPane;
	private Object game;
	private Object view;
	private Object objToSell;
	private int cost;
	private JTextField textField;
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
					game.setPlayers(players);
					GUI gui=new GUI();
					gui.setId(1);
					gui.setGame(game);
					AddProductFrame frame = new AddProductFrame(game,gui);
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
	public AddProductFrame(Game game, GUI view) {
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
		
		textField = new JTextField();
		textField.setBounds((int)((42/XREF)*getWidth()),(int)((231/YREF)*getHeight()),(int)((86/XREF)*getWidth()),(int)((20/YREF)*getHeight()));
		contentPane.add(textField);
		textField.setColumns(10);
		
		
		JButton btnSEND = new JButton("SEND");
		btnSEND.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {		
				int value=-2;
				try{
					value=Integer.parseInt(textField.getText());
				}catch(NumberFormatException g){
					JOptionPane.showMessageDialog(null, "You've inserted a not valid value", "Not Valid Insertion", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(value<0){
					JOptionPane.showMessageDialog(null, "You've inserted a negative value", "Not Valid Insertion", JOptionPane.ERROR_MESSAGE);
				}else{
					if(objToSell==null){
						JOptionPane.showMessageDialog(null, "You have to choise one item to sell", "Not Valid Insertion", JOptionPane.ERROR_MESSAGE);
					}else{
						if(JOptionPane.showConfirmDialog(null, "Do you confirm your adding?", "Confirmation", JOptionPane.YES_NO_OPTION)==0){
							view.setRequest(new MarketRequest<>(new MarketObject<>(objToSell, thisPlayer, value), view.getId()));
							setVisible(false);
						}
					}
				}
				
			}
		});
		btnSEND.setBounds((int)((10/XREF)*getWidth()),(int)((280/YREF)*getHeight()),(int)((89/XREF)*getWidth()),(int)((23/YREF)*getHeight()));
		contentPane.add(btnSEND);
		
		
		JLabel lblAddTheProduct = new JLabel("ADD THE PRODUCT YOU WANT TO SELL");
		lblAddTheProduct.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddTheProduct.setBounds((int)((83/XREF)*getWidth()),(int)((11/YREF)*getHeight()),(int)((499/XREF)*getWidth()),(int)((20/YREF)*getHeight()));
		contentPane.add(lblAddTheProduct);
		
	}
	
	private void showProducts(Player player, JPanel panel){
		panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));
		Dimension cardDim=new Dimension((int)((90/XREF)*getWidth()),(int)((140/YREF)*getHeight()));
		Dimension permitDim=new Dimension((int)((124/XREF)*getWidth()),(int)((140/YREF)*getHeight()));
		Dimension assistantDim=new Dimension((int)((61/XREF)*getWidth()),(int)((140/YREF)*getHeight()));
		for(PoliticsCard card:player.getCardsOwned()){
			JLabel cardLabel=new ImageLabel(card.getImagePath(), cardDim);
			cardLabel.setSize(cardDim);
			panel.add(cardLabel);
			cardLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseClicked(e);
					objToSell=card;
					for(Component component:panel.getComponents()){
						((JLabel)component).setBorder(null);
					}
					cardLabel.setBorder(new LineBorder(Color.yellow,2));
				}
				
			});
		}
		
		for(BuildingPermit permit:player.getBuildingPermits()){
			JLabel permitLabel=new ImageLabel(permit.getImagePath(), permitDim);
			permitLabel.setSize(permitDim);
			panel.add(permitLabel);
			permitLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseClicked(e);
					objToSell=permit;
					for(Component component:panel.getComponents()){
						((JLabel)component).setBorder(null);
					}
					permitLabel.setBorder(new LineBorder(Color.yellow,2));
				}
				
			});
		}
		
		for(int i=0;i<player.getAssistants();i++){
			JLabel assistantLabel=new ImageLabel("src/main/resources/Immagini/assistant.jpg",assistantDim);
			assistantDim.setSize(assistantDim);
			panel.add(assistantLabel);
			assistantLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseClicked(e);
					objToSell=new Assistant(1);
					for(Component component:panel.getComponents()){
						((JLabel)component).setBorder(null);
					}
					assistantLabel.setBorder(new LineBorder(Color.yellow,2));
				}
				
			});
		}
		
	}
}
