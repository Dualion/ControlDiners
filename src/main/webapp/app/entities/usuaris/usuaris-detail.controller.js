(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('UsuarisDetailController', UsuarisDetailController);

    UsuarisDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Usuaris'];

    function UsuarisDetailController($scope, $rootScope, $stateParams, previousState, entity, Usuaris) {
        var vm = this;

        vm.usuaris = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('controlDinersApp:usuarisUpdate', function(event, result) {
            vm.usuaris = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
