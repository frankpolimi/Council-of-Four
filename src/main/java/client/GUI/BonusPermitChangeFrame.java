package client.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import model.bonus.Bonus;
import model.game.BuildingPermit;
import model.game.Game;
import model.game.Player;
import model.game.topology.City;
import view.BonusRequest;
import view.LocalStorage;
import view.PermitsRequest;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.util.*;

public class BonusPermitChangeFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3576678380143630130L;
	private JPanel contentPane;
	private final static double XREF=683;
	private final static double YREF=384;
	private final LocalStorage memory;
	private Game game;
	private GUI view;
	private Object selected;

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
					game.getPlayerByID(1).setNobilityPoints(4);
					
					//BonusPermitChangeFrame frame = new BonusPermitChangeFrame(game, gui);
					//frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * if check flag is TRUE the request is a PermitRequest, otherwise is a BonusRequest
	 */
	public BonusPermitChangeFrame(Game game, GUI view, String title, String message, boolean check) {
		this.game=game;
		this.view=view;
		this.memory=view.getMemoryContainer();
				
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, screenSize.width/2, screenSize.height/2);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel(title);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds((int)((29/XREF)*getWidth()),(int)((11/YREF)*getHeight()),(int)((616/XREF)*getWidth()),(int)((32/YREF)*getHeight()));
		contentPane.add(titleLabel);
		
		JLabel messageLabel = new JLabel(message);
		messageLabel.setBounds(29, 51, 616, 32);
		messageLabel.setBounds((int)((29/XREF)*getWidth()),(int)((51/YREF)*getHeight()),(int)((616/XREF)*getWidth()),(int)((32/YREF)*getHeight()));
		contentPane.add(messageLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 100, 647, 180);
		panel.setBounds((int)((10/XREF)*getWidth()),(int)((100/YREF)*getHeight()),(int)((647/XREF)*getWidth()),(int)((180/YREF)*getHeight()));
		contentPane.add(panel);
		this.populatePanel(panel, new Dimension((int)((175/YREF)*getHeight()),(int)((175/YREF)*getHeight())), check);
		
		JButton btnSend = new JButton("SEND");
		btnSend.setBounds((int)((10/XREF)*getWidth()),(int)((291/YREF)*getHeight()),(int)((89/XREF)*getWidth()),(int)((23/YREF)*getHeight()));
		contentPane.add(btnSend);
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				super.mouseClicked(e);
				if(selected==null){
					JOptionPane.showMessageDialog(null, "You have to choise at least one object to apply the request", "Error", JOptionPane.ERROR_MESSAGE);
				}else{
					if(JOptionPane.showConfirmDialog(null, "Do you want to confirm your choise?", "Action", JOptionPane.YES_NO_OPTION)==0){
						if(check){
							PermitsRequest request=new PermitsRequest(view.getId());
							request.addPermit((BuildingPermit)selected);
							view.setRequest(request);
						}else{
							BonusRequest request=new BonusRequest(view.getId());
							if(selected.getClass().equals(BuildingPermit.class)){
								for(Bonus b:((BuildingPermit)selected).getBonusList()){
									request.addBonus(b);
								}
								view.setRequest(request);
							}else{
								for(Bonus b:((City)selected).getBonus()){
									request.addBonus(b);
								}
								view.setRequest(request);
							}
						}
						view.getMemoryContainer().setBonus(new ArrayList<>());
						view.getMemoryContainer().setPermits(new ArrayList<>());
						setVisible(false);
					}
				}
			}
		});
		
	}
	
	public void populatePanel(JPanel panel, Dimension dim, boolean check){
		List<?> list;
		if(check){
			list=memory.getPermits();
		}else{
			list=memory.getBonus();
		}
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		for(Object obj:list){
			JLabel label;
			if(obj.getClass().equals(BuildingPermit.class)){
				label=new ImageLabel(((BuildingPermit)obj).getImagePath(),dim);

				panel.add(label);
				label.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						super.mouseClicked(e);
						selected=obj;
						for(Component comp:panel.getComponents()){
							((JLabel)comp).setBorder(null);
						}
						label.setBorder(new LineBorder(Color.red,2));
					}
				});
			}else{
				if(((City)obj).getBonusImagePath()!=null){
					label=new ImageLabel(((City)obj).getBonusImagePath(),dim);

					panel.add(label);
					label.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							// TODO Auto-generated method stub
							super.mouseClicked(e);
							selected=obj;
							for(Component comp:panel.getComponents()){
								((JLabel)comp).setBorder(null);
							}
							label.setBorder(new LineBorder(Color.red,2));
						}
					});
				}
					
			}

		}
	}
}
