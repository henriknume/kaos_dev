'use strict';

//Here we define the module of our app. The array contains all other modules 
//that this one depends on. 
var chat = angular.module('chat', [
    'chatControllers',
    'ngRoute'
    /*, 'chatService'*/]);


chat.config(['$routeProvider',
    function ($routeProvider) {  // Injected object $routeProvider
        $routeProvider.
            when('/', {
                templateUrl: 'chat/app/partials/start.html',
                controller: 'loginCtrl'
            })
            .when('/login', {
                templateUrl: 'chat/app/partials/login.html',
                controller: 'loginCtrl'
            })
            .when('/register', {
                templateUrl: 'chat/app/partials/register.html',
                controller: 'loginCtrl'
            })
            .when('/chat/:user', {
                templateUrl: 'chat/app/partials/main.html',
                controller: 'mainCtrl',
                css: 'chat/app/css/mainCss.css'
            });
            
    }]);

