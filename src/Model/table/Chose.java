package Model.table;

/**
 * ½èÔÄ¹é»¹ĞÅÏ¢±í×Ö¶Î Ó³Éä
 *  
 *
 */
public class Chose {
	private int chose_id;
	private String r_number;
	private int chose_b_id;
	private int chosedate;
	private int duedate;
	private int returndate;
	private boolean isreturn;

	public int getChose_id() {
		return chose_id;
	}

	public void setChose_id(int chose_id) {
		this.chose_id = chose_id;
	}

	public String getR_number() {
		return r_number;
	}

	public void setR_number(String r_number) {
		this.r_number = r_number;
	}

	public int getChose_b_id() {
		return chose_b_id;
	}

	public void setChose_b_id(int chose_b_id) {
		this.chose_b_id = chose_b_id;
	}

	public int getChosedate() {
		return chosedate;
	}

	public void setChosedate(int chosedate) {
		this.chosedate = chosedate;
	}

	public int getDuedate() {
		return duedate;
	}

	public void setDuedate(int duedate) {
		this.duedate = duedate;
	}

	public int getReturndate() {
		return returndate;
	}

	public void setReturndate(int returndate) {
		this.returndate = returndate;
	}

	public boolean isIsreturn() {
		return isreturn;
	}

	public void setIsreturn(boolean isreturn) {
		this.isreturn = isreturn;
	}

}
