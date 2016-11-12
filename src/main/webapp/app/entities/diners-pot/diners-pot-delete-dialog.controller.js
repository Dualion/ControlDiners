(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('DinersPotDeleteController',DinersPotDeleteController);

    DinersPotDeleteController.$inject = ['$uibModalInstance', 'entity', 'DinersPot'];

    function DinersPotDeleteController($uibModalInstance, entity, DinersPot) {
        var vm = this;

        vm.dinersPot = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            DinersPot.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
