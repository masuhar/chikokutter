package chikokutter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FileStringSet extends HashSet<String> implements Set<String> {

	private String pathname;

	FileStringSet(String pathname) {
		this.pathname = pathname;
		try {
			read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		for (String name: NG_NAMES_ARRAY) 
//			this.add(name);
	}
	
	public void dump() {
		PrintWriter out;
		try {
			out = new PrintWriter(new FileWriter(pathname));
			for (String entry : this) {
				out.println(entry);
			}
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void read() throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(pathname));
		for (String line = in.readLine(); line != null; line = in.readLine())
			this.add(line.trim());
		in.close();
	}
	
	@Override
	public boolean add(String str) {
		try {
			return super.add(str);
		} finally {
			dump();
		}
	}

	private static String[] NG_NAMES_ARRAY = {
		"Odakyu_1051F",
		"JR_shinkansen",
		"TokaiAnauns",
		"C_line_info",
		"F_line_info",
		"JNR203kei",
		"q031i44gedx278c",
		"Mc_16101",
		"TamagawaSta_4",
		"fujisawa_sta_dp",
		"skrp683sander",
		"JRE_announce",
//		"ChiyodaLine_BOT",
		"kinenbimachida",
		"au_shomen",
		"JR_Yamanoteline",
		"Fukutoshin10000",
//		"E233_2000_Bot",
		"drink_kanto",
		"train_inf2014",
		"chiensokuho2",
		"juhan_tokyu",
		"umakamondesu",
	};

//	public static void main(String[] args) throws IOException {
//		new FileStringSet("NG_NAMES.txt").dump();
//	}
}
