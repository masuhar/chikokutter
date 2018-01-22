package chikokutter;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ComponentColorModel;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellRenderer;

//TODO show the full text of a tweet or an ID as a hovering rectangle when 
//the mouse cursor comes over a cell. 
public class StreamPanel extends JPanel implements TableModelListener {

	private TableStreamModel model;
	private JTable table;
	private JScrollPane scrollPane;
	public static final Font DEFAULT_FONT = new Font("Sans Serif", Font.PLAIN,
			12);

	StreamPanel() {
		String[] columnNames = { "Tweet", "id", "time" };

		model = new TableStreamModel(columnNames, 0);
		model.addTableModelListener(this);

		final TableCellRenderer rowRenderer = new TweetRenderer(model);
		table = new JTable(model) {

			@Override
			public TableCellRenderer getCellRenderer(int row, int column) {
				return rowRenderer;
				// return super.getCellRenderer(arg0, arg1);
			}
		};

		scrollPane = new JScrollPane(table);
		scrollPane.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				System.out.println("resizing widths...");
				StreamPanel.this.setColumnWidths();
			}
		});
		// change the widths of columns
		setColumnWidths();
		// let the table not to change the column widths automatically,
		// otherwise, the columns have the equal width.
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// I don't know what this does.
		table.setFillsViewportHeight(true);

		registerRightClickMenu();
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				Point p = e.getPoint();
				int r = table.rowAtPoint(p);
				table.setRowSelectionInterval(r, r);
				// TODO make a selection visible
			}

		});
		// set the layout
		this.setLayout(new BorderLayout());
		// show the table with scroll bars
		this.add(scrollPane, BorderLayout.CENTER);

	}

	// to register a right-click menu
	private void registerRightClickMenu() {
		// TODO change menu depending on the column
		JPopupMenu cellClickedMenu = new JPopupMenu();
		JMenuItem addToFilterMenu = new JMenuItem("Filter this ID");
		addToFilterMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				TweetI tweet = (TweetI) table.getModel().getValueAt(
						selectedRow, 1);
				System.err.println("filter this id:" + tweet.getId() + ":"
						+ tweet.getScreenName());
				model.filterThisID(tweet.getScreenName());
			}
		});
		cellClickedMenu.add(addToFilterMenu);
		JMenuItem browseThisIDMenu= new JMenuItem("Browse this ID");
		browseThisIDMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				TweetI tweet = (TweetI) table.getModel().getValueAt(
						selectedRow, 1);
				System.err.println("browsethis id:" + tweet.getId() + ":"
						+ tweet.getScreenName());
				// TODO instead of using an external browser, it 
				// should manually obtain recent tweets from the given user, 
				// and show them with interval and matching the pattern.
				URLOpener.openURL("https://twitter.com/"+tweet.getScreenName());
			}
		});
		cellClickedMenu.add(browseThisIDMenu);
		table.setComponentPopupMenu(cellClickedMenu);
	}

	private void setColumnWidths() {
		int idWidth = measureTextWidth("1234567890");
		table.getColumnModel().getColumn(1).setPreferredWidth(idWidth);
		int timeWidth = measureTextWidth("00:00");
		table.getColumnModel().getColumn(2).setPreferredWidth(timeWidth);
		int marignForVerticalScrollBar = 18;// TODO make this intelligent
		table.getColumnModel()
				.getColumn(0)
				.setPreferredWidth(
						getWidth() - idWidth - timeWidth
								- marignForVerticalScrollBar);
	}

	private int measureTextWidth(String text) {
		Graphics graphics = getGraphics();
		if (graphics == null)
			return text.length() * 10;
		// get metrics from the graphics
		FontMetrics metrics = graphics.getFontMetrics(DEFAULT_FONT);
		// get the height of a line of text in this
		// font and render context
		int hgt = metrics.getHeight();
		// get the advance of my text in this font
		// and render context
		int adv = metrics.stringWidth(text);
		// calculate the size of a box to hold the
		// text with some padding.
		// Dimension size = new Dimension(adv+2, hgt+2);
		return adv + 2;
	}

	public static void main(String[] args) {
		JFrame testFrame = new JFrame("stream panel test");
		testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		testFrame.getContentPane().add(new StreamPanel());
		testFrame.setSize(1000, 200);
		testFrame.setVisible(true);

	}

	@Override
	public void tableChanged(TableModelEvent e) {
		setNewRowsHeight();
		final int rows = model.getRowCount();
		int timeLength = 10 * 1000;
		getScroller().scrollTo(rows - 1, timeLength);
		// http://stackoverflow.com/questions/5956603/jtable-autoscrolling-to-bottom-problem
		// SwingUtilities.invokeLater(new Runnable() {
		// @Override
		// public void run() {
		// table.scrollRectToVisible(table.getCellRect(rows - 1, 0, true));
		// }
		// });
	}

	public SmoothScroller getScroller() {
		if (scroller == null)
			scroller = new SmoothScroller(table, scrollPane);
		return scroller;
	}

	int heightCheckRow = 0;
	private SmoothScroller scroller;

	// to minimize height of each newly added row if the tweet should be hidden.
	// TODO instead of using setRowHeight, we should use RowFilter or something
	// else in order to completely hide the rows. Current implementation uses
	// setRowHeight which can only set the height to 1 at the least, which
	// leaves a visible dark line where the hidden row had been shown otherwise.
	//
	// TODO it seems to cause flickering when scrolling.
	private void setNewRowsHeight() {
		for (; heightCheckRow < model.getRowCount(); heightCheckRow++) {
			TweetI tweet = (TweetI) model.getValueAt(heightCheckRow, 0);
			if (tweet.isHidden())
				table.setRowHeight(heightCheckRow, 1);// at least 1
		}

	}

	public TableStreamModel getModel() {
		return model;
	}

}
