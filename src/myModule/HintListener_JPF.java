package myModule;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JPasswordField;

public class HintListener_JPF implements FocusListener {

	private JPasswordField jpf;
	private String InfoHint;
	private char defaultChar;

	public HintListener_JPF(JPasswordField jpf, char defaultChar, String InfoHint) {
		this.jpf = jpf;
		this.defaultChar = defaultChar;
		this.InfoHint = InfoHint;
		jpf.setForeground(Color.GRAY);
	}

	@Override
	public void focusGained(FocusEvent e) {
		String pswd = new String(jpf.getPassword()).trim();
		if (pswd.equals(InfoHint)) {
			jpf.setText("");
			jpf.setEchoChar(defaultChar);
			jpf.setForeground(Color.BLACK);
		}
	}

	@Override
	public void focusLost(FocusEvent e) {
		String pswd = new String(jpf.getPassword()).trim();
		if (pswd.equals("")) {// 如果没有输入密码. 就用明文 提示用户输入密码
			jpf.setEchoChar('\0');
			jpf.setText(InfoHint);
			jpf.setForeground(Color.GRAY);
		}
	}
}
