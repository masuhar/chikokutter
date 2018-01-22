package chikokutter;

import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.Timer;

/**
 * @SmoothScroller provides a smoothly-scrolling function for a given JTable in
 * a JScrollPanel.
 * 
 * @author masuhara
 * 
 */
public class SmoothScroller implements ActionListener {

	// the next three fields are initialized by the constructor; this object is
	// created for each table/scrollPane pair.
	private JTable table;
	private JViewport viewPort;
	private Timer timer;

	// the following fields are initialized for each time scrolling is started
	private long startTime; // milliseconds of wall-clock when scrolling is
							// started.
	private int timeLength; // milliseconds of the time length of one scrolling
							// action.
	private int startViewPositionY; // top-left position of the table in
									// scrollPane when scrolling is started.
	private int targetViewPositionY; // top-left position of the table in
										// scrollPane when scrolling is ended.
	// private int counter;
	private static final int INTERVAL = 100; // minimum frequency of updating in
												// milliseconds

	/**
	 * to construct a scroller for the given table in a scrollPane.
	 * 
	 * @param table
	 * @param scrollPane
	 * @return
	 */
	public SmoothScroller(JTable table, JScrollPane scrollPane) {
		this.table = table;
		this.viewPort = scrollPane.getViewport();
		this.timer = new Timer(INTERVAL, null);
		timer.addActionListener(this);
		timer.setInitialDelay(INTERVAL);
		timer.setRepeats(true);
	}

	public static Rectangle getRowBounds(JTable table, int row) {
		Rectangle result = table.getCellRect(row, -1, true);
		Insets i = table.getInsets();

		result.x = i.left;
		result.width = table.getWidth() - i.left - i.right;

		return result;
	}

	/**
	 * to start smooth scrolling after timeLength, the table shall show the
	 * specified row at the bottom.
	 * 
	 * @param row
	 *            --- of the table to be shown at the bottom
	 * @param timeLength
	 *            --- the time span of the scrolling in milliseconds
	 */
	public void scrollTo(int row, final int timeLength) {
		// TODO stop scrolling, or recalculate scrolling when the window is
		// resized.
		// FIXME with many rows in the table, it makes the table jumps for the
		// first moment of the scrolling
		this.timeLength = timeLength;
		// System.err.println("targetRow =" +row);

		/*
		 *                        +------+ 
		 * startVPY-->+---+ ......+------+ 
		 *            |   |       +------+
		 * targetVPY->|...|       +------+ 
		 *            |   |       +------+ 
		 *            +---+ ......+------+ 
		 *            :   :       +------+ <-- rb.y 
		 *            :...:       +------+ <-- rb.y+rb.height 
		 *            viewPort     table
		 */

		Rectangle rb = getRowBounds(table, row);
		// System.err.println("rowBound=" +rb);
		this.targetViewPositionY = rb.y + rb.height
				- viewPort.getViewRect().height;
//		System.err.println("targetViewPositionY=" + targetViewPositionY);
		this.startViewPositionY = viewPort.getViewPosition().y;
		// when the calculated targetViewPoisitonY is above (i.e., 
		// less than) startViewPoisitionY, the bottom of the added
		// rows are already shown in the view.  We don't scroll
		// in this case.
		this.targetViewPositionY =
				Math.max(this.startViewPositionY, targetViewPositionY);
		try { 
			int interval = Math.max(timeLength
				/ (targetViewPositionY - startViewPositionY), INTERVAL);
//		System.err.println("interval = " + interval);
		this.startTime = System.currentTimeMillis();
		// this.counter = 0;
		timer.setDelay(interval);
		timer.start();
		} catch (ArithmeticException e) {
			; // targetViewPositionY - startViewPositionY = 0
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 * to scroll down the scrollPane a bit for each time step.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		long ct = System.currentTimeMillis();
		// System.err.format("currentTime=%d\nendTime    =%d\t", ct, endTime);
		// counter++;
		long elapsed = ct - startTime;
		setRelativeViewPosition(Math.min((int) elapsed, timeLength), timeLength);
		if (timeLength <= elapsed)
			timer.stop();
	}

	/**
	 * to set the vertical position of the table; the position is specified by a
	 * rational number number/denom. When the number is zero, the position is
	 * startViewPositionY. When the number one, the position is targetPositionY.
	 * 
	 * @param numer
	 * @param denom
	 */
	private void setRelativeViewPosition(int numer, int denom) {
		Point p = new Point(0, (targetViewPositionY - startViewPositionY)
				* numer / denom + startViewPositionY);
//		System.err.format("setViewposition from %s to %s\n",
//				viewPort.getViewPosition(), p);
		viewPort.setViewPosition(p);
	}
}
