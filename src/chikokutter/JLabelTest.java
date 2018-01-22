package chikokutter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JLabelTest {

	public static void main(String[] args) {
		JFrame f =new JFrame(JLabelTest.class.toString());
		final JTextField label = new JTextField("A quick brown fox jumps over the lazy dog.");
		f.getContentPane().add(label);
		label.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				label.setScrollOffset(label.getScrollOffset()+1);
				
			}
		});
		
		f.setSize(100,50);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
