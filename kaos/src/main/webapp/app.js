'use strict';

angular.module('app', ['ngRoute', 'ngCookies', 'chat'])
    .config(config)
    .run(run);

config.$inject = ['$routeProvider'];
function config($routeProvider) {
    $routeProvider
        .when('/', {
            controller: 'ChatController',
            templateUrl: 'chat/chat.html'
        })
        .when('/login', {
            controller: 'LoginController',
            templateUrl: 'login/login.view.html'
        })
        .when('/register', {
            controller: 'RegisterController',
            templateUrl: 'register/register.view.html'
        })
        .when('/create', {
            controller: 'TeamCreateController',
            templateUrl: 'team_management/team.html'
        })
        .when('/join', {
            controller: 'TeamJoinController',
            templateUrl: 'team_management/team.html'
        })
        .otherwise({ redirectTo: '/login' });
}

run.$inject = ['$rootScope', '$location', '$cookieStore', '$http'];
function run($rootScope, $location, $cookieStore, $http) {
    
    // keep user logged in after page refresh
    $rootScope.globals = $cookieStore.get('globals') || {};
    if ($rootScope.globals.currentUser) {
        $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
    }

    // redirect to login page if not logged in and trying to access a restricted page
    $rootScope.$on('$locationChangeStart', function (event, next, current) {
        
        var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
        var loggedIn = $rootScope.globals.currentUser;
        if (restrictedPage && !loggedIn) {
            $location.path('/login');
        }
    });
}
