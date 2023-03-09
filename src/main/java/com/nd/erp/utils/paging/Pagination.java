package com.nd.erp.utils.paging;

import lombok.ToString;

@ToString
public class Pagination {
	private int currentPage; // ���� ������
	private int cntPerPage; // ������ ��� ����
	private int pageSize; // pagination ������
	private int totalRecordCount; // ��ü ������ ����
	private int totalPageCount; // ��ü ������ ����
	private int firstPage; // pagination ù ������ ��ȣ
	private int lastPage; // pagination ������ ������ ��ȣ
	private int firstRecordIndex; // SQL ���� ù RNUM
	private int lastRecordIndex; // SQL ���� ������ RNUM
	private boolean hasPreviousPage; // ���� ������ ���� ����
	private boolean hasNextPage; // ���� ������ ���� ����
	
	public Pagination(int currentPage, int cntPerPage, int pageSize) {
		// ���� �߻� ����
		if(currentPage < 1) {
			currentPage = 1;
		}
		
		// 5�� ���� �������� ó��
//		if(cntPerPage != 5) {
//			cntPerPage = 5;
//		}
		
		// pagination ������ ���� 5�� ����
//		if(pageSize != 5 || pageSize != 10) {
//			pageSize = 5;
//		}
		
		this.currentPage = currentPage;
		this.cntPerPage = cntPerPage;
		this.pageSize = pageSize;
	}
	
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
		
		if(totalRecordCount > 0) {
			calculation();
		}
	}
	
	private void calculation() {
		// ��ü ������ �� ( ���� �������� ��ü ���������� ũ�� ���� �������� ��ü �������� ���� )
		totalPageCount = ((totalRecordCount - 1) / this.getCntPerPage()) + 1;
		if(this.getCurrentPage() > totalPageCount) {
			this.setCurrentPage(totalPageCount);
		}
		
		// ������ ����Ʈ�� ù ������ ��ȣ
		firstPage = ((this.getCurrentPage() -1) / this.getPageSize()) * this.getPageSize() + 1;
		
		// ������ ����Ʈ�� ������ ������ ��ȣ ( ������ �������� ��ü ���������� ũ�� ������ �������� ��ü �������� ���� )
		lastPage = firstPage + this.getPageSize() -1;
		if(lastPage > totalPageCount) {
			lastPage = totalPageCount;
		}
		
		// SQL ���ǿ� ���Ǵ� ù RNUM
		firstRecordIndex = (this.getCurrentPage() - 1) * this.getCntPerPage() + 1;
		
		// SQL ���ǿ� ���Ǵ� ������ RNUM
		lastRecordIndex = this.getCurrentPage() * this.getCntPerPage();
		
		// ���� ������ �����ϴ��� Ȯ��
		hasPreviousPage = firstPage == 1 ? false : true;
		if(hasPreviousPage == false) {
			if(currentPage != firstPage) {
				hasPreviousPage = true;
			} else {
				hasPreviousPage = false;
			}
		}
		
		// ���� ������ �����ϴ��� Ȯ��
		hasNextPage = (lastPage * this.getCntPerPage()) >= totalRecordCount ? false : true;
		if(hasNextPage == false) {
			if(currentPage != lastPage) {
				hasNextPage = true;
			} else {
				hasNextPage = false;
			}
		}
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCntPerPage() {
		return cntPerPage;
	}

	public void setCntPerPage(int cntPerPage) {
		this.cntPerPage = cntPerPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public int getFirstPage() {
		return firstPage;
	}

	public void setFirstPage(int firstPage) {
		this.firstPage = firstPage;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
	}

	public int getFirstRecordIndex() {
		return firstRecordIndex;
	}

	public void setFirstRecordIndex(int firstRecordIndex) {
		this.firstRecordIndex = firstRecordIndex;
	}

	public int getLastRecordIndex() {
		return lastRecordIndex;
	}

	public void setLastRecordIndex(int lastRecordIndex) {
		this.lastRecordIndex = lastRecordIndex;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public int getTotalRecordCount() {
		return totalRecordCount;
	}
}
