package chikokutter;

import java.awt.Color;

public enum Line {
	ODAKYU    ("���c�}",   "0C449B"), 
	CHIYODA   ("���c��", "009944"), 
	FUKUTOSHIN("���s�S��", "BB641D"), 
	TOYOKO    ("������",   "DA0442"), 
	DENEN     ("�c���s�s", "FF00FF"), 
	OOIMACHI  ("��䒬��", "F18C43"),
	YAMANOTE  ("�R��",     "33FF33"),
	NARITA    ("���c��",   "F18C43"),
	SOBU      ("������",   "F18C43"),
	NANBU     ("�앐��",   "FFD600"),
	MEGURO    ("�ڍ���",   "009CD2"),
	KEISEI    ("\\(����\\|�X�J�C���C�i�[\\|�X�J�C�A�N�Z�X\\|�A�N�Z�X���}\\)",     "F18C43"),
	KEISEI2   ("����",     "F18C43"),
	SAIKYO	  ("�鋞��",   "008000"),
	KEIYU     ("���}",     "FF0000"),
	RINKAI    ("��񂩂���", "0000FF");
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
