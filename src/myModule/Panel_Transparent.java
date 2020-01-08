package myModule;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

//设置实现半透明的JPanel
public class Panel_Transparent extends JPanel {

	private static final long serialVersionUID = 1L;

	private float transparency;

	public Panel_Transparent(float transparency) {
		this.transparency = transparency;
	}

	public void setTransparent(float transparency) {
		this.transparency = transparency;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics2d = (Graphics2D) g.create();
		graphics2d.setComposite(AlphaComposite.SrcOver.derive(transparency));
		graphics2d.setColor(getBackground());
		graphics2d.fillRect(0, 0, getWidth(), getHeight());
		graphics2d.dispose();
	}

}
