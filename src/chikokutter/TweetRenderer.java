package chikokutter;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class TweetRenderer extends JLabel implements TableCellRenderer {

	private TableStreamModel model;
	public TweetRenderer(TableStreamModel model) {
		this.model = model;
		setOpaque(true);//MUST do this for background to show up
		setFont(StreamPanel.DEFAULT_FONT);//MUST do this to avoid bold font
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object item,
			boolean isSelected, boolean hasFocus, int row, int column) {
		TweetI tweet = (TweetI) item;
		 setBackground(tweet.getColor());
		 setText(getText(tweet, column));
//		 String str = item.toString();
//		 setText("<htmlx><b>"+str.substring(0,1)+"</b>"+str.substring(1)+"</html>");
//	        if (isBordered) {
//	            if (isSelected) {
//	                ...
//	                //selectedBorder is a solid border in the color
//	                //table.getSelectionBackground().
//	                setBorder(selectedBorder);
//	            } else {
//	                ...
//	                //unselectedBorder is a solid border in the color
//	                //table.getBackground().
//	                setBorder(unselectedBorder);
//	            }
//	        }
//	        
//	        setToolTipText(...); //Discussed in the following section
	        return this;
	}

	private String getText(TweetI tweet, int column) {
		if (column==0) 
			return tweet.getText();
		else if (column==1)
			return tweet.getScreenName();
		else if (column==2)
			return renderTime(tweet);
		else
			throw new Error("unexpected column:" + column);
	}

	private String renderTime(TweetI tweet) {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        String l = (sdf.format(tweet.getTime()));
		return l;
	}
}
