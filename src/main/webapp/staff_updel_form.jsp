<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/utils.js"></script>
<title>staff updel</title>
<script>
	const path = '${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}'

	const school = ["고졸", "전문대졸", "일반대졸"]
	const skill = ["JAVA", "JSP", "ASP", "PHP", "Delphi"]
	const department = ["ICT사업부", "디지털트윈사업부", "SI사업부", "반도체사업부", "기업부설연구소", "전략기획팀", "경영지원팀"]

	const staffNo = '<c:out value="${staffNo}"/>'
	
	function init() {
		// 날짜 추가
		setDate(["#graduate_year"], ["#graduate_month"], ["#graduate"])
		
		// 부서 추가
	    department.forEach((v, i) => {
    		$("#department").append(`<option value="\${i+1}">\${v}</option>`)
	    })
	    
	    // 기술 추가
	    skill.forEach((v, i) => {
	        $("#skill").append(`<input type="checkbox" name="skill" value="\${v}"> \${v} `)
	    }) 
	
	    // 학력 추가
	    school.forEach((v, i) => {
	        $("#school").append(`<input type="radio" name="school" value="\${i+1}"/>\${v} `)
	    })
	    
	    // staff 정보 출력
		getStaff()
		
		// staff skill 정보 출력
		getStaffSkill()
	}
    
	// 사원 정보를 요청한다.
	function getStaff() {
		getJson("/api/staff?staffNo=" + staffNo, getStaffCallback)
	}
	
	// 사원 정보를 가져와 화면에 출력한다.
	function getStaffCallback(result) {
		if(result) {
	    	$("#jumin1").val(result?.juminNo?.substr(0, 6))
	    	$("#jumin2").val(result?.juminNo?.substr(7, 7))
			$("#staffName").val(result?.staffName)
	    	$("#graduate_year").val(result?.graduateDay?.substr(0, 4)).prop("selected", true)
	    	$("#graduate_month").val(result?.graduateDay?.substr(4, 2)).prop("selected", true)
	    	$("#graduate_day").val(result?.graduateDay?.substr(6, 2)).prop("selected", true)
			$(`#department option:eq(\${result?.departmentCode})`).prop("selected", true)
	    	$("input:radio[name=school]").eq(result?.schoolCode - 1).prop("checked", true)
		}
	}
	
	// 사원의 기술 정보를 요청한다.
	function getStaffSkill() {
		getJson("/api/staff/skill?staffNo=" + staffNo, getStaffSkillCallback)
	}

	// 사원의 기술 정보를 가져와 화면에 출력하는 callback 함수이다.
	function getStaffSkillCallback(result) {
		result.map((i) => {
			if(i?.SKILL_CODE > 5) {
				$("#addSkill").append(`<input type="checkbox" name="skill" value="\${i.SKILL_NAME}" checked/>\${i.SKILL_NAME} `)
			} else {
				$("input[name=skill]").eq(i?.SKILL_CODE - 1).prop("checked", true)	
			}
		})
	}
	
	// 사원 정보를 업데이트 한다.
	function updateStaff() {
    	let year = $("select[id=graduate_year]").val() 
    	let month = $("select[id=graduate_month]").val()
    	let day = $("select[id=graduate_day]").val()
		
    	let data = {
			staffNo,
			"graduateDay": year + month + day,
			"staffName": $("#staffName").val(), 
    		"schoolCode": $("input[name=school]:checked").val(),
    		"departmentCode": $("select[name=department]").val(),
    		"juminNo": $("#jumin1").val() + "-" + $("#jumin2").val(),
    		"skill": arrayDistinct([...getInputCheckedArray("skill"), ...getInputCheckedArray("addSkill")])
    	}
		
		if(!dateValidate(year, month, day)) return false
		
		let isErr = validate(data)
		
		if(isErr != null) {
			alert(isErr)
			return false
		}
		
		let result = confirm("정말 수정 하시겠습니까?")
		if(result) {
			putJson(`${path}/api/staff`, data, updateCallback)
		}
	}
	
	// 사원 정보를 업데이트 후 작업을 진행하는 callback 함수이다.
	function updateCallback(result) {
		if(result > 0) {
			alert("수정이 완료되었습니다.")
			window.opener.getReturnValue(JSON.stringify({type: "update", success: true}))
			window.close();
		} else alert("수정을 실패했습니다.")
	}
	
	// 사원을 삭제한다.
	function deleteStaff() {
		let result = confirm("정말 삭제 하시겠습니까?")
		if(result) {
			deleteJson(`${path}/api/staff?staffNo=` + staffNo, deleteCallback)
		}
	}
	
	// 사원을 삭제 후 진행하는 callback 함수이다. 
	function deleteCallback(result) {
		if(result > 0) {
			alert("삭제가 완료되었습니다.");
			window.opener.getReturnValue(JSON.stringify({type: "delete", success: true}))
			window.close();
		} else {
			alert("삭제를 실패했습니다.")
		}
	}

	$(() => {
		init()
	})
</script>
</head>
<body>
    <div class="container-fluid">
        <form>
            <table class="table table-bordered border mt-3 text-center">
                <tr>
                    <th colspan="6">사원 정보 수정</th>
                </tr>
                <tr>
                    <td>이름</td>
                    <td><input id="staffName" type="text" value="${staff.staffName }"/></td>
                    <td>주민번호</td>
                    <td>
                        <input id="jumin1" type="text" size="6" maxlength="6" value="${fn:substring(staff.juminNo,0, 6)}"/> -
                        <input id="jumin2" type="password" size="7" maxlength="7" value="${fn:substring(staff.juminNo,7, 14)}"/>
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
                    <td id="school"></td>
                    <td>기술</td>
                    <td id="skill" colspan="3"></td>
                </tr>
                <tr>
                    <td>졸업일</td>
                    <td colspan="5">
                        <select id="graduate_year" onchange="setLastDay(`#graduate`)">
                            <option value="" selected></option>
                        </select>년
                        <select id="graduate_month" onchange="setLastDay(`#graduate`)">
                            <option value="" selected></option>
                        </select>월
                        <select id="graduate_day"></select>일
                    </td>
                </tr>
                <tr>
                	<td>추가 기술</td>
                	<td colspan="4" id="addSkill"></td>
                	<td><input type="text" id="addSkillInput" onkeyup="if(window.event.keyCode==13) { addSkill() }" placeholder="추가 기술을 입력하세요."/></td>
                </tr>
            </table>
            <div class="btn-toolbar flex justify-content-center">
                <div class="btn-group">
                    <span><button type="button" class="btn btn-primary mx-1" onclick="updateStaff()">수정</button></span>
                    <span><button type="button" class="btn btn-danger mx-1" onclick="deleteStaff()">삭제</button></span>
                </div>
            </div>
        </form>
    </div>
</body>
</html>