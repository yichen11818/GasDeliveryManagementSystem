package Model.table;

// ±Ì”≥…‰
public class Total {
	private Gas gas;
	private GasType gastype;
	private User user;
	private userCommunity usercommunity;
	private Borrow borrow;
	private Administrator admi;

	public Gas getGas() {
		return gas;
	}

	public void setGas(Gas gas) {
		this.gas = gas;
	}

	public GasType getGastype() {
		return gastype;
	}

	public void setGastype(GasType gastype) {
		this.gastype = gastype;
	}

	public User getReader() {
		return user;
	}

	public void setReader(User user) {
		this.user = user;
	}

	public userCommunity getReadertype() {
		return usercommunity;
	}

	public void setReadertype(userCommunity usercommunity) {
		this.usercommunity = usercommunity;
	}

	public Borrow getBorrow() {
		return borrow;
	}

	public void setBorrow(Borrow borrow) {
		this.borrow = borrow;
	}

	public Administrator getAdmi() {
		return admi;
	}

	public void setAdmi(Administrator admi) {
		this.admi = admi;
	}
}
