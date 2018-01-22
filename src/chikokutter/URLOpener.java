package chikokutter;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class URLOpener {

	public static void main(String[] args) throws IOException, URISyntaxException {
		openURL("http://www.yahoo.com/");

	}

	public static void openURL(String urlString) {
		
		Desktop d = Desktop.getDesktop();
		try {
			d.browse(new URI(urlString));
		} catch (IOException | URISyntaxException e) {
			System.err.printf("failed to open %s with %s\n", urlString, e);
		}
		
	}

}
