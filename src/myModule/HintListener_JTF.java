package myModule;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.*;
import java.awt.*;

public class HintListener_JTF implements FocusListener {

	private JTextField jtf;
	private String InfoHint;

	public HintListener_JTF(JTextField jtf, String InfoHint) {
		this.jtf = jtf;
		this.InfoHint = InfoHint;
		jtf.setForeground(Color.GRAY);
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (jtf.getText().trim().equals(InfoHint)) {// 如果是提示文字 ,就清空提示文字
			jtf.setText("");
			jtf.setForeground(Color.BLACK);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (jtf.getText().trim().equals("")) {// 如果没有任何输入 ,就提示用户输入
			jtf.setText(InfoHint);
			jtf.setForeground(Color.GRAY);
		}
	}
}
