package client.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import model.game.council.Councillor;
import model.game.topology.Region;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AcquirePermitGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 660009630551746911L;
	
	private JPanel contentPane;
	private Dimension monitorDimension=Toolkit.getDefaultToolkit().getScreenSize();
	private Dimension frameDimension=new Dimension(monitorDimension.width*9/10, monitorDimension.height*3/4); 
	
	public AcquirePermitGUI() {
		this.setSize(frameDimension);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		contentPane.setMinimumSize(frameDimension);
		contentPane.setBorder(new LineBorder(Color.BLACK));
		contentPane.setSize(frameDimension);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JButton btnSend = new JButton("Send");
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		btnSend.setSize(frameDimension.width*72/1000, frameDimension.height*399/10000);
		btnSend.setLocation(frameDimension.width*820/1000, frameDimension.height*873/1000);
		contentPane.add(btnSend);		
		/*
		JButton btnAbort = new JButton("Abort");
		btnAbort.setSize(frameDimension.width*72/1000, frameDimension.height*399/10000);
		btnAbort.setLocation(frameDimension.width*906/1000, frameDimension.height*873/1000);
		contentPane.add(btnAbort);
		*/
	}

	public void council(Set<Region> regions) {
		JPanel councils = new JPanel();
		Dimension councilsDim = new Dimension(frameDimension.width*987/1000, frameDimension.height/3);
		Dimension councilDimension = new Dimension(councilsDim.width*154/1000, councilsDim.height/2);
		councils.setBounds(0, 0, councilsDim.width, councilsDim.height);
		contentPane.add(councils);
		councils.setLayout(null);
			
		JButton region1 = new JButton();
		region1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(contentPane, "land council");
			}
		});
		region1.setName("land");
		region1.setSize(councilDimension);
		region1.setLocation(councilsDim.width*54/1000, councilsDim.height*146/1000);
		councils.add(this.paintCouncil(region1, 
					regions.stream().filter(e->e.getName()
							.equals(region1.getName())).findFirst().get()));
		region1.setContentAreaFilled(false);
		region1.setVisible(true);
		region1.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
		councils.add(region1);
		
		JButton region2 = new JButton();
		region2.setName("hill");
		region2.setSize(councilDimension);
		region2.setLocation(councilsDim.width*385/1000, councilsDim.height*146/1000);
		councils.add(this.paintCouncil(region2, 
					regions.stream().filter(e->e.getName()
							.equals(region2.getName())).findFirst().get()));
		region2.setContentAreaFilled(false);
		region2.setVisible(true);
		councils.add(region2);
		
		JButton region3 = new JButton();
		region3.setName("mountain");
		region3.setSize(councilDimension);
		region3.setLocation(councilsDim.width*745/1000, councilsDim.height*146/1000);
		councils.add(this.paintCouncil(region3, 
				regions.stream().filter(e->e.getName()
						.equals(region3.getName())).findFirst().get()));
		region3.setContentAreaFilled(false);
		region3.setBorder(new BevelBorder(0));
		region3.setVisible(true);
		councils.add(region3);
	}
	
	private JPanel paintCouncil(JButton button, Region region) {
		JPanel council = new JPanel();
		Iterator<Councillor> gameCouncillor = region.getCouncil().getCouncillors().iterator();
		Dimension councillorDimension = new Dimension(button.getWidth()/4, button.getHeight());
		council.setSize(button.getSize());
		council.setLocation(button.getLocation().x, button.getLocation().y);
		council.setLayout(null);
		council.setVisible(true);
		
		ImagePanel councillor1 = new ImagePanel(gameCouncillor.next().getImagePath(), councillorDimension);
		councillor1.setSize(councillorDimension);
		councillor1.setLocation(0,0);
		councillor1.setVisible(true);
		council.add(councillor1);
		ImagePanel councillor2 = new ImagePanel(gameCouncillor.next().getImagePath(), councillorDimension);
		councillor2.setSize(councillorDimension);
		councillor2.setLocation(button.getWidth()/4, 0);
		councillor2.setVisible(true);
		council.add(councillor2);
		ImagePanel councillor3 = new ImagePanel(gameCouncillor.next().getImagePath(), councillorDimension);
		councillor3.setSize(councillorDimension);
		councillor3.setLocation(button.getWidth()/2, 0);
		councillor3.setVisible(true);
		council.add(councillor3);
		ImagePanel councillor4 = new ImagePanel(gameCouncillor.next().getImagePath(), councillorDimension);
		councillor4.setSize(councillorDimension);
		councillor4.setLocation(button.getWidth()*3/4, 0);
		councillor4.setVisible(true);
		council.add(councillor4);
		return council;
	}

	private AbstractButton getRadioSelected(ButtonGroup group){
		if(group==null)
			throw new NullPointerException("Group is null");
		Enumeration<AbstractButton> buttons=group.getElements();
		AbstractButton button=buttons.nextElement();
		if(button.isSelected()) return button;
		while(buttons.hasMoreElements()){
			button= buttons.nextElement();
			if(button.isSelected()) return button;
		}
		return null;
	}
}
