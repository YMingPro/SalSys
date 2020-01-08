package myModule;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LimitWord_JTF extends PlainDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int limit; // 限制的长度

	public LimitWord_JTF(int limit) {
		super(); // 调用父类构造
		this.limit = limit;
	}

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null)
			return;
		// 下面的判断条件改为自己想要限制的条件即可，这里为限制输入的长度
		if ((getLength() + str.length()) <= limit) {
			super.insertString(offset, str, attr);// 调用父类方法
		}
	}

}
