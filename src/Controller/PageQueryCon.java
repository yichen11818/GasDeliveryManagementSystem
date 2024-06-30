package Controller;

import java.util.Vector;

import Model.access.GasAccess;

/*
 * ��ҳ��ѯ������
 */
public class PageQueryCon {
	public static int curentPageIndex = 1; // ��ǰҳ��
	public int pageIndex;
	protected int countPerpage = 15; // ÿҳ��ʾ����
	public int pageCount; // ��ҳ��
	protected int recordCount; // �ܼ�¼����
	protected static Vector<Vector<Object>> bigPageVector = new Vector<Vector<Object>>();
	protected Vector<Vector<Object>> smallPageVector = new Vector<Vector<Object>>();
	GasAccess bookDao = new GasAccess();

	public PageQueryCon(){
		
	}
	// @param curentPageIndex
	public PageQueryCon(Vector<Vector<Object>> vec) {
		bigPageVector=vec;
		
		//����ҳ��
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

	// ��ȡ��ǰҳ�ļ�¼
	public Vector<Vector<Object>> setCurentPageIndex() {
		curentPageIndex=1;
		return selectCount();
	}

	// ��һҳ
	public Vector<Vector<Object>> previousPage() {
		if (curentPageIndex > 1) {
			curentPageIndex--;
			System.out.println("��ǰҳ:" + curentPageIndex);
			pageIndex=curentPageIndex;
		}
		return selectCount();
	}
	public Vector<Vector<Object>> nextPage() {

		if (curentPageIndex < pageCount) {
			curentPageIndex++;
			pageIndex=curentPageIndex;
			System.out.println("��ǰҳ:" + curentPageIndex);
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
