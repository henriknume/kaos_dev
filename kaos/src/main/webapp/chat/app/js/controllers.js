/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
'use strict';

/*
 * Controllers
 */

var MessageControllers = angular.module('MessageControllers', []);

// General navigation controller
MessageControllers.controller('NavigationCtrl', ['$scope', '$location',
    function($scope, $location) {
        $scope.navigate = function(url) {
            $location.path(url);
        };
    }]);

MessageControllers.controller('ChatMessagesCtrl', ['$scope', 'MessagesProxy',
        function($scope, MessagesProxy){
            $scope.teamName = 'kaosdev';            
            
        }]);
