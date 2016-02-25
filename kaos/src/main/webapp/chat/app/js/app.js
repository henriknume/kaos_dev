/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';

/* 
 *  The Shop App
 */
var chat = angular.module('Chat', [
    'ngRoute',
    //'MessageService',
    'MessageControllers'
]);


chat.config(['$routeProvider',
    function ($routeProvider) {  // Injected object $routeProvider
        console.log("=========$routeProvider start======");
        $routeProvider.
                /*when('/'), {
                    templateUrl: 'partials/chat.html',
                    controller: 'ChatMessageCtrl'
                }.
                when('/chat/:team', {
                    templateUrl: 'partials/teamChat.html',
                    controller: 'ChatMessagesCtrl'
                }).*/
                otherwise({
                    redirectTo: '/login.html'
                });
    }]);
