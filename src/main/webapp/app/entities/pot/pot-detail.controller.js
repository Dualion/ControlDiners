(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('PotDetailController', PotDetailController);

    PotDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Pot'];

    function PotDetailController($scope, $rootScope, $stateParams, previousState, entity, Pot) {
        var vm = this;

        vm.pot = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('controlDinersApp:potUpdate', function(event, result) {
            vm.pot = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
