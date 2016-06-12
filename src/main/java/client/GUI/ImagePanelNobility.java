package client.GUI;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanelNobility extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3053617854127146582L;
	private Image image;
	
	public ImagePanelNobility(String path) {
		File file=new File(path);
		try {
			image=ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		image=this.getScaledImage(image, 450, 250);
		this.setLayout(new FlowLayout());
		this.setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		if(image==null)
			return;
		g.drawImage(image, 0, 0, null);
	}
	
	public Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
}
