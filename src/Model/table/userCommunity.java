package Model.table;

/**
 * 读者类型表字段 映射
 * @author rsw
 */
public class userCommunity {
	private int u_id;
	private String u_community;
	private int maxcount;
	private int maxday;

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public void setMaxcount(int maxcount) {
		this.maxcount = maxcount;
	}

	public void setMaxday(int maxday) {
		this.maxday = maxday;
	}

	public String getU_community() {
		return u_community;
	}

	public void setU_community(String u_community) {
		this.u_community = u_community;
	}

	public int getMaxcount() {
		return maxcount;
	}

	public int getMaxday() {
		return maxday;
	}
}
