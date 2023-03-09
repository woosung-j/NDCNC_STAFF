package com.nd.erp.domain;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Staff {
    private int staffNo;
    private String staffName;
    private String juminNo;
    private int schoolCode;
    private int departmentCode;
	private String graduateDay;
}
