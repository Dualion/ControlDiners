(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Pot', 'Proces', 'Quantitat', 'UsuarisProces', 'QuantitatService', 'ExtreurePotService'];

    function HomeController ($scope, Principal, LoginService, $state, Pot, Proces, Quantitat, UsuarisProces, QuantitatService, ExtreurePotService) {
        var vm = this;

        vm.lastPot = null;
        vm.quantitatActiva = null;
        vm.procesIsActive = null;
        vm.usuarisProces = null;
        vm.crearProces = crearProces;
        vm.acabarProces = acabarProces;
        vm.pagar = pagar;
        vm.quantitatNova = quantitatNova;
        vm.extreurePot = extreurePot;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();
        getLast();
        procesIsActive();
        getQuantitatActiva();
        getUsuarisProces();
        
        function extreurePot() {
        	ExtreurePotService.open().then(function (data) {
                vm.lastPot = data.dinersTotals;
            });
        }

        function quantitatNova() {
            QuantitatService.open().then(function (data) {
                vm.quantitatActiva = data.diners;
            });
        }

        function pagar(userId) {
        	Pot.pagament({
        		  'userId': userId
        	}).$promise.then(function(data) {
        		getUsuarisProces();
        		getLast();
        	});
        }

        function getUsuarisProces() {
        	UsuarisProces.getActiu().$promise.then(function(data) {
        		vm.usuarisProces = data;
        	}, function(error) {
        		vm.usuarisProces = null;
        	});
        }

        function acabarProces() {
        	Proces.acabarProces().$promise.then(function(data) {
        		vm.procesIsActive = false;
        		getUsuarisProces();
        	}, function(error) {
        		vm.procesIsActive = true;
        	});
        }

        function crearProces() {
        	Proces.crearProces().$promise.then(function(data) {
        		vm.procesIsActive = true;
        		getUsuarisProces();
        	}, function(error) {
        		vm.procesIsActive = false;
        	});

        }

        function procesIsActive() {
        	Proces.getProcesIsActive().$promise.then(function(data) {
        		vm.procesIsActive = data.actiu;
        	}, function(error) {
        		vm.procesIsActive = null;
        	});
        }

        function getQuantitatActiva() {
        	Quantitat.getActiva().$promise.then(function(data) {
        		vm.quantitatActiva = data.diners;
        	}, function(error) {
        		vm.quantitatActiva = 0;
        	});
        }

        function getLast() {
        	Pot.getLast().$promise.then(function(data) {
        		vm.lastPot = data.dinersTotals;
        	}, function(error) {
        		vm.lastPot = 0;
        	});
        }

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
