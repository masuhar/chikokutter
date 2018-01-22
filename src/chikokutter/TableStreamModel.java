package chikokutter;
import javax.swing.table.DefaultTableModel;

import twitter4j.Status;


public class TableStreamModel  extends DefaultTableModel implements StreamModel {
	
	private static final int MAX_ROWS = 20;
	public TableStreamModel(Object[] columnNames, int rows) {
		super(columnNames, rows);
	}
	public void addTweet(Status _tweet) {
		TweetI tweet = new TweetWrapper(_tweet);
		addRow(new Object[]{tweet,tweet,tweet});
	}
	private void deleteWhenFull() {
		while (getRowCount() >= MAX_ROWS)
			removeRow(0);
	}
	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}
	@Override
	public void purgeBeforeAdd() {
//		deleteWhenFull();
	}
	public void filterThisID(String screenName) {
		Filter.filterThisID(screenName);
		//TODO update all the tweets and refresh the screen
	}
}
