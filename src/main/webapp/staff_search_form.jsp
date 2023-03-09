<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/utils.js"></script>
<script type="text/javascript" src="/resources/js/align.js"></script>
<script type="text/javascript" src="/resources/js/pagination.js"></script>
<title>staff search</title>
<script>
	const path = '${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}'
	
	// 정렬 객체를 생성한다.
	const align = new Align()
	// 페이지 객체를 생성한다.
	const pagination = new Pagination()
	
	// 검색 결과를 가진 array이다.
	const searchResult = []
	let isAllSearch = false
	
	// 초기값 array이다.
    const graduate = ["고졸", "전문대졸", "일반대졸"]
    const skill = ["JAVA", "JSP", "ASP", "PHP", "Delphi"]
	const department = ["ICT사업부", "디지털트윈사업부", "SI사업부", "반도체사업부", "기업부설연구소", "전략기획팀", "경영지원팀"]
	
    // 초기 화면을 설정한다.
    function init() {
    	// 날짜 추가
    	setDate(["#graduate_start_year", "#graduate_end_year"], ["#graduate_start_month", "#graduate_end_month"], ["#graduate_start", "#graduate_end"])
    	
        // 부서 추가
        department.forEach((v, i) => {
            $("#department").append(`<option value="\${i+1}">\${v}</option>`)
        })
        
        // 기술 추가
        skill.forEach((v, i) => {
            $("#skill").append(`<input type="checkbox" name="skill" value="\${v}"/>\${v} `)
        }) 

        // 학력 추가
        graduate.forEach((v, i) => {
            $("#graduate").append(`<input type="checkbox" name="graduate" value="\${i+1}"/>\${v} `)
        })
    }
    
    // 날짜를 잘 선택했는지 검사한다.
    function validate(sy, sm, sd, ey, em, ed) {
    	if(sy == "" || sy == undefined || sy == null ||
   			sm == "" || sm == undefined || sm == null ||
   			sd == "" || sd == undefined || sd == null ||
   			ey == "" || ey == undefined || ey == null ||
   			em == "" || em == undefined || em == null ||
   			ed == "" || ed == undefined || ed == null) return -1;
    	
    	let startDate = new Date(sy, sm, sd)
    	let endDate = new Date(ey, em, ed)
		
    	if(startDate.getTime() > endDate.getTime()) return 0;
    	return 1;
    }
    
    // 수정 / 삭제 창을 연다.
    function updelBtnClick(staffNo) {
    	window.open(`${path}/staff_updel_form/` + staffNo, 'updel_window', 'width=1200, height=400, location=no, status=no, scrollbars=yes')
    }
    
    // 등록, 수정, 삭제 창에서 추가, 수정, 삭제에 성공하면 결과를 반영한다.
    function getReturnValue(returnValue) {
    	let {type, success} = JSON.parse(returnValue)
    	if(success) {
			allSearchBtnClick()
//     		if(type = "delete") {
//     			isAllSearch = false
//     		} else {
//     			searchBtnClick()	
//     		}
    	}
    }
    
    // 모든 검색 창을 초기화한다.
    function resetBtnClick() {
    	isAllSearch = false
    	$("#result_count").text('')
    	$("#result_body, #addSkill, #pagination").empty()
    	$("input[name=gender], input[name=graduate], input[name=skill]").prop("checked", false)
    	$("#staffName, select[id=department], select[id=graduate_start_year], select[id=graduate_start_month], " + 
    		"select[id=graduate_start_day], select[id=graduate_start_day], select[id=graduate_end_year], " + 
    		"select[id=graduate_end_month], select[id=graduate_end_day], #addSkillInput").val("")
    	$(":radio[name=skillLogic][value='and']").prop("checked", true) 
    }
    
    // 검색 결과를 그린다.
    function showSearchTable(result) {
    	const resultString = []
    	
		$("#result_body").empty()
		$('#result_count').text(`검색건수 -> \${pagination.getTotalRecordCount()}`)
    	if(pagination.getTotalPageCount() > 0) {
    		result.forEach((item, index) => {
        		if(result.length - 1 == index) return
       			resultString.push(`
       				<tr>
    	       	        <td>\${item.STAFFNO}</td>
    	       	        <td>\${item.STAFFNAME}</td>
    	       	        <td>\${item.GENDER}</td>
    	       	        <td>\${department[item.DEPARTMENTCODE - 1]}</td>
    	       	        <td>\${item.GRADUATEDAY}</td>
    	       	        <td>
    	       	            <button type="button" class="btn btn-primary" onClick="updelBtnClick(\${item.STAFFNO})">수정/삭제</button>
    	       	        </td>
    	       	    </tr>		
    			`)
        	})
        	$("#result_body").append(`\${resultString.join('')}`)
	
    	} else {
        	$("#result_body").append(`<th colspan="6">사원이 없습니다.</th>`)
    	}
    }

    // data 를 초기화하고 반환하는 함수이다.
    function getInitData() {
    	return {
       		...align.getAlign(),
       		...pagination.getPagination()
       	}
    }
    
    // 검색 시 데이터를 설정해주는 함수이다.
    function getData() {
    	let start_year = $("select[id=graduate_start_year]").val() 
    	let start_month = $("select[id=graduate_start_month]").val()
    	let start_day = $("select[id=graduate_start_day]").val()
    	let end_year = $("select[id=graduate_end_year]").val()
    	let end_month = $("select[id=graduate_end_month]").val()
    	let end_day = $("select[id=graduate_end_day]").val()
    	
    	let isGraduate = validate(start_year, start_month, start_day, end_year, end_month, end_day)
    	
    	if(isGraduate == 0) {
    		alert("졸업 날짜가 정확하지 않습니다.")
    		return;
    	}

    	const data = getInitData()
    	
    	data.staffName = $("#staffName").val()
    	data.genderList = getInputCheckedArray("gender")
    	data.graduateList = getInputCheckedArray("graduate")
    	data.graduateEnd = isGraduate == 1 ? (end_year + end_month + end_day) : null
 	    data.graduateStart = isGraduate == 1 ? (start_year + start_month + start_day) : null
    	data.skillList = [...getInputCheckedArray("skill")]
 	   	data.addSkillList = [...getInputCheckedArray("addSkill")] 
		data.department = $("select[name=department]").val() != "" ? $("select[name=department]").val() : null
		data.addSkillLogic = $("input[name=skillLogic]:checked").val() 
		
		console.log(data)
// 	    	let skillLogic: $("input[name=skillLogic]:checked").val(),
    	return data
    }
    
    // 다음 페이지로 넘어간다.
    function nextBtnClick() {
    	pagination.nextPage()
    	
    	getStaff()
    }
    
    // 이전 페이지로 넘어간다.
    function prevBtnClick() {
    	pagination.prevPage()
    	
    	getStaff()
    }
    
    // 임의의 페이지로 넘어간다.
    function pageBtnClick(i) {
    	pagination.setCurrentPage(i)
    	
    	getStaff()
    }

    // 새로운 방식으로 정렬한다.
    function alignBtnClick(i) {
    	pagination.setCurrentPage(1)
    	align.setAlign(i)
    	
    	getStaff()
    }

    // 검색 버튼 동작 함수이다.
    function searchBtnClick() {
    	setSearch(false)
    	getStaff()
    }
    
    // 전체 검색 버튼 동작 함수이다.
    function allSearchBtnClick() {
    	setSearch(true)	
    	getStaff()
    }
    
    function setSearch(isAll) {
    	align.setOrderItem(0)
    	pagination.setCurrentPage(1)
    	isAllSearch = isAll
    }
    
    function getStaff() {
    	if(!isAllSearch) searchStaff()
    	else searchAllStaff()
    }
    
    // 검색 결과를 가져온다.
    function searchStaff() {
    	postJson(`${path}/api/staff`, getData(), searchCallBack)
   	}   
        
    // 모든 사람에 대한 검색 결과를 가져온다.
    function searchAllStaff() {
    	postJson(`${path}/api/staff/all`, getInitData(), searchCallBack)
    }
    
    // 검색 결과를 가져온 후 실행하는 callback 함수이다.
    function searchCallBack(result) {
		pagination.setPagination(result[result.length-1])
		showSearchTable(result)
		pagination.showPagination()
    }
    
    $(() => {
        init()
    })
</script>
<body>
    <div class="container-fluid">
        <form>
            <table class="table table-bordered mt-3 text-center">
                <tr>
                    <th colspan="6">사원 정보 검색</th>
                </tr>
                <tr>
                    <td>이름</td>
                    <td><input id="staffName" type="text"/></td>
                    <td>성별</td>
                    <td id="gender">
                        <input type="checkbox" name="gender" value="남"/>남
                        <input type="checkbox" name="gender" value="여"/>여
                    </td>
                    <td>부서</td>
                    <td>
                        <select id="department" name="department">
                        	<option value=""></option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>학력</td>
                    <td id="graduate"></td>
                    <td>기술</td>
                    <td id="skill" colspan="3"></td>
                </tr>
                <tr>
                    <td>졸업일</td>
                    <td colspan="5">
                        <select id="graduate_start_year" onchange="setLastDay(`#graduate_start`)">
                        	<option value=""></option>
                        </select>년
                        <select id="graduate_start_month" onchange="setLastDay(`#graduate_start`)">
                        	<option value=""></option>
                        </select>월
                        <select id="graduate_start_day"></select>일
                        ~
                        <select id="graduate_end_year" onchange="setLastDay(`#graduate_end`)">
                        	<option value=""></option>
                        </select>년
                        <select id="graduate_end_month" onchange="setLastDay(`#graduate_end`)">
                        	<option value=""></option>
                        </select>월
                        <select id="graduate_end_day"></select>일
                    </td>
                </tr>
                <tr>
                	<td>추가 기술</td>
                	<td colspan="3" id="addSkill"></td>
                	<td><input type="radio" name="skillLogic" value="and" checked/>&nbsp;AND &nbsp;&nbsp;&nbsp; <input type="radio" name="skillLogic" value="or"/>&nbsp;OR</td>
                	<td><input type="text" id="addSkillInput" onkeyup="if(window.event.keyCode==13) { addSkill() }" placeholder="추가기술을 입력하세요."/></td>
                </tr>
            </table>
        </form>
        <div class="btn-toolbar flex justify-content-around">
        	<div></div>
            <div class="btn-group mx-5">
                <span><button type="button" class="btn btn-primary" onclick="searchBtnClick()">검색</button></span>
            </div>
            <div class="btn-group mx-5">
                <span><button type="button" class="btn btn-primary mx-1" onclick="allSearchBtnClick()">전부검색</button></span>
                <span><button type="button" class="btn btn-warning mx-1" onclick="resetBtnClick()">초기화</button></span>
                <span><button type="button" class="btn btn-success mx-1" onclick="window.open('${path}/staff_input_form.jsp', 'input_window', 'width=1200, height=400, location=no, status=no, scrollbars=yes');">등록</button></span>
            </div>
        </div>
        <div class="flex mt-2">
        	<div class="d-flex justify-content-end"><p id="result_count" ></span></div>
   	    	<table class="table table-bordered text-center table-hover">
   	    		<thead>
	   	    		<tr>
	   	    	        <th>번호<button type="button" name="alignBtn" onclick="alignBtnClick(1)" class="btn btn-none"><i class="bi bi-three-dots-vertical"></i></button></th>
	   	    	        <th>이름<button type="button" name="alignBtn" onclick="alignBtnClick(2)" class="btn btn-none"><i class="bi bi-three-dots-vertical"></i></button></th>
	   	    	        <th>성별<button type="button" name="alignBtn" onclick="alignBtnClick(3)" class="btn btn-none"><i class="bi bi-three-dots-vertical"></i></button></th>
	   	    	        <th>부서<button type="button" name="alignBtn" onclick="alignBtnClick(4)" class="btn btn-none"><i class="bi bi-three-dots-vertical"></i></button></th>
	   	    	        <th>졸업일<button type="button" name="alignBtn" onclick="alignBtnClick(5)" class="btn btn-none"><i class="bi bi-three-dots-vertical"></i></button></th>
	   	    	        <th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
	   	    	    </tr>
   	    		</thead>
   	    		<tbody id="result_body"></tbody>
   	    	</table>
   	    	<nav>
				<ul id="pagination" class="pagination justify-content-center"></ul>
   	    	</nav>
        </div>
    </div>
</body>
</html>