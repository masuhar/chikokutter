package exp;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;

//import org.omg.PortableInterceptor.SUCCESSFUL;

public class SmoothScrollTest {

	private static final class MyTableModel extends AbstractTableModel {
		private int rows = 30;

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return String.format("(%d, %d)", rowIndex, columnIndex);
		}

		@Override
		public int getRowCount() {
			return rows;
		}

		@Override
		public int getColumnCount() {
			return 3;
		}
	}

	public static Rectangle getRowBounds(JTable table, int row) {
		Rectangle result = table.getCellRect(row, -1, true);
		Insets i = table.getInsets();

		result.x = i.left;
		result.width = table.getWidth() - i.left - i.right;

		return result;
	}

	public static void main(String[] args) {
		JFrame f = new JFrame("smooth scroll test");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1000, 200);
		final AbstractTableModel model = new MyTableModel();
		final JTable table = new JTable(model);
		final JScrollPane sp = new JScrollPane(table);
		// sp.getVerticalScrollBar().setUnitIncrement(1);
		// sp.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
		f.getContentPane().add(sp, BorderLayout.CENTER);

		JButton startScrollButton = new JButton("scroll");
		startScrollButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					public void runByViewport() {
						final int rows = model.getRowCount();

						JViewport vp = sp.getViewport();
						for (int r = 0; r < rows; r++) {
							// table.scrollRectToVisible(table.getCellRect(r, 0,
							// true));
							vp.setViewPosition(new Point(0, r));
							System.err.println(vp);
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

					public void runByScrollRectToVisible() {
						final int rows = model.getRowCount();
						for (int r = 0; r < rows; r++) {
							table.scrollRectToVisible(table.getCellRect(r, 0,
									true));
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

					@Override
					public void run() {
						int targetRow = model.getRowCount();
						System.err.println("targetRow=" + targetRow);
						Rectangle rb = getRowBounds(table, targetRow);
						System.err.println("bound=" + rb);
						final JViewport vp = sp.getViewport();
						System.err.println("vp.viewPosition="
								+ vp.getViewPosition());
						System.err.println("vp.viewRect=" + vp.getViewRect());
						// vp.setViewPosition(new Point(0,rb.y));

						final int targetViewPositionY = rb.y + rb.height
								- vp.getViewRect().height;
						final int startViewPositionY = vp.getViewPosition().y;
						final long startTime = System.currentTimeMillis();
						final int timeLength = 10 * 1000;
						final long endTime = startTime + timeLength;
						final Timer timer = new Timer(33, null);
						timer.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								long ct = System.currentTimeMillis();
								if (endTime <= ct) {
									Point p = new Point(
											0,
											targetViewPositionY);
									System.err.println("setViewposition to " + p);
									vp.setViewPosition(p);
									timer.setRepeats(false);
								} else {
								int elapsed = (int) (ct - startTime);
								Point p = new Point(
										0,
										(targetViewPositionY - startViewPositionY)
												* elapsed
												/ timeLength
												+ startViewPositionY);
								System.err.println("setViewposition to " + p);
								vp.setViewPosition(p);
								}
							}
						});
						timer.start();
//						while ((System.currentTimeMillis()) < endTime) {
//
//							incrementalScroll.run();
//							try {
//								// TODO use SwingUtils.wait or something
//								Thread.sleep(100);
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//
//						}
//						Point p = new Point(0, targetViewPositionY);
//						System.err.println("setViewposition to " + p);
//						vp.setViewPosition(p);
						// for (int i=0; i <= 100; i++) {
						// Point p = new Point(0,
						// (targetViewPositionY-startViewPositionY)*i/100 +
						// startViewPositionY);
						// System.err.println("setViewposition to " + p);
						// vp.setViewPosition(p);
						// try {
						// //TODO use SwingUtils.wait or something
						// Thread.sleep(100);
						// } catch (InterruptedException e) {
						// // TODO Auto-generated catch block
						// e.printStackTrace();
						// }
						// }
						// Scrolling.scrollVertically(table,getRowBounds(table,
						// targetRow));
					}
				});

			}
		});
		f.getContentPane().add(startScrollButton, BorderLayout.SOUTH);

		f.setVisible(true);

	}

}
