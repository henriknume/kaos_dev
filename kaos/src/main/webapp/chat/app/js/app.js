'use strict';

//Here we define the module of our app. The array contains all other modules 
//that this one depends on. 
var chat = angular.module('chat', ['chatControllers'/*, 'chatService'*/]);

/*
 * Don't know if we need this route provider yet, or at all. 
 * 
chat.config(['$routeProvider',
    function ($routeProvider) {  // Injected object $routeProvider
        $routeProvider.
                when('/chat/:team', {
                    templateUrl: 'main.html',
                    controller: 'mainCtrl'
                });
    }]);
*/