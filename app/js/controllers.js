'use strict';

/* Controllers */

var readingControllers = angular.module('readingControllers', []);


/* Login Controller ****************************************************************/

readingControllers.controller('LoginCtrl', ['$scope', '$location',  'dataSvc',
  function($scope,  $location, dataSvc) {
    /*
    Figure out authentication, initial loading of variables
      - Load student IDs here
      - Handle errors
      - Router here by default
      - Update UI to MD

    */
    console.log("LoginCtrl entry");
    
   
    $scope.someFunction = function(option) {
      console.log("LoginCtrl someFunction()");
    }

  }]);

/* Home Controller ****************************************************************/

readingControllers.controller('HomeCtrl', ['$scope', '$http', '$location', '$window', '$route', 'dataSvc',
  function($scope, $http, $location, $window, $route, dataSvc) {
    dataSvc.setData("studentID","0001");  //get this from somewhere else in future
    $scope.studentID = dataSvc.getData("studentID"); 
    console.log("HomeCtrl studentID= "+$scope.studentID+" = "+dataSvc.getData("studentID"));
    
    //console.log('home before call');
    $http.get('service/student/' + $scope.studentID + '.json').success(function(data) {
      
      //console.log('data:',data);
      $scope.student = data;
      //$scope.student = {"id": "0001","name": {"first": "Gabi","last": "Claiborne"}};
      //console.log('student:',$scope.student);
      //console.log('studentID:',$scope.student.id);
      //console.log('first:',$scope.student.name.first);

    });

    $scope.selectMenu = function(option) {
      dataSvc.setData("homeOption",option);
      console.log("in selectOption(), option= "+dataSvc.getData("homeOption")+", option= "+option);
      //console.log("in selectOption(), cur location= "+$window.location.href);
      //$location.path("#/practiceSight");
      //$route.reload();
      //$window.location.href="#/practiceSight";
      //$window.location.assign("#/practiceSight/");
      $window.location="#/practiceSight";
      //console.log("in selectOption(), location="+ $location.path());
      //$window.location.assign("#/practiceSight");
      
      //dataSvc.dumpData("1");
    }

  }]);


/* Practice Controller ****************************************************************/

readingControllers.controller('PracticeSightCtrl', ['$scope', '$routeParams', '$location','$http', 'dataSvc',
  function($scope, $routeParams, $location, $http, dataSvc) {
    console.log('enter PracticeSightCtrl');
    
    //dataSvc.dumpData("2");
    $scope.progress="0%";
    $scope.studentID= dataSvc.getData("studentID");
    $scope.option= dataSvc.getData("homeOption");
    console.log("beginning PracticeSightCtrl, studentID= "+$scope.studentID+" , option= "+$scope.option);

    var d= new Date();
    $http.get('service/sightwordlist/' + $scope.studentID + '-' + dataSvc.getData("homeOption") + '.json?' + d.getMilliseconds()).success(function(data) {
      console.log('PracticeSightCtrl after $http call');
      $scope.wordList = data;
      console.log('data:',data);
      $scope.currWordIndex=0;
      console.log('current word:',$scope.wordList.sight_words[$scope.currWordIndex].word);
      
      //$scope.mainImageUrl = data.images[0];
    });


    $scope.markAnswer = function(studentID, word, correct) {
      
      // Update the server
      $scope.message = "{studentID="+studentID+",word="+word+",correct="+correct+"}";
      console.log('markAnswer() message:',$scope.message);

      $http.post('service/sightwordlist/markanswer', $scope.message)
        .success(function(data) {
          //console.log('markAnswer service call: success');  
        })
        .error(function(data) {
          //console.log('markAnswer service call: fail');    
        }); 

      //Update the local array
      var iInt, iBool; 
      if ('true'==correct) { iInt=1; iBool=true; } else { iInt=0; iBool=false; }
      console.log('markAnswer() numberic value of correct',iInt);
      $scope.wordList.sight_words[$scope.currWordIndex].stats.avg= 
        ($scope.wordList.sight_words[$scope.currWordIndex].stats.avg * $scope.wordList.sight_words[$scope.currWordIndex].stats.num + iInt)/
        ($scope.wordList.sight_words[$scope.currWordIndex].stats.num+1);
      $scope.wordList.sight_words[$scope.currWordIndex].stats.num++;
      $scope.wordList.sight_words[$scope.currWordIndex].stats.last=true;
      
      $scope.calcProgress($scope.wordList.sight_words);

      $scope.selectNextWord();
    }


    $scope.isWordLearned = function(word) {
      if (word.stats.avg>=0.5 && word.stats.last && word.stats.num>=1) {
        return true;
      } else {
        return false;
      }

    }


    $scope.calcProgress = function(wordList) {
      var numCorrect=0, i, learned;
      
      if (!wordList) {
          console.log("calcProgress(): !wordList");
          return "0%";
      }

      for (i=0; i<wordList.length; i++) {
        learned = $scope.isWordLearned( wordList[i] );
        if (learned) {
          numCorrect++;
        }
      }
      var pctCorrect = numCorrect / wordList.length * 100;
      
      $scope.progress = pctCorrect.toFixed(0)+"%";
      console.log("calcProgress()= " + $scope.progress);
      //return $scope.progress;
    }


    $scope.selectNextWord = function() {
      $scope.incrementIndex();
      
     // mike, check this function for endless loop goto

      if ("100%"!=$scope.progress) {
        while ($scope.isWordLearned($scope.wordList.sight_words[$scope.currWordIndex])) {
          $scope.incrementIndex();
          //console.log("selectNextWord: "+ $scope.currWordIndex);
        }
      }

    }
    

    $scope.incrementIndex = function() {
      if ($scope.currWordIndex < $scope.wordList.sight_words.length - 1) {
        $scope.currWordIndex++;
      } else {
        $scope.currWordIndex = 0;
      }

    }

}]);
