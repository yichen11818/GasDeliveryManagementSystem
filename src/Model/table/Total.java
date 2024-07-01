package Model.table;

// ±Ì”≥…‰
public class Total {
	private Gas gas;
	private GasType booktype;
	private User user;
	private UserType readertype;
	private Borrow borrow;
	private Administrator admi;

	public Gas getBook() {
		return gas;
	}

	public void setBook(Gas gas) {
		this.gas = gas;
	}

	public GasType getBooktype() {
		return booktype;
	}

	public void setBooktype(GasType booktype) {
		this.booktype = booktype;
	}

	public User getReader() {
		return user;
	}

	public void setReader(User user) {
		this.user = user;
	}

	public UserType getReadertype() {
		return readertype;
	}

	public void setReadertype(UserType readertype) {
		this.readertype = readertype;
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
