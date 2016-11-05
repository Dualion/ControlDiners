(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('ProcesDeleteController',ProcesDeleteController);

    ProcesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Proces'];

    function ProcesDeleteController($uibModalInstance, entity, Proces) {
        var vm = this;

        vm.proces = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Proces.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
