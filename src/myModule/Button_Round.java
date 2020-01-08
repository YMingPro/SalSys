package myModule;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Icon;
import javax.swing.JButton;

public class Button_Round extends JButton {
	
	private static final long serialVersionUID = 1L;
	
	private Shape shape = null;
	
	private Color click = new Color(42, 145, 228);// 按下时的颜色
	private Color quit = new Color(64, 145, 228);// 离开时颜色

	private int b_weight = 20;
	private int b_height = 20;

	public Button_Round(String s) {
		super(s);
		setContentAreaFilled(false);// 是否显示外围矩形区域 选否
	}

	public Button_Round() {
		super();
	}

	public void setAngle(int w, int h) {
		b_weight = w;
		b_height = h;
	}

	public Button_Round(String text, Icon icon) {
		super(text, icon);
	}

	public Button_Round(Icon icon) {
		super(icon);
	}

	public void setColor(Color c, Color q) {
		click = c;
		quit = q;
	}

	public void paintComponent(Graphics g) {
		// 如果按下则为ＣＬＩＣＫ色 否则为 ＱＵＩＴ色
		if (getModel().isArmed()) {
			g.setColor(click);
		} else {
			g.setColor(quit);
		}
		// 填充圆角矩形区域 也可以为其它的图形
		g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, b_weight, b_height);
		// 必须放在最后 否则画不出来
		super.paintComponent(g);
	}

	public void paintBorder(Graphics g) {
		// 画边界区域
		g.setColor(click);
		g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, b_weight, b_height);
	}

	public boolean contains(int x, int y) {
		// 判断点（x,y）是否在按钮内
		if (shape == null || !(shape.getBounds().equals(getBounds()))) {
			shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), b_weight, b_height);
		}
		return shape.contains(x, y);
	}

}