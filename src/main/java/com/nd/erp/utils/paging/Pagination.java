package com.nd.erp.utils.paging;

import lombok.ToString;

@ToString
public class Pagination {
	private int currentPage; // 현재 페이지
	private int cntPerPage; // 페이지 출력 갯수
	private int pageSize; // pagination 사이즈
	private int totalRecordCount; // 전체 데이터 갯수
	private int totalPageCount; // 전체 페이지 갯수
	private int firstPage; // pagination 첫 페이지 번호
	private int lastPage; // pagination 마지막 페이지 번호
	private int firstRecordIndex; // SQL 조건 첫 RNUM
	private int lastRecordIndex; // SQL 조건 마지막 RNUM
	private boolean hasPreviousPage; // 이전 페이지 존재 여부
	private boolean hasNextPage; // 다음 페이지 존재 여부
	
	public Pagination(int currentPage, int cntPerPage, int pageSize) {
		// 오류 발생 방지
		if(currentPage < 1) {
			currentPage = 1;
		}
		
		// 5개 단위 페이지만 처리
//		if(cntPerPage != 5) {
//			cntPerPage = 5;
//		}
		
		// pagination 페이지 갯수 5개 제한
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
		// 전체 페이지 수 ( 현재 페이지가 전체 페이지보다 크면 현재 페이지에 전체 페이지를 저장 )
		totalPageCount = ((totalRecordCount - 1) / this.getCntPerPage()) + 1;
		if(this.getCurrentPage() > totalPageCount) {
			this.setCurrentPage(totalPageCount);
		}
		
		// 페이지 리스트의 첫 페이지 번호
		firstPage = ((this.getCurrentPage() -1) / this.getPageSize()) * this.getPageSize() + 1;
		
		// 페이지 리스트의 마지막 페이지 번호 ( 마지막 페이지가 전체 페이지보다 크면 마지막 페이지에 전체 페이지를 저장 )
		lastPage = firstPage + this.getPageSize() -1;
		if(lastPage > totalPageCount) {
			lastPage = totalPageCount;
		}
		
		// SQL 조건에 사용되는 첫 RNUM
		firstRecordIndex = (this.getCurrentPage() - 1) * this.getCntPerPage() + 1;
		
		// SQL 조건에 사용되는 마지막 RNUM
		lastRecordIndex = this.getCurrentPage() * this.getCntPerPage();
		
		// 이전 페이지 존재하는지 확인
		hasPreviousPage = firstPage == 1 ? false : true;
		if(hasPreviousPage == false) {
			if(currentPage != firstPage) {
				hasPreviousPage = true;
			} else {
				hasPreviousPage = false;
			}
		}
		
		// 다음 페이지 존재하는지 확인
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
