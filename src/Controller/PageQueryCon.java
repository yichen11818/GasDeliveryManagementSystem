package Controller;

import java.util.Vector;

import Model.access.GasAccess;

/*
 * 分页查询控制器
 */
public class PageQueryCon {
	public static int curentPageIndex = 1; // 当前页码
	public int pageIndex;
	protected int countPerpage = 15; // 每页显示条数
	public int pageCount; // 总页数
	protected int recordCount; // 总记录条数
	protected static Vector<Vector<Object>> bigPageVector = new Vector<Vector<Object>>();
	protected Vector<Vector<Object>> smallPageVector = new Vector<Vector<Object>>();
	GasAccess bookDao = new GasAccess();

	public PageQueryCon(){
		
	}
	// @param curentPageIndex
	public PageQueryCon(Vector<Vector<Object>> vec) {
		bigPageVector=vec;
		
		//设置页数
		if (bigPageVector.size() % countPerpage == 0) {
			pageCount = bigPageVector.size() / countPerpage;
		} else {
			pageCount = (bigPageVector.size() / countPerpage) + 1;
		}
	}

	public Vector<Vector<Object>> selectCount() {
		recordCount = bigPageVector.size();
		for (int i = (curentPageIndex - 1) * countPerpage; i < curentPageIndex * countPerpage && i < recordCount; i++) {
			smallPageVector.add(bigPageVector.get(i));
		}
		return smallPageVector;
	}

	// 获取当前页的记录
	public Vector<Vector<Object>> setCurentPageIndex() {
		curentPageIndex=1;
		return selectCount();
	}

	// 上一页
	public Vector<Vector<Object>> previousPage() {
		if (curentPageIndex > 1) {
			curentPageIndex--;
			System.out.println("当前页:" + curentPageIndex);
			pageIndex=curentPageIndex;
		}
		return selectCount();
	}
	public Vector<Vector<Object>> nextPage() {

		if (curentPageIndex < pageCount) {
			curentPageIndex++;
			pageIndex=curentPageIndex;
			System.out.println("当前页:" + curentPageIndex);
		}
		return selectCount();
	}
	public Vector<Vector<Object>> lastPage() {
		curentPageIndex =  pageCount;
		return selectCount();
	}
	public Vector<Vector<Object>> jumpPage(int page) {
		curentPageIndex =  page;
		return selectCount();
	}
	public int pageCount() {
		return pageCount;
	}

}
