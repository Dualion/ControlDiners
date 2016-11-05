(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('UsuarisProcesDetailController', UsuarisProcesDetailController);

    UsuarisProcesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UsuarisProces', 'Proces', 'Usuaris'];

    function UsuarisProcesDetailController($scope, $rootScope, $stateParams, previousState, entity, UsuarisProces, Proces, Usuaris) {
        var vm = this;

        vm.usuarisProces = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('controlDinersApp:usuarisProcesUpdate', function(event, result) {
            vm.usuarisProces = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
