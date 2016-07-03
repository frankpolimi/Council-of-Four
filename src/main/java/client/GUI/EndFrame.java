package client.GUI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import model.game.Game;
import model.game.Player;
import model.game.PointsTile;
import view.EndState;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JButton;

public class EndFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5693396560194662127L;
	private JPanel contentPane;
	private Game game;
	private GUI view;
	private final static double XREF=683;
	private final static double YREF=384;
	private JTable rankingTable;

	/**
	 * Create the frame.
	 */
	public EndFrame(Game game, GUI view) {
		this.game=game;
		this.view=view;
		Player winner=((EndState)game.getGameState()).getWinner();
		List<Player> ranking=((EndState)game.getGameState()).getRanking();
				
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(100, 100, screenSize.width/2, screenSize.height/2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTheWinnerIs = new JLabel("THE WINNER IS");
		lblTheWinnerIs.setHorizontalAlignment(SwingConstants.CENTER);
		lblTheWinnerIs.setBounds((int)((10/XREF)*getWidth()),(int)((11/YREF)*getHeight()),(int)((647/XREF)*getWidth()),(int)((26/YREF)*getHeight()));
		contentPane.add(lblTheWinnerIs);
		
		JLabel winnerLabel = new JLabel(winner.getName()+" "+winner.getPlayerID()+" "+winner.getPoints());
		winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		winnerLabel.setBounds((int)((10/XREF)*getWidth()),(int)((35/YREF)*getHeight()),(int)((647/XREF)*getWidth()),(int)((50/YREF)*getHeight()));
		contentPane.add(winnerLabel);
		
		this.populateTable(ranking);
		rankingTable.setFillsViewportHeight(true);
		rankingTable.setBounds((int)((10/XREF)*getWidth()),(int)((96/YREF)*getHeight()),(int)((647/XREF)*getWidth()),(int)((201/YREF)*getHeight()));
		contentPane.add(rankingTable);
		JScrollPane scroll=new JScrollPane(rankingTable);
		scroll.setBounds(rankingTable.getBounds());
		rankingTable.setFillsViewportHeight(true);
		contentPane.add(scroll);
		
		JButton btnClose = new JButton("CLOSE");
		btnClose.setBounds((int)((10/XREF)*getWidth()),(int)((308/YREF)*getHeight()),(int)((89/XREF)*getWidth()),(int)((23/YREF)*getHeight()));
		contentPane.add(btnClose);
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				JOptionPane.showMessageDialog(null, "Goodbye!");
				view.setVisible(false);
				setVisible(false);
			}
		});
		
	}
	
	public void populateTable(List<Player> ranking){
		Vector<String> name=new Vector<>();
		name.add("Position");
		name.add("ID");
		name.add("Name");
		name.add("NP");
		name.add("Permits");
		name.add("Ass&Cards");
		name.add("Tiles' Points");
		name.add("Points");
		name.add("TOTAL");
		
		Vector<Vector<Object>> content=new Vector<>();
		for(int i=0;i<ranking.size();i++){
			Vector<Object> thisPlayer=new Vector<>();
			Player current=game.getPlayerByID(ranking.get(i).getPlayerID());
			thisPlayer.add(i+1);
			thisPlayer.add(current.getPlayerID());
			thisPlayer.add(current.getName());
			thisPlayer.add(current.getNobilityPoints());
			thisPlayer.add(current.howManyBuildingPermitsOwned());
			thisPlayer.add(current.howManyAssistansAndCardsOwned());
			int tilePoints=0;
			for(PointsTile tile:current.getTilesOwned()){
				tilePoints+=tile.getVPs();
			}
			thisPlayer.add(tilePoints);
			thisPlayer.add(ranking.get(i).getPoints());
			content.add(thisPlayer);
		}
		
		rankingTable=new JTable(new DefaultTableModel(content, name));
	}
}
