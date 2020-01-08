package myModule;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

//设置主页背景图片的JPanel类
public class Panel_Home extends JPanel {

	private static final long serialVersionUID = 1L;

	ImageIcon icon;
	Image img;

	public Panel_Home(String s) {
		icon = new ImageIcon(ClassLoader.getSystemResource(s)); 
//		icon = new ImageIcon(ClassLoader.getSystemResource(s)); 
		img = icon.getImage();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
	}

}