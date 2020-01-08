package myModule;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextArea;

public class HintListener_JTA implements FocusListener {

	private JTextArea jta;
	private String InfoHint;

	public HintListener_JTA(JTextArea jta, String InfoHint) {
		this.jta = jta;
		this.InfoHint = InfoHint;
		jta.setForeground(Color.GRAY);
	}

	@Override
	public void focusGained(FocusEvent e) {
		if (jta.getText().trim().equals(InfoHint)) {// 如果是提示文字 ,就清空提示文字
			jta.setText("");
			jta.setForeground(Color.BLACK);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (jta.getText().trim().equals("")) {// 如果没有任何输入 ,就提示用户输入
			jta.setText(InfoHint);
			jta.setForeground(Color.GRAY);
		}
	}
}