'use strict';

/* App Module */

var varReadingApp = angular.module('readingApp', [
  'ngRoute',
  'ngMaterial',
  'readingControllers',
  'readingServices'
]);


varReadingApp.config(['$routeProvider',
  function($routeProvider) {
    console.log('in router',$routeProvider);
    $routeProvider.
      when('/home', {
        templateUrl: 'partials/home.html',
        controller: 'HomeCtrl'
      }).
      when('/login', {
        templateUrl: 'partials/login.html',
        controller: 'LoginCtrl'
      }).
      when('/practiceSight/:studentID', {
        templateUrl: 'partials/practice-sight.html',
        controller: 'PracticeSightCtrl'
      }).
      when('/practiceSight', {
        templateUrl: 'partials/practice-sight.html',
        controller: 'PracticeSightCtrl'
      }).
      otherwise({
        redirectTo: '/login'
      });
  }]);
