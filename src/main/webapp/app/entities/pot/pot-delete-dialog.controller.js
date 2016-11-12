(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('PotDeleteController',PotDeleteController);

    PotDeleteController.$inject = ['$uibModalInstance', 'entity', 'Pot'];

    function PotDeleteController($uibModalInstance, entity, Pot) {
        var vm = this;

        vm.pot = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Pot.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
