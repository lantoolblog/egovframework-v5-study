/****************************************************************
 *
 * 파일명 : select_load
 * 설  명 : select box 데이터 로드
 *
 *    수정일          수정자     Version        Function 명
 * ------------    ---------   -------------  ----------------------------
 * 2017.01.13       장동한        1.0             최초생성
 *
 */
$(function() {
	
	$.fn.emptySelect = function(){
	    return this.each(function(){
	      if (this.tagName=='SELECT') this.options.length = 0;
	    });
	}

	$.fn.loadSelectDept = function(optionsDataArray,defaultText) {
	    return this.emptySelect().each(function(){
	      if (this.tagName=='SELECT') {
	          var selectElement = this;
	          selectElement.add( new Option(defaultText, ''));
	          jQuery.each(optionsDataArray,function(idx,data1){
				  jQuery.each(data1,function(idx,optionData){
		              var option = new Option(optionData.deptname, optionData.deptid);
		              if ( navigator.userAgent.match(/msie/i) ) {
		                  selectElement.add(option);
		              }else {
		                  selectElement.add(option,null);
		              }
				  });
				});  
	      }
	   });
	}

	
})
