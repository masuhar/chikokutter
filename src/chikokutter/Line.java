package chikokutter;

import java.awt.Color;

public enum Line {
	ODAKYU    ("小田急",   "0C449B"), 
	CHIYODA   ("千代田線", "009944"), 
	FUKUTOSHIN("副都心線", "BB641D"), 
	TOYOKO    ("東横線",   "DA0442"), 
	DENEN     ("田園都市", "FF00FF"), 
	OOIMACHI  ("大井町線", "F18C43"),
	YAMANOTE  ("山手",     "33FF33"),
	NARITA    ("成田線",   "F18C43"),
	SOBU      ("総武線",   "F18C43"),
	NANBU     ("南武線",   "FFD600"),
	MEGURO    ("目黒線",   "009CD2"),
	KEISEI    ("\\(京成\\|スカイライナー\\|スカイアクセス\\|アクセス特急\\)",     "F18C43"),
	KEISEI2   ("京成",     "F18C43"),
	SAIKYO	  ("埼京線",   "008000"),
	KEIYU     ("京急",     "FF0000"),
	RINKAI    ("りんかい線", "0000FF");
	// http://stackoverflow.com/questions/3942878/how-to-decide-font-color-in-white-or-black-depending-on-background-color
	public static Line[] defaultLines() {
		return new Line[]{	
				ODAKYU    ,
//				CHIYODA   ,
//				FUKUTOSHIN,
//				DENEN     ,
//				TOYOKO    ,
				OOIMACHI  ,
				NANBU     ,
//				MEGURO    ,
//				SAIKYO    ,
//				RINKAI    ,
//				YAMANOTE,
//				NARITA,
//				SOBU,
//				KEISEI,
 };
	}
	private String lineName;

	private Color color;

	Line(String lineName, String colorCode) {
		this.lineName = lineName;
		this.color = makeBackgroundColor(colorCode);
	}

	public Color getColor() {
		return color;
	}

	public String getLineName() {
		return lineName;
	}

	private Color makeBackgroundColor(String colorCode) {
		Color color = new Color(Integer.parseInt(colorCode, 16));
		float[] hsb = new float[3];
		Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
		return new Color(Color.HSBtoRGB(hsb[0], hsb[1]/3, 1.0F));
	}
}
