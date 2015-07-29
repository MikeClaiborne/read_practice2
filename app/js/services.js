'use strict';

var varReadingServices = angular.module('readingServices', []);

varReadingServices.factory('dataSvc', function() {
	 var savedData = {}
	 
	 function setData(pName, pValue) {
	   savedData[pName] = pValue;
	 }
	 
	 function getData(pName) {
	  return savedData[pName];
	 }

	 function dumpData(place) {
	    console.log("dumpData(): "+place);
	  	for (var i in savedData) {
	    	if (savedData.hasOwnProperty(i)) {
	        	console.log(">>> "+i+" = "+savedData[i]);
	   		}

	 	}
	 }	

	 return {
	  setData: setData,
	  getData: getData,
	  dumpData: dumpData
	 }

});
