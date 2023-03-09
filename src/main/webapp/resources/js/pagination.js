// 페이지 클래스
class Pagination {
	// 생성자
	// currentPage: 현재 페이지
	// cntPerPage: 페이지당 데이터 출력 갯수
	// pageSize: Pagination 페이지 사이즈
	// totalPageCount: 전체 데이터 갯수
	constructor(currentPage = 1, cntPerPage = 5, pageSize = 5) {
		this.currentPage = currentPage
		this.cntPerPage = cntPerPage
		this.pageSize = pageSize
		this.totalPageCount = 1
	}

	// 서버에서 값을 반환해주면 그 값을 저장한다.
	setPagination(object) {
		let page = object.pagination
		this.currentPage = page.currentPage
		this.cntPerPage = page.cntPerPage
		this.pageSize = page.pageSize
		this.totalPageCount = page.totalPageCount
		this.totalRecordCount = page.totalRecordCount
	}

	// page 그린다.
    showPagination() {
    	let arr = []
    	let totalPage = pagination.getTotalPageCount()
    	let currentPage = pagination.getCurrentPage()
    	let pageSize = pagination.getPageSize()
    	let startPage = 0
    	let endPage = 0
    	
    	$("#pagination").empty()
    	if(totalPage > 0) {
	    	if(currentPage % pageSize == 0) endPage = Math.floor(currentPage / pageSize) * pageSize > totalPage ? totalPage : Math.floor(currentPage / pageSize) * pageSize 
	    	else endPage = Math.floor(currentPage / pageSize + 1) * pageSize > totalPage ? totalPage : Math.floor(currentPage / pageSize + 1) * pageSize
	    	
	    	if(endPage % pageSize == 0) startPage = endPage - pageSize + 1 > 0 ? endPage - pageSize + 1 : 1
	    	else startPage = endPage - (endPage % pageSize) + 1 > 0 ? endPage - (endPage % pageSize) + 1 : 1
	    	arr.push(`<li class="page-item"><button type="button" class="page-link" onclick="prevBtnClick()">이전</a></li>`)
	    	
	    	for(let i = startPage; i <= endPage; i++) {
	    		arr.push(`<li class="page-item"><button type="button" class="${i == currentPage ? 'page-link active' : 'page-link'}" onClick="pageBtnClick(${i})">${i}</a></li>`)
	    	}
	    	
	    	arr.push(`<li class="page-item"><button type="button" class="page-link" onclick="nextBtnClick()">다음</a></li>`)
	    	$("#pagination").append(arr.join(''))
    	}
    }

	// 다음 페이지로 설정한다.
	nextPage() {
		if(this.currentPage > this.totalPageCount) this.currentPage = this.totalPageCount
		else this.currentPage += 1
	}
	
	// 이전 페이지로 설정한다.
	prevPage() {
		if(this.currentPage < 1) this.currentPage = 1
		else this.currentPage -= 1
	}
	
	// 설정 값을 반환한다.
	getPagination() {
		return {"currentPage": this.currentPage, "cntPerPage": this.cntPerPage, "pageSize": this.pageSize}
	}

	// ------ GETTER & SETTER ------
	getTotalRecordCount() {
		return this.totalRecordCount
	}
	
	getTotalPageCount() {
		return this.totalPageCount
	}
	
	getCntPerPage() {
		return this.cntPerPage
	}
	
	getPageSize() {
		return this.pageSize
	}
	
	getCurrentPage() {
		return this.currentPage
	}
	
	setCurrentPage(currentPage) {
		this.currentPage = currentPage
	}
}