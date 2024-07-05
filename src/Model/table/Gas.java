package Model.table;

/**
 * 图书信息表字段 映射
 * @author rsw
 */
public class Gas {
	private int g_id;
	private String g_name;
	private int gastype;
	private String author;
	private double price;
	private int invebtory;

	public int getG_id() {
		return g_id;
	}

	public void setG_id(int g_id) {
		this.g_id = g_id;
	}

	public String getB_name() {
		return g_name;
	}

	public void setB_name(String g_name) {
		this.g_name = g_name;
	}

	public int getGastype() {
		return gastype;
	}

	public void setGastype(int gastype) {
		this.gastype = gastype;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getInvebtory() {
		return invebtory;
	}

	public void setInvebtory(int invebtory) {
		this.invebtory = invebtory;
	}
	
}
