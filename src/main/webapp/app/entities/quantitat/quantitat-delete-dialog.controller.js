(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('QuantitatDeleteController',QuantitatDeleteController);

    QuantitatDeleteController.$inject = ['$uibModalInstance', 'entity', 'Quantitat'];

    function QuantitatDeleteController($uibModalInstance, entity, Quantitat) {
        var vm = this;

        vm.quantitat = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Quantitat.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
