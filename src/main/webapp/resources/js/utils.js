// json 타입의 get request를 요청 후 callback 함수로 반환한다.
function getJson(url, cb) {
	$.ajax({
		url,
		type: "GET",
		success: cb
	})
}

// json 타입의 post request를 요청 후 callback 함수로 반환한다.
function postJson(url, data, cb) {
	$.ajax({
		url,
		type: "POST",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: cb
	})
} 

// json 타입의 put request를 요청 후 callback 함수로 반환한다.
function putJson(url, data, cb) {
	$.ajax({
		url,
		type: "PUT",
		contentType: "application/json",
		data: JSON.stringify(data),
		success: cb
	})
}

// json 타입의 delete request를 요청 후 callback 함수로 반환한다.
function deleteJson(url, cb) {
	$.ajax({
		url,
		type: "DELETE",
		success: cb
	})
}

// check된 input 값들을 array로 반환한다.
function getInputCheckedArray(id) {
	let array = []
	$(`#${id}`).find("input:checked").each((i, e) => {
		array.push(e.value)
	})
	
	return array
}

// 추가 스킬을 추가한다.
function addSkill() {
	let skill = $("#addSkillInput").val()
	if(getBytes(skill) > 20) {
		alert("스킬 이름이 너무 깁니다.")
		return 0
	}
	$("#addSkill").append(`<input type="checkbox" name="skill" value="${skill}" checked/>${skill} `)
	$("#addSkillInput").val('')
}

// 년, 월에 따라 일 을 생성한다.
function setLastDay(id) {
    let $dayId = $(id + "_day")
    let year = $(id + "_year").val()
    let month = $(id + "_month").val()
    let day = new Date(new Date(year, month, 1) - 86400000).getDate()

    $dayId.empty()
    $dayId.append('<option value=""></option>')
    for(let i = 1; i <= day; i++) {
    	if(i < 10)
    		$dayId.append(`<option value="0${i}">${i}</option>`)
    	else
        	$dayId.append(`<option value="${i}">${i}</option>`)
    }
}

// 년, 월을 생성한다. 
function setDate(year, month, day) {
    let end_year = 1960
    let today = new Date()
    let today_year = today.getFullYear()
    let index = 0

    for(let y = today_year; y >= end_year; y--) {
        $(`${year.join(", ")}`).append(`<option value="${y}">${y}</option>`)
    }
    
    for(let m = 1; m <= 12; m++) {
    	if(m < 10) $(`${month.join(", ")}`).append(`<option value="0${m}">${m}</option>`)
    	else $(`${month.join(", ")}`).append(`<option value="${m}">${m}</option>`)
    }

	day.forEach((item, index) => {
		setLastDay(item)
	})
}

// 주민번호 검사 함수이다.
function juminValidate(juminNo) {
	let jumin1 = juminNo.substr(0, 6)
	let jumin2 = juminNo.substr(7, 7)
	let yy = jumin1.substr(0, 2)
	let mm = jumin1.substr(2, 2)
	let dd = jumin1.substr(4, 2)
	let gender = jumin2.substr(0, 1)
	let msg, ss, cc
	
	// 주민번호 앞자리 숫자 검사 및 6자리 검사
	if(!isNumeric(jumin1) || jumin1.length != 6) return false
	
	// 연, 월, 일 검사
	if(yy < "00" || yy > "99" || mm < "01" || mm > "12" || dd < "01" || dd > "31") return false
	
	// 주민번호 뒷자리 숫자 검사 및 7자리 검사
	if(!isNumeric(jumin2) || jumin2.length != 7) return false
	
	// 성별 1 ~ 4 검사
	if(gender < "1" || gender > "4") return false
	
	// cc
	//cc = (gender == "1" || gender == "2") ? "19" : "20"
	
	// 날짜 형식 검사
	//if(isValidDate(cc + yy + mm + dd) == false) return false
	
	// check digit 검사
	//if(!isSSN(jumin1, jumin2)) return false
	
	return true
}

// 주민번호 검사 함수이다.
function isValidDate(iDate) {
	if(iDate.length != 8) return false
	
	let oDate = new Date()
	oDate.setFullYear(iDate.substring(0, 4))
	oDate.setMonth(parseInt(iDate.substring(4, 6)) - 1)
	oDate.setDate(iDate.substring(6))
	
	if(oDate.getFullYear() != iDate.substring(0, 4)
		|| oDate.getMonth() + 1 != iDate.substring(4, 6)
		|| oDate.getDate() != iDate.substring(6)) return false
	
	return true
}

// 주민번호 검사 함수이다.
function isNumeric(s) {
	for(let i = 0; i < s.length; i++) {
		c = s.substr(i, 1)
		if(c < "0" || c > "9") return false
	}
	
	return true
}

// 주민번호 검사 함수이다.
function isSSN(s1, s2) {
	let n = 2
	let sum = 0
	
	for(let i = 0; i < s1.length; i++)
		sum += parseInt(s1.substr(i, 1)) * n++
	
	for(let i = 0; i < s2.length - 1; i++) {
		sum += parseInt(s2.substr(i, 1)) * n++
		if(n == 10) n = 2
	}
	
	c = 11 - sum % 11
	
	if(c == 11) c = 1
	if(c == 10) c = 0
	if(c != parseInt(s2.substr(6, 1))) return false
	
	return true
}

// 문자열의 바이트 수를 구하는 함수이다.
function getBytes(contents) {
    let str_character;
    let int_char_count = 0;
    let int_contents_length = contents.length;

    for (k = 0; k < int_contents_length; k++) {
        str_character = contents.charAt(k);
        if (escape(str_character).length > 4)
            int_char_count += 2;
        else
            int_char_count++;
    }

    return int_char_count;
}

// 배열의 중복 값을 대/소문자 구분 없이 제거하는 함수이다.
function arrayDistinct(array) {
	let newArray = []
	
	array.map((v, i) => {
		let isDistinct = false
		
		if(newArray.length === 0) newArray.push(v)
		else {
			newArray.map((v2, i2) => {
				if(v.toLowerCase() == newArray[i2].toLowerCase()) isDistinct = true
			})
			if(!isDistinct) newArray.push(v)
		}
	})
	return newArray
}

// insert, update 시 검사하는 함수이다.
function validate(data) {
	let errMsg = null

	if(data.skill.length == 0) errMsg = "하나 이상의 기술을 선택하세요."
	if(data.departmentCode == "" || data.departmentCode == undefined || data.departmentCode == null) errMsg = "부서를 선택하세요."
	if(data.schoolCode == null || data.schoolCode == undefined || data.schoolCode == "") errMsg = "학력을 선택하세요."
	if(!juminValidate(data.juminNo)) errMsg = "올바른 주민번호를 입력하세요."
	if(data.staffName == "" || data.staffName == undefined || data.staffName == null) errMsg = "이름을 입력하세요."
	if(getBytes(data.staffName) > 20) errMsg = "이름이 너무 깁니다."
	
	return errMsg
}

// 졸업일이 존재하는지 확인한다.
// 최대 졸업년도 =  현재년도 + 5년
function dateValidate(year, month, day) {
	let date = new Date()
	let lastDay = new Date(new Date(year, month, 1) - 86400000).getDate()
	
	if(year == "" || year == undefined || year == null ||
   		month == "" || month == undefined || month == null ||
   		day == "" || day == undefined || day == null ||
   		year < 1 || year > date.getFullYear() + 5 || 
   		month < 1 || month > 12 || day < 1 || day > lastDay
   	) {
		alert("정확한 졸업일을 선택하세요.")
		return false
	}
	return true
}