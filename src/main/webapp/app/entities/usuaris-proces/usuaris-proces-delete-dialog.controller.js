(function() {
    'use strict';

    angular
        .module('controlDinersApp')
        .controller('UsuarisProcesDeleteController',UsuarisProcesDeleteController);

    UsuarisProcesDeleteController.$inject = ['$uibModalInstance', 'entity', 'UsuarisProces'];

    function UsuarisProcesDeleteController($uibModalInstance, entity, UsuarisProces) {
        var vm = this;

        vm.usuarisProces = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UsuarisProces.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
