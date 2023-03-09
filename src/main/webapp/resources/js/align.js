// 정렬 클래스
class Align {
	// 생성자
	// orderItem: 어떤 항목으로 정렬을 할 지 선택
	// - 0: 없음, 1: 번호, 2: 이름, 3: 성별, 4: 부서, 5: 졸업일
	// order : 오름차순, 내림차순 선택
	// - 0: 없음, 1: 오름차순, 2: 내림차순
	constructor(orderItem = 0, order = 0) {
		this.orderItem = orderItem
		this.order = order
	}
	
	// Icon class값을 반환한다.
	getOrderIcon() {
		if(this.order == 0) return "bi bi-three-dots-vertical"
		else if(this.order == 1) return "bi bi-arrow-up"
		else return "bi bi-arrow-down"
	}
	
	// 설정 값을 반환한다.
	getAlign() {
		return {"orderItem": this.orderItem, "order": this.order}
	}

	// 메소드를 호출할때마다 정렬방식을 Toggle한다.
	setOrder() {
		if(this.order == 1) this.order = 2
		else this.order = 1
	}
	
	// 정렬값을 임의로 변경하고 결과에 따라 정렬 방식과 아이콘을 설정한다.
	setOrderItem(orderItem) {
		this.orderItem = orderItem
		this.order = orderItem == 0 ? 0 : this.orders
		this.setIcon()
	}
	
	// 정렬한다.
	setAlign(orderItem) {
		// true : 같은 버튼을 누를 때
		// false : 다른 버튼을 누를 때
		this.setOrder()
		if(this.orderItem != orderItem) {
			this.orderItem = orderItem // 버튼 변경
			this.order = 1 // 오름차순 초기화
		}
		this.setIcon()
	}

	// Icon class를 설정한다.
	setIcon(btnName = "alignBtn") {
    	$(`button[name=${btnName}]`).find('i').each((i, e) => {
    		if(this.orderItem == i + 1) {
    			$(e).attr("class", this.getOrderIcon())	
    		} else {
    			$(e).attr("class", "bi bi-three-dots-vertical")
    		}
    	})
	}
}